import pytest
from flask import Flask

from .. import create_app

test_config_file = 'test_config.py'

@pytest.fixture(scope='module')
def client():
    """Return a test client for the application."""
    app = create_app(test_config_file)

    with app.test_client() as client: 
        yield client

    # TODO: drop the test database table

@pytest.fixture(scope='session')
def test_config():
    config_app = Flask(__name__, instance_relative_config=True)
    config_app.config.from_pyfile(test_config_file)
    return config_app.config