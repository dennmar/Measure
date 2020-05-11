import os
from flask import Flask
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

def create_app(config_file='config.py'):
    """Create the Flask application.

    Configure and set-up the application.

    Args:
        config_file: A str of the config file's name in the instance folder.

    Returns:
        A flask.Flask object representing the configured Flask application.
    """
    app = Flask(__name__, instance_relative_config=True)
    app.config.from_pyfile(config_file)
    app.config.update(SECRET_KEY=os.urandom(24))
    db.init_app(app)

    # register routes
    from .routes import users as user_routes
    app.register_blueprint(user_routes.bp)

    # reset database and create tables
    from .models import user as user_model
    with app.app_context():
        db.drop_all()
        db.create_all()
    
    return app