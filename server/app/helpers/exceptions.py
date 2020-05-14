class RequestException(Exception):
    def __init__(self, error, code):
        self.exception = error
        self.status_code = code