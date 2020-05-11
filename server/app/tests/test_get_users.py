import json

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
    post_body = json.dumps({
        'username': 'FirstUser',
        'password': 'hello'
    })
    client.post('/users/', content_type='application/json', data=post_body)

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
    new_users = 10
    for i in range(0, new_users):
        post_body = json.dumps({
            "username": f"test{i}",
            "password": "samepassword"
        })
        client.post('/users/', content_type='application/json',
                data=post_body)

    result = client.get('/users/')
    result_json = result.get_json()
    assert result.status_code == 200
    assert result_json['msg'] == None
    assert len(result_json['users']) == (new_users) + 1

    for user in result_json['users']:
        id = user['id']
        assert id >= 1 and id < 102

        if id == 1:
            assert user['username'] == 'FirstUser'
        else:
            assert user['username'] == f'test{id - 2}'