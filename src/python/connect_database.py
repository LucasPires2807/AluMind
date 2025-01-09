import os
from dotenv import load_dotenv
import psycopg2

def connect_to_postgres():
    """
    Connects to a PostgreSQL database using credentials from a .env file.
    Returns the connection and cursor objects.
    """
    # Load environment variables from .env file
    load_dotenv()

    try:
        # Establish the connection
        connection = psycopg2.connect(
            dbname=os.getenv("DB_NAME"),
            user=os.getenv("DB_USER"),
            password=os.getenv("DB_PASSWORD"),
            host=os.getenv("DB_HOST"),
            port=os.getenv("DB_PORT"),
        )

        # Create a cursor to execute SQL queries
        cursor = connection.cursor()
        print("Connected to PostgreSQL database successfully!")
        
        # Optional: fetch PostgreSQL version
        cursor.execute("SELECT version();")
        db_version = cursor.fetchone()
        print("PostgreSQL database version:", db_version)
        
        return connection, cursor

    except Exception as e:
        print("Error connecting to PostgreSQL database:", e)
        return None, None


def close_postgres_connection(connection, cursor):
    """
    Closes the connection and cursor to the PostgreSQL database.
    """
    try:
        if cursor:
            cursor.close()
            print("Cursor closed.")
        if connection:
            connection.close()
            print("PostgreSQL connection closed.")
    except Exception as e:
        print("Error closing PostgreSQL connection:", e)


# Example usage:
if __name__ == "__main__":
    conn, cur = connect_to_postgres()
    if conn and cur:
        # Perform database operations here
        close_postgres_connection(conn, cur)
