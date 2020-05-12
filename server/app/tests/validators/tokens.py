from flask_jwt_extended import decode_token

def is_valid_access_token(token, expected_id):
    """Check whether the JWT token is a valid access token.

    Args:
        token (str): The JWT token to check.

    Returns:
        True if it is a valid access token; false otherwise.
    """
    access_token = decode_token(token)
    valid_type = access_token['type'] == 'access'
    valid_id = access_token['identity'] == expected_id
    return valid_type and valid_id 

def is_valid_refresh_token(token, expected_id):
    """Check whether the JWT token is a valid refresh token.

    Args:
        token (str): The JWT token to check.

    Returns:
        True if it is a valid refresh token; false otherwise.
    """
    refresh_token = decode_token(token)
    valid_type = access_token['type'] == 'refresh'
    valid_id = access_token['identity'] == expected_id
    return valid_type and valid_id 