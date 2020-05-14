import json

from .conftest import client
from .validators.tokens import is_valid_access_token, is_valid_refresh_token
from .. import db

def test_success(client):
    """Test a successful login."""
    username = 'logintester23'
    user_info = json.dumps({
        'username': username,
        'password': 'omelet'
    })
    create_result = client.post('/users/', content_type='application/json',
            data=user_info)
    result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    result_json = result.get_json()
    assert result.status_code == 200
    assert is_valid_access_token(result_json['access_token'], username)
    assert is_valid_refresh_token(result_json['refresh_token'], username)
    assert result_json['msg'] == None

def test_non_json_body(client):
    """Test sending a non-JSON body in the request."""
    result = client.get('/auth/login/', data={
        'username': 'When',
        'password': 'where'
    })

    result_json = result.get_json()
    assert result.status_code == 415
    assert result_json == {
        'msg': 'Request body must be JSON',
        'access_token': None,
        'refresh_token': None
    }

def test_missing_username(client):
    """Test logging in a user without a username."""
    user_info = json.dumps({'password': 'hey'})
    result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['msg'] == 'Missing username'
    assert result_json['access_token'] == None
    assert result_json['refresh_token'] == None

def test_missing_password(client):
    """Test logging in a user without a password."""
    user_info = json.dumps({'username': 'underwater'})
    result = client.get('/auth/login/', content_type='application/json',
            data=user_info)

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['msg'] == 'Missing password'
    assert result_json['access_token'] == None
    assert result_json['refresh_token'] == None

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
    assert result_json['msg'] == 'Invalid username or password'
    assert result_json['access_token'] == None
    assert result_json['refresh_token'] == None

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
    assert result_json['msg'] == 'Invalid username or password'
    assert result_json['access_token'] == None
    assert result_json['refresh_token'] == None

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
    assert result_json['msg'] == 'Invalid username or password'
    assert result_json['access_token'] == None
    assert result_json['refresh_token'] == None

def test_over_max_username(client):
    """Test logging in a user with a username that's too long."""
    user_table = db.metadata.tables['user']
    username_max_len = user_table.columns.username.type.length
    post_body = json.dumps({
        'username': 'b' * (username_max_len + 1),
        'password': 'scorch'
    })
    result = client.get('/auth/login/', content_type='application/json',
            data=post_body)

    exp_msg = f'Username exceeds max length of {username_max_len}'
    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json['msg'] == exp_msg 
    assert result_json['access_token'] == None
    assert result_json['refresh_token'] == None

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

    for i in range(0, new_users):
        username = f'parka{i}'
        user_info = json.dumps({
           'username': username,
           'password': f'{i}uno'
        })
        result = client.get('/auth/login/', content_type='application/json',
                data=user_info)

        result_json = result.get_json()
        assert result.status_code == 200
        assert is_valid_access_token(result_json['access_token'], username)
        assert is_valid_refresh_token(result_json['refresh_token'], username)
        assert result_json['msg'] == None