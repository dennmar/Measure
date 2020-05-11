import pytest

from .. import create_app

@pytest.fixture(scope='module')
def client():
    """Return a test client for the application."""
    app = create_app("test_config.py")

    with app.test_client() as client: 
        yield client

    # TODO: drop the test database table