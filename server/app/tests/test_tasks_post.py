import json
import datetime
import time
import calendar

from .conftest import client

def test_single(client):
    """Test creating a single task for a user."""
    user_info = json.dumps({
        'username': 'fair',
        'password': 'essential'
    })
    create_result = client.post('/users/', content_type='application/json',
            data=user_info)
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    access_token = login_result.get_json()['access_token']

    new_task = {
        'name': 'Book flights and hotels',
        'note': 'Check with Yoina first',
        'is_completed': False,
        'seconds_worked': 2090,
        'completion_date': None 
    }
    result = client.post(
        '/tasks/',
        content_type='application/json',
        headers={'Authorization': 'Bearer ' + access_token},
        data=json.dumps(new_task)
    )

    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json['success'] == True
    assert result_json['msg'] == None

    tasks_result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})
    assert len(tasks_result['tasks']) == 1
    assert tasks_results == [new_task]

def test_create_dup(client):
    """Test creating a duplicate of an existing task for a user."""
    user_info = json.dumps({
        'username': 'fair',
        'password': 'essential'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    access_token = login_result.get_json()['access_token']

    new_task = {
        'name': 'Book flights and hotels',
        'note': 'Check with Yoina first',
        'is_completed': False,
        'seconds_worked': 2090,
        'completion_date': None 
    }
    result = client.post(
        '/tasks/',
        content_type='application/json',
        headers={'Authorization': 'Bearer ' + access_token},
        data=json.dumps(new_task)
    )

    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json['success'] == True
    assert result_json['msg'] == None

    tasks_result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})
    assert len(tasks_result['tasks']) == 2
    assert tasks_results == [new_task, new_task]

def test_pass_refresh(client):
    """Test sending a request with a refresh token."""
    user_info = json.dumps({
        'username': 'fair',
        'password': 'essential'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    refresh_token = login_result.get_json()['refresh_token']

    new_task = {
        'name': 'Find the car',
        'note': None,
        'is_completed': False,
        'seconds_worked': 200,
        'completion_date': None 
    }
    result = client.post(
        '/tasks/',
        content_type='application/json',
        headers={'Authorization': 'Bearer ' + refresh_token},
        data=json.dumps(new_task)
    )

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['success'] == False
    assert result_json['msg'] == 'Invalid access token'

def test_non_token(client):
    """Test sending a request with a string that is not a token."""
    new_task = {
        'name': 'Buy flowers',
        'note': None,
        'is_completed': False,
        'seconds_worked': 0,
        'completion_date': None 
    }
    result = client.post(
        '/tasks/',
        content_type='application/json',
        headers={'Authorization': 'Bearer whatisthis'},
        data=json.dumps(new_task)
    )

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['success'] == False
    assert result_json['msg'] == 'Invalid access token'

def test_expired_access(client, test_config):
    """Test sending a request with an expired access token."""
    user_info = json.dumps({
        'username': 'fair',
        'password': 'essential'
    })
    create_result = client.post('/users/', content_type='application/json',
            data=user_info)
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    access_token = login_result.get_json()['access_token']

    access_exp = test_config['JWT_ACCESS_TOKEN_EXPIRES']
    exp_time = datetime.datetime.now() + access_exp
    sleep_time = exp_time - datetime.datetime.now()
    time.sleep(sleep_time.total_seconds() + 1)

    new_task = {
        'name': 'Investigate',
        'note': 'Suspicious person at 1605 on 2/2 near back window',
        'is_completed': False,
        'seconds_worked': 40000,
        'completion_date': None 
    }
    result = client.post(
        '/tasks/',
        content_type='application/json',
        headers={'Authorization': 'Bearer ' + access_token},
        data=json.dumps(new_task)
    )

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['success'] == False
    assert result_json['msg'] == 'Provided access token has expired'

def test_crossover(client):
    """Test creating a task for a user and check if it affects another user."""
    user_info = json.dumps({
        'username': 'grill',
        'password': 'barbeque'
    })
    create_result = client.post('/users/', content_type='application/json',
            data=user_info)
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    access_token = login_result.get_json()['access_token']

    now_utc = datetime.datetime.utcnow()
    comp_date = calendar.timegm(now_utc.timetuple())
    new_task = {
        'name': 'Walk the dog',
        'note': None,
        'is_completed': True,
        'seconds_worked': 2000,
        'completion_date': comp_date 
    }
    result = client.post(
        '/tasks/',
        content_type='application/json',
        headers={'Authorization': 'Bearer ' + access_token},
        data=json.dumps(new_task)
    )

    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json['success'] == True
    assert result_json['msg'] == None

    tasks_result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})
    assert len(tasks_result['tasks']) == 1
    assert tasks_results == [new_task]

    other_user_info = json.dumps({
        'username': 'fair',
        'password': 'essential'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    other_access_token = login_result.get_json()['access_token']

    other_user_task = {
        'name': 'Book flights and hotels',
        'note': 'Check with Yoina first',
        'is_completed': False,
        'seconds_worked': 2090,
        'completion_date': None 
    }
    tasks_result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + other_access_token})
    assert len(tasks_result['tasks']) == 2
    assert tasks_results == [other_user_task, other_user_task]

def test_multi(client):
    """Test creating multiple tasks for a user."""
    user_info = json.dumps({
        'username': 'dumpster',
        'password': 'garbage'
    })
    create_result = client.post('/users/', content_type='application/json',
            data=user_info)
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    access_token = login_result.get_json()['access_token']

    new_task_amt = 5
    now_utc = datetime.datetime.utcnow()
    comp_date = calendar.timegm(now_utc.timetuple())
    for i in range(new_task_amt):
        new_task = {
            'name': 'Empty the bins',
            'note': None,
            'is_completed': True,
            'seconds_worked': i,
            'completion_date': comp_date 
        }
        result = client.post(
            '/tasks/',
            content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token},
            data=json.dumps(new_task)
        )

        result_json = result.get_json()
        assert result.status_code == 200
        assert result_json['success'] == True
        assert result_json['msg'] == None

    tasks_result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})
    assert len(tasks_result['tasks']) == new_task_amt
    for task in tasks_result.get_json['tasks']:
        task_id = task['id']
        assert task == {
            'id': task_id,
            'name': 'Empty the bins',
            'note': None,
            'is_completed': True,
            'seconds_worked': task_id,
            'completion_date': comp_date           
        }