import os
from flask import Flask

def create_app(test_config=None):
    """Create the Flask application.

    Configure and set-up the application.

    Args:
        test_config: A dictionary for the test configuration.

    Returns:
        A flask.Flask object representing the configured Flask application.
    """
    app = Flask(__name__, instance_relative_config=True)

    if test_config is None:
        app.config.from_pyfile('config.py')
    else:
        app.config.from_mapping(test_config)

    app.config.update(SECRET_KEY=os.urandom(24))

    from .routes import users as user_routes
    app.register_blueprint(user_routes.bp)
    
    return app