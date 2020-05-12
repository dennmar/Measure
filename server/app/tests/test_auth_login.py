import json

from .conftest import client
from .validators.tokens import is_valid_access_token, is_valid_refresh_token

def test_success(client):
    """Test a successful login."""
    user_info = json.dumps({
        'username': 'logintester23',
        'password': 'omelet'
    })
    create_result = client.post('/users/', content_type='application/json',
            data=user_info)
    result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    result_json = result.get_json()
    assert result.status_code == 200
    assert is_valid_access_token(result['access_token'])
    assert is_valid_refresh_token(result['refresh_token'])
    assert result['msg'] == None

def test_non_json_body(client):
    """Test sending a non-JSON body in the request."""
    result = client.get('/auth/login/', data={
        'username': 'When',
        'password': 'where'
    })

    result_json = result.get_json()
    assert result.status_code == 415
    assert result_json == {
        'success': False,
        'msg': 'Request body must be JSON',
        'access_token': None,
        'refresh_token': None
    }

def test_wrong_username(client):
    """Test logging in with the wrong username and an existing password."""
    user_info = json.dumps({
        'username': 'logintester230',
        'password': 'omelet'
    })
    result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    result_json = result.get_json()
    assert result.status_code == 401
    assert result['msg'] == 'Invalid username or password'
    assert result['access_token'] == None
    assert result['refresh_token'] == None

def test_wrong_password(client):
    """Test logging in with the wrong password and an existing username."""
    user_info = json.dumps({
        'username': 'logintester23',
        'password': 'eggs'
    })
    result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    result_json = result.get_json()
    assert result.status_code == 401
    assert result['msg'] == 'Invalid username or password'
    assert result['access_token'] == None
    assert result['refresh_token'] == None

def test_nonexistent(client):
    """Test logging in with an account that does not exist."""
    user_info = json.dumps({
        'username': 'dragon',
        'password': 'sphere'
    })
    result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    result_json = result.get_json()
    assert result.status_code == 401
    assert result['msg'] == 'Invalid username or password'
    assert result['access_token'] == None
    assert result['refresh_token'] == None

def test_multiple(client):
    """Test getting tokens for multiple accounts."""
    new_users = 5
    for i in range(0, new_users):
        post_body = json.dumps({
            'username': f'parka{i}',
            'password': f'{i}uno'
        }) 
        result = client.post('/users/', content_type='application/json',
                data=post_body)

        result_json = result.get_json()
        assert result.status_code == 200
        assert result_json == {
            'success': True,
            'msg': None
        }

    for i in range(0, new_users):
        user_info = json.dumps({
           'username': f'parka{i}',
           'password': f'{i}uno'
        })
        result = client.get('/auth/login/', content_type='application/json',
                data=user_info)

        assert result.status_code == 200
        assert is_valid_access_token(result['access_token'])
        assert is_valid_refresh_token(result['refresh_token'])
        assert result['msg'] == None