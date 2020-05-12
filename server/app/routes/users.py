import flask
from flask import Blueprint, request

from .. import db
from ..models import user

bp = Blueprint('users', __name__, url_prefix='/users')

@bp.route('/', methods=['GET', 'POST'])
def users_view():
    """Return all users or create a new user.

    Returns:
        For a GET request, returns a flask.Response containing all users. For
        a POST request, returns a flask.Response describing if the creation was
        successful.
    """
    if request.method == 'GET':
        try:
            users = get_users()
            return flask.make_response({'users': users, 'msg': None}, 200)
        except Exception as e:
            return flask.make_response({'users': None, 'msg': e}, 500)
    else:
        try:
            if not request.is_json:
                err_body = {'success': False, 'msg': 'Request body must be JSON'}
                return flask.make_response(err_body, 415)

            username, password, err_msg = validate_new_user_req(request)
            if err_msg is not None:
                return flask.make_response({'success': False, 'msg': err_msg}, 400)

            err_msg = validate_new_user(username, password)
            if err_msg is not None:
                return flask.make_response({'success': False, 'msg': err_msg}, 400)

            create_new_user(username, password)
            return flask.make_response({'success': True, 'msg': None}, 200)
        except Exception as e:
            return flask.make_response({'success': False, 'msg': e}, 500)
        finally:
            db.session.close()

def get_users():
    """Return all users stored in the database.

    Returns:
        A list of dicts where each dict represents a user.
    """
    users = [u.to_dict() for u in user.User.query.all()]
    return users

def validate_new_user_req(request):
    """Check whether the request to create a new user is valid.

    Args:
        request: A flask.Reqeust to create a new user.

    Returns:
        A tuple containing the following:
        1. A str representing the username for the new user or None if there
           was an error with the request.
        2. A str representing the unhashed password for the new user or None if
           there was an error with the reqeust.
        3. A str detailing any error with the request or None if no error was
           found.
    """
    request_json = request.get_json()
    username = request_json.get('username', None)
    password = request_json.get('password', None)
    if username is None:
        return None, None, 'Missing username'
    elif password is None:
        return None, None, 'Missing password'

    return username, password, None

def validate_new_user(username, password):
    """Check whether the data for the new user is valid.

    Args:
        username: A str of the username for the new user.
        password: A str of the unhashed password for the new user.

    Returns:
        A str detailing any error with the data or None if no error was found.
    """
    user_table = db.metadata.tables['user']
    username_max_len = user_table.columns.username.type.length

    if len(username) > username_max_len:
        return f'Username exceeds max length of {username_max_len}'

    if user.User.query.filter_by(username=username).first() is not None:
        return 'Username already exists'

    return None

def create_new_user(username, password):
    """Insert a new user into the database.

    Args:
        username: A str of the username for the new user.
        password: A str of the unhashed password for the new user.
    """
    password_hash = user.User.genr_password_hash(password)
    new_user = user.User(username=username, password=password_hash)

    db.session.add(new_user)
    db.session.commit()