from werkzeug.security import generate_password_hash, check_password_hash

from .. import db

class User(db.Model):
    """The account for a user of the application.

    Attributes:
        id: A db.Column for the user id.
        username: A db.Column for the username of the account.
        password: A db.Column for the password for the account.
        tasks: A db.relationship for all tasks belonging to the user.
    """
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(100), nullable=False)
    tasks = db.relationship('Task', backref='user')

    @staticmethod
    def genr_password_hash(password):
        """Hash the given password.

        Args:
            password (str): the password to hash

        Returns:
            A str representing the hashed password.
        """
        return generate_password_hash(password)

    def check_password_hash(self, password):
        """Check if the given password matches this user's password.

        Args:
            password (str): the unhashed password to check

        Returns:
            True if the given password matches the user's password, false
            otherwise.
        """
        return check_password_hash(self.password, password)

    def to_dict(self):
        """Return a dictionary representation of the user.

        Returns:
            A dict representing the non-secret characteristics of the user.
        """
        return {'id': self.id, 'username': self.username}

    def __repr__(self):
        """Return a string representation of the user.

        Returns:
            A str containing the username.
        """
        return f'<User {self.username}>'