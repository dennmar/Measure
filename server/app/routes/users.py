import flask
from flask import Blueprint, request

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
        return 'GET - All users'
    else:
        return 'POST - Create user'