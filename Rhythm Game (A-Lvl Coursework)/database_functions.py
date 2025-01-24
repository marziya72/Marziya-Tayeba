import mysql.connector
from mysql.connector import Error

db_config = {
    'host': 'localhost',
    'database': 'GameData',
    'user': 'root',
    'password': 'star'
}

def create_connection():
    try:
        connection = mysql.connector.connect(**db_config)
        if connection.is_connected():
            return connection
    except Error as e:
        print(f"Error: {e}")
        return None

def username_check(username):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()
            query = "SELECT * FROM users WHERE username = %s"
            cursor.execute(query, (username,))
            result = cursor.fetchone()
            return result is not None
        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.close()
            connection.close()
    return False

def email_check(email):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()
            query = "SELECT * FROM users WHERE email = %s"
            cursor.execute(query, (email,))
            result = cursor.fetchone()
            return result is not None
        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.close()
            connection.close()
    return False

def create_user(username, password, email):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()

            check_query = "SELECT * FROM users WHERE username = %s OR email = %s"
            cursor.execute(check_query, (username, email))
            existing_user = cursor.fetchone()

            if existing_user:
                print("User with the same username or email already exists.")
                return "invalid"

            insert_query = "INSERT INTO users (username, password, email, score) VALUES (%s, %s, %s, %s )"
            cursor.execute(insert_query, (username, password, email, 0))
            connection.commit()
            print("User created successfully!")
            return "valid"

        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.fetchall()
            cursor.close()
            connection.close()
    
    return "invalid"

            
def check_credentials(username, password, email):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()
            query = "SELECT * FROM users WHERE username = %s AND password = %s AND email = %s"
            cursor.execute(query, (username, password, email))
            result = cursor.fetchone()
            return result is not None
        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.close()
            connection.close()
    return False

def checklogin_credentials(username, password):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()
            query = "SELECT * FROM users WHERE BINARY username = %s AND BINARY password = %s"
            cursor.execute(query, (username, password))
            result = cursor.fetchone()
            return result is not None
        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.close()
            connection.close()
    return False

def update_score(username, new_score):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()
            query = "UPDATE users SET score = %s WHERE username = %s"
            cursor.execute(query, (new_score, username))
            connection.commit()
            print("Score updated successfully!")
        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.close()
            connection.close()
            
def get_score(username):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()
            query = "SELECT score FROM users WHERE username = %s"
            cursor.execute(query, (username,))
            result = cursor.fetchone()
            print(f'result from the database: {result}')
            if result:
                return result[0]
            else:
                return 0  
        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.close()
            connection.close()
    return 0 
