import json
import time
import datetime

from .conftest import client
from .validators.tokens import is_valid_refresh_token

def test_success(client):
    """Test normal refresh."""
    username = 'pinetrees'
    user_info = json.dumps({
        'username': username,
        'password': '1000triumph'
    })
    create_result = client.post('/users/', content_type='application/json',
            data=user_info)
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    
    login_json = login_result.get_json()
    refresh_token = login_json['refresh_token']
    result = client.get('/auth/refresh/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + refresh_token})

    result_json = result.get_json()
    assert result.status_code == 200
    assert is_valid_access_token(result_json['access_token'], username)
    assert result_json['msg'] == None

def test_expired_refresh(client, test_config):
    """Test refreshing with an expired refresh token."""
    user_info = json.dumps({
        'username': 'pinetrees',
        'password': '1000triumph'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    login_json = login_result.get_json()
    refresh_token = login_json['refresh_token']
    refresh_exp = test_config['JWT_REFRESH_TOKEN_EXPIRES']
    exp_time = datetime.datetime.now() + refresh_exp
    sleep_time = exp_time - datetime.datetime.now()
    time.sleep(sleep_time.total_seconds() + 1)
    
    result = client.get('/auth/refresh/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + refresh_token})
    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json == {
        'msg': 'Provided refresh token has expired',
        'access_token': None
    }

def test_pass_access(client):
    """Test passing an access token as the refresh token."""
    user_info = json.dumps({
        'username': 'pinetrees',
        'password': '1000triumph'
    })
    login_result = client.get('/auth/login/', content_type='application/json',
            data=user_info)
    login_json = login_result.get_json()
    access_token = login_json['access_token']

    result = client.get('/auth/refresh/', content_type='application/json',
            headers={'Authorization': 'Bearer ' + access_token})
    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json == {
        'msg': 'Invalid refresh token',
        'access_token': None
    }

def test_non_token(client):
    """Test passing a string that is not a token."""
    result = client.get('/auth/refresh/', content_type='application/json',
            headers={'Authorization': 'Bearer abcdnontoken'})
    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json == {
        'msg': 'Invalid refresh token',
        'access_token': None
    }