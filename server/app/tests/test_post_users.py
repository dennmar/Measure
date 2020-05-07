from .conftest import client

# Tests for /users/ (POST)

def test_create(client):
    """Test creating the first and only user."""
    result = client.post('/users/', data={
        'username': '393aelkja0',
        'password': 'panda'
    })

    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json == {
        'success': True,
        'msg': None
    }

    users_result = client.get('/users/')
    users_json = users_result.get_json()
    assert users_json['users'] == [{
        'id': 1,
        'username': '393aelkja0'
    }]

def test_non_json_body(client):
    """Test sending a non-JSON body in the request."""
    bad_content_type = 'application/x-www-form-urlencoded'
    result = client.post('/users/', content_type=bad_content_type, data={
        'username': 'TinFoiL',
        'password': 'hat'
    })

    result_json = result.get_json()
    assert result.status_code == 415
    assert result_json == {
        'success': False,
        'msg': 'Request must be in JSON'
    }

def test_missing_username(client):
    """Test creating a user without a username."""
    result = client.post('/users/', data={
        'password': 'rinse'
    })

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json == {
        'success': False,
        'msg': 'Missing username'
    }
    
    users_result = client.get('/users/')
    users_json = users_result.get_json()
    assert users_json['users'] == [{
        'id': 1,
        'username': '393aelkja0'
    }]

def test_missing_password(client):
    """Test creating a user without a password."""
    result = client.post('/users/', data={
        'username': '5100'
    })

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json == {
        'success': False,
        'msg': 'Missing password'
    }
    
    users_result = client.get('/users/')
    users_json = users_result.get_json()
    assert users_json['users'] == [{
        'id': 1,
        'username': '393aelkja0'
    }]

def test_dup_username(client):
    """Test creating a user with the same username as an existing user."""
    result = client.post('/users/', data={
        'username': '393aelkja0',
        'password': 'iceberg'
    })

    result_json = result.get_json()
    assert result.status_code == 400
    assert result_json == {
        'success': False,
        'msg': 'Username already exists'
    }
    
    users_result = client.get('/users/')
    users_json = users_result.get_json()
    assert users_json['users'] == [{
        'id': 1,
        'username': '393aelkja0'
    }]

def test_dup_password(client):
    """Test creating a user with the same password as an existing user."""
    result = client.post('/users/', data={
        'username': 'Qualify',
        'password': 'panda'
    })

    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json == {
        'success': True,
        'msg': None
    }

    users_result = client.get('/users/')
    users_json = users_result.get_json()
    assert len(users_json['users']) == 2
    for user in users_json['users']:
        if user['id'] == 1:
            assert user['username'] == '393aelkja0'
        elif user['id'] == 2:
            assert user['username'] == 'Qualify'
        else:
            assert False

def test_create_mult(client):
    """Test creating multiple users."""
    new_users = 100
    for i in range(0, new_users):
        result = client.post('/users/', data={
            'username': f'oceanwave20{i}',
            'password': f'pqka{i}'
        })
        result_json = result.get_json()
        assert result.status_code == 200
        assert result_json == {
            'success': True,
            'msg': None
        }

    users_result = client.get('/users')
    users_json = users_result.get_json()
    assert len(users_json['users']) == (new_users + 1)