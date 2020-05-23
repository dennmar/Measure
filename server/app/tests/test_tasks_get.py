import json
import datetime
import time
import calendar

from .conftest import client

def test_empty(client):
    """Test retrieving tasks for a user that has none."""
    user_info = json.dumps({
        'username': 'porridge',
        'password': 'rain'
    })
    create_result = client.post('/users/', content_type='application/json',
            data=user_info)
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    login_json = login_result.get_json()
    access_token = login_result.get_json()['access_token']
    result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})

    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json['tasks'] == []
    assert result_json['msg'] == None

def test_single(client):
    """Test retrieving tasks for a user that has one."""
    user_info = json.dumps({
        'username': 'porridge',
        'password': 'rain'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    access_token = login_result.get_json()['access_token']

    new_task = {
        'name': 'Complete essay',
        'note': None,
        'is_completed': False,
        'seconds_worked': 0,
        'completion_date': None 
    }
    create_result = client.post(
        '/tasks/',
        content_type='application/json',
        data=json.dumps(new_task),
        headers={'Authorization': 'Bearer ' + access_token})
    result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})

    result_json = result.get_json()
    new_task['id'] = 1
    assert result.status_code == 200
    assert result_json['tasks'] == [new_task]
    assert result_json['msg'] == None

def test_pass_refresh(client):
    """Test sending a request with a refresh token."""
    user_info = json.dumps({
        'username': 'porridge',
        'password': 'rain'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    refresh_token = login_json['refresh_token']
    result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + refresh_token})    

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['tasks'] == []
    assert result_json['msg'] == 'Invalid access token'

def test_non_token(client):
    """Test sending a request with a string that is not a token."""
    result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer 21,3o29i.'})
    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['tasks'] == []
    assert result_json['msg'] == None

def test_expired_access(client, test_config):
    """Test sending a request with an expired access token."""
    user_info = json.dumps({
        'username': 'porridge',
        'password': 'rain'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    login_json = login_result.get_json()
    access_token = login_json['access_token']
    access_exp = test_config['JWT_ACCESS_TOKEN_EXPIRES']
    exp_time = datetime.datetime.now() + access_exp
    sleep_time = exp_time - datetime.datetime.now()
    time.sleep(sleep_time.total_seconds() + 1)
    
    result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})
    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['tasks'] == []
    assert result_json['msg'] == 'Provided access token has expired'

def test_multi(client):
    """Test retrieving tasks for a user that has multiple."""
    user_info = json.dumps({
        'username': 'porridge',
        'password': 'rain'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    access_token = login_result.get_json()['access_token']
    new_task_amt = 10

    today_utc = datetime.datetime.utcnow().date()
    for i in range(new_task_amt):
        new_task = {
            'name': f'Finish PA{i + 1}',
            'note': 'That was easy',
            'is_completed': True,
            'seconds_worked': 50921,
            'completion_date': today_utc.isoformat()
        }
        create_result = client.post(
            '/tasks/',
            content_type='application/json',
            data=json.dumps(new_task),
            headers={'Authorization': 'Bearer ' + access_token}
        )
    
    result = client.get('/tasks/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})
    result_json = result.get_json()
    first_task = {
        'id': 1,
        'name': 'Complete essay',
        'note': None,
        'is_completed': False,
        'seconds_worked': 0,
        'completion_date': None 
    }
    prev_created_tasks = 1

    assert result.status_code == 200
    assert result_json['msg'] == None
    assert len(result_json['tasks']) == new_task_amt + 1
    for task in result_json['tasks']:
        task_id = task['id']
        assert task_id >= 1 and task_id <= new_task_amt + prev_created_tasks

        if task_id == 1:
            assert task == first_task
        else:
            task_comp_date = datetime.date.fromisoformat(task['completion_date'])
            assert task['name'] == f'Finish PA{task_id - prev_created_tasks}'
            assert task['note'] == 'That was easy'
            assert task['is_completed'] == True
            assert task['seconds_worked'] == 50921
            assert task_comp_date == today_utc