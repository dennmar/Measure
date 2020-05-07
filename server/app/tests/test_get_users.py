from .conftest import client

# Tests for /users/ (GET)

def test_empty(client):
    """Test with no users in the database."""
    result = client.get('/users/')
    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json == {
        'users': [],
        'msg': None
    }

def test_single(client):
    """Test with one user in the database."""
    client.post('/users/', data={
        'username': 'FirstUser',
        'password': 'hello'
    })

    result = client.get('/users/')
    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json == {
        'users': [{
            'id': 1,
            'username': 'FirstUser'
        }],
        'msg': None
    }

def test_multiple(client):
    """Test with multiple users in the database."""
    for i in range(0, 100):
        client.post('/users/', data={
            'username': f'test{i}',
            'password': 'samepassword'
        })

    result = client.get('/users/')
    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json['msg'] == None

    for user in result_json['users']:
        id = user['id']
        assert id >= 1 and id < 102

        if id == 1:
            assert user['username'] == 'FirstUser'
        else:
            assert user['username'] == f'test{id - 2}'