import flask
from flask import Blueprint, request
from flask_jwt_extended import (
    get_jwt_identity, jwt_required
)

bp = Blueprint('tasks', __name__, url_prefix='/tasks')

@bp.route('/', methods=['GET', 'POST'])
@jwt_required
def task_view():
    if request.method == 'GET':
        return 'GET task'
    else:
        return 'POST task'

@bp.route('/<int:task_id>', methods=['PUT', 'DELETE'])
@jwt_required
def specific_task_view(task_id):
    if request.method == 'PUT':
        return f'PUT task {task_id}'
    else:
        return f'DELETE task {task_id}'