import json
import datetime
import time
import calendar
import copy

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
    tasks_result_json = tasks_result.get_json()
    new_task['id'] = 1
    assert len(tasks_result_json['tasks']) == 1
    assert tasks_result_json['tasks'] == [new_task]

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
    tasks_result_json = tasks_result.get_json()
    new_task1 = copy.deepcopy(new_task)
    new_task2 = copy.deepcopy(new_task)
    new_task1['id'] = 1
    new_task2['id'] = 2
    assert len(tasks_result_json['tasks']) == 2
    assert tasks_result_json['tasks'] == [new_task1, new_task2]

def test_non_json_body(client):
    """Test sending a non-JSON body in the request."""
    user_info = json.dumps({
        'username': 'fair',
        'password': 'essential'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    access_token = login_result.get_json()['access_token']

    result = client.post('/tasks/',
        headers={'Authorization' : 'Bearer ' + access_token},
        data={
            'name': 'Blend smoothies',
            'note': 'Out of blueberries',
            'is_completed': False,
            'seconds_worked': 0,
            'completion_date': None 
    })

    result_json = result.get_json()
    assert result.status_code == 415
    assert result_json['success'] == False
    assert result_json['msg'] == 'Request body must be JSON'

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

    today_utc = datetime.datetime.utcnow().date()
    new_task = {
        'name': 'Walk the dog',
        'note': None,
        'is_completed': True,
        'seconds_worked': 2000,
        'completion_date': today_utc.isoformat()
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
    tasks_result_json = tasks_result.get_json()
    new_task['id'] = 3
    added_task = tasks_result_json['tasks'][0]
    task_comp_date = datetime.date.fromisoformat(added_task['completion_date'])

    assert len(tasks_result_json['tasks']) == 1
    assert added_task['id'] == new_task['id']
    assert added_task['name'] == new_task['name']
    assert added_task['note'] == new_task['note']
    assert added_task['is_completed'] == new_task['is_completed']
    assert added_task['seconds_worked'] == new_task['seconds_worked']
    assert task_comp_date == today_utc

    other_user_info = json.dumps({
        'username': 'fair',
        'password': 'essential'
    })
    other_login_result = client.get('/auth/login/',
            content_type='application/json', data=other_user_info)
    other_login_json = other_login_result.get_json()
    other_access_token = other_login_result.get_json()['access_token']

    other_user_task = {
        'name': 'Book flights and hotels',
        'note': 'Check with Yoina first',
        'is_completed': False,
        'seconds_worked': 2090,
        'completion_date': None 
    }
    other_tasks_result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + other_access_token})
    other_tasks_result_json = other_tasks_result.get_json()

    other_user_task1 = copy.deepcopy(other_user_task)
    other_user_task2 = copy.deepcopy(other_user_task)
    other_user_task1['id'] = 1
    other_user_task2['id'] = 2
    assert len(other_tasks_result_json['tasks']) == 2
    assert other_tasks_result_json['tasks'] == [other_user_task1, other_user_task2]

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
    today_utc = datetime.datetime.utcnow().date()
    for i in range(new_task_amt):
        new_task = {
            'name': 'Empty the bins',
            'note': None,
            'is_completed': True,
            'seconds_worked': i + 1,
            'completion_date': today_utc.isoformat() 
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
    prev_created_tasks = 3

    tasks_result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})
    tasks_result_json = tasks_result.get_json()
    assert len(tasks_result_json['tasks']) == new_task_amt
    for task in tasks_result_json['tasks']:
        task_id = task['id']
        task_comp_date = datetime.date.fromisoformat(task['completion_date'])
        assert task_id > prev_created_tasks and \
                task_id <= new_task_amt + prev_created_tasks
        assert task['name'] == 'Empty the bins'
        assert task['note'] == None
        assert task['is_completed'] == True
        assert task['seconds_worked'] == task_id - prev_created_tasks
        assert task_comp_date == today_utc