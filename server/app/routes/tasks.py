import flask
import datetime
from flask import Blueprint, request
from flask_jwt_extended import (
    get_jwt_identity, jwt_required
)

from .. import db
from ..models.user import User
from ..models.task import Task
from ..helpers.exceptions import RequestException

bp = Blueprint('tasks', __name__, url_prefix='/tasks')

@bp.route('/', methods=['GET', 'POST'])
@jwt_required
def task_view():
    if request.method == 'GET':
        try:
            username = get_jwt_identity()
            tasks = find_tasks(username)
            return flask.make_response({'msg': None, 'tasks': tasks}, 200)
        except RequestException as re:
            return flask.make_response({'msg': re.exception, 'tasks': None},
                    re.status_code)
        except Exception as e:
            return flask.make_response({'msg': e, 'tasks': None}, 500)
    else:
        try:
            username = get_jwt_identity()
            new_task = validate_create_task_req(request, username)
            add_task(new_task)
            return flask.make_response({'msg': None, 'success': True}, 200)
        except RequestException as re:
            return flask.make_response({'msg': re.exception, 'success': False},
                    re.status_code)
        except Exception as e:
            return flask.make_response({'msg': e, 'tasks': None}, 500)
        finally:
            db.session.close()

@bp.route('/<int:task_id>', methods=['PUT', 'DELETE'])
@jwt_required
def specific_task_view(task_id):
    if request.method == 'PUT':
        return f'PUT task {task_id}'
    else:
        return f'DELETE task {task_id}'

def find_tasks(username):
    """Return tasks for the user with the matching username.

    Args:
        username: A str of the user's username to find tasks for.

    Returns:
        A list of dicts where each dict represents a task.

    Raises:
        RequestException: if there is no matching user in the database.
    """
    matching_user = User.query.filter_by(username=username).first()
    if matching_user is not None:
        return [task.to_dict() for task in matching_user.tasks]
    else:
        raise RequestException('User does not exist', 401)

def validate_create_task_req(request, username):
    """Check whether the request to create a task is valid.

    Args:
        request: A flask.Request to login.
        username: A str of the username of the user trying to create the task.

    Returns:
        A Task object for the task to create, belonging to the given user.

    Raises:
        RequestException: if the content type of the body is not JSON,
                          a mandatory field is missing, or the user doesn't
                          exist.
    """
    if not request.is_json:
        raise RequestException('Request body must be JSON', 415)

    request_json = request.get_json()
    name = request_json.get('name', None)
    note = request_json.get('note', None)
    is_completed = request_json.get('is_completed', None)
    seconds_worked = request_json.get('seconds_worked', None)
    completion_date = request_json.get('completion_date', None)

    if name is None:
        raise RequestException('Missing name', 400)
    elif is_completed is None:
        raise RequestException('Missing is_completed', 400)
    elif seconds_worked is None:
        raise RequestException('Missing seconds_worked', 400)

    # convert str representation of date to date object
    if completion_date is not None:
        completion_date = datetime.date.fromisoformat(completion_date)

    matching_user = User.query.filter_by(username=username).first()
    if matching_user is not None:
        return Task(name=name, user_id=matching_user.id, note=note,
                is_completed=is_completed, seconds_worked=seconds_worked,
                completion_date=completion_date)
    else:
        raise RequestException('User does not exist', 401)

def add_task(task):
    """Store the task in the database.

    Args:
        task: A Task object of the task to store.
    """
    db.session.add(task)
    db.session.commit()