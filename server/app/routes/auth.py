import flask
from flask import Blueprint, request
from flask_jwt_extended import (
    create_access_token, create_refresh_token
)

from .. import db
from ..helpers.exceptions import RequestException
from ..models.user import User

bp = Blueprint('auth', __name__, url_prefix='/auth')

@bp.route('/login/', methods=['GET'])
def login():
    """Check if the user's credentials are valid and return tokens.

    Returns:
        A flask.Response containing the user's access and refresh tokens.
    """
    try:
        if not request.is_json:
            err_body = {'msg': 'Request body must be JSON',
                    'access_token': None, 'refresh_token': None}
            return flask.make_response(err_body, 415)
            
        username, password = validate_login_req(request)
        access_token, refresh_token = login_user(username, password)
        return flask.make_response({'msg': None, 'access_token': access_token,
                'refresh_token': refresh_token})
    except RequestException as re:
        return flask.make_response({'msg': re.exception, 'access_token': None,
                'refresh_token': None}, re.status_code)
    except Exception as e:
        return flask.make_response({'msg': e, 'access_token': None,
                'refresh_token': None}, 500)

@bp.route('/refresh/', methods=['GET'])
def refresh():
    """Generate a new access token from a refresh token.

    Returns:
        A flask.Response containing the user's access and refresh tokens.
    """
    return flask.make_response({'msg': 'Hello'}, 200)

def validate_login_req(request):
    """Check whether the request to login is valid.

    Args:
        request: A flask.Request to login.

    Returns:
        A tuple containing the following:
        1. A str representing the username of the user to login.
        2. A str representing the password of the user to login.
    
    Raises:
        RequestException: if the username or password is not provided or if
                          the username is over the max length.
    """
    request_json = request.get_json()
    username = request_json.get('username', None)
    password = request_json.get('password', None)
    if username is None:
        raise RequestException('Missing username', 400)
    elif password is None:
        raise RequestException('Missing password', 400)

    user_table = db.metadata.tables['user']
    username_max_len = user_table.columns.username.type.length
    if len(username) > username_max_len:
        err_str = f'Username exceeds max length of {username_max_len}'
        raise RequestException(err_str, 400)

    return username, password

def login_user(username, password):
    """Return access and refresh tokens for the user.

    Args:
        username: A str of the username for the new user.
        password: A str of the unhashed password for the new user.
    
    Returns:
        A tuple containing the following:
        1. A str representing a new access token for the user.
        2. A str representing a new refresh token for the user.

    Raises:
        RequestException: if the username or password is invalid.
    """
    matching_user = User.query.filter_by(username=username).first()
    if matching_user is not None \
            and matching_user.check_password_hash(password):
        access_token = create_access_token(identity=username)
        refresh_token = create_refresh_token(identity=username)
        return access_token, refresh_token 
    else:
        raise RequestException('Invalid username or password', 401)