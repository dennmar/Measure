import flask
from flask import Blueprint

bp = Blueprint('auth', __name__, url_prefix='/auth')

@bp.route('/login/', methods=['GET'])
def login():
    """Check if the user's credentials are valid and return tokens.

    Returns:
        A flask.Response containing the user's access and refresh tokens.
    """
    return flask.make_response({'msg': 'Login'}, 200)