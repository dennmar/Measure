from .. import db

class Task(db.Model):
    """A completable task for a user.

    Attributes:
        id: A db.Column for the task id.
        user_id: A db.Column for the id of the user it is associated with.
        name: A db.Column for the name of the task.
        note: A db.Column for notes about the task.
        is_complete: A db.Column for whether the task is completed.
        seconds_worked: A db.Column for the amount of time working on the task.
        completion_date: A db.Column for the date the task was completed.
    """
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    name = db.Column(db.String(50), nullable=False)
    note = db.Column(db.String(500), nullable=True)
    is_completed = db.Column(db.Boolean, nullable=False)
    seconds_worked = db.Column(db.Integer, nullable=False)
    completion_date = db.Column(db.Date, nullable=True)

    def to_dict(self):
        """Return a dictionary representation of the task.

        Returns:
            A dict representing the characteristics of the task.
        """
        dict_repr = {
            'id': self.id,
            'name': self.name,
            'note': self.note,
            'is_completed': self.is_completed,
            'seconds_worked': self.seconds_worked,
        }
        if self.completion_date is not None:
            dict_repr['completion_date'] = self.completion_date.isoformat()
        else:
            dict_repr['completion_date'] = None

        return dict_repr