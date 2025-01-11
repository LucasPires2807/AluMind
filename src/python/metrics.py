from dotenv import load_dotenv
import os
from connect_postgres import connect_to_postgres, close_postgres_connection

def fetch_predictions():
    """
    Fetches predictions from the sentiment_analysis table in the database.
    Returns a list of tuples (predictedCorrectly).
    """
    load_dotenv()  # Load environment variables from .env file

    try:
        # Connect to PostgreSQL database
        connection, cursor = connect_to_postgres()
        if not connection or not cursor:
            return []

        # Query to fetch predictedCorrectly values
        query = "SELECT predicted_correctly FROM public.sentiment_analysis;"
        cursor.execute(query)
        predictions = cursor.fetchall()

        print(f"Fetched {len(predictions)} records from the database.")
        return predictions

    except Exception as e:
        print("Error fetching data from PostgreSQL:", e)
        return []

    finally:
        if 'cursor' in locals() and 'connection' in locals():
            close_postgres_connection(connection, cursor)


def calculate_accuracy(predictions):
    """
    Calculates the accuracy of the model.
    :param predictions: List of tuples containing predictedCorrectly values (e.g., [(True,), (False,)])
    :return: Accuracy as a percentage.
    """
    if not predictions:
        return 0.0

    total = len(predictions)
    correct = sum(1 for prediction in predictions if prediction[0])  # Count True values
    accuracy = (correct / total) * 100
    return accuracy


if __name__ == "__main__":
    # Fetch predictions from the database
    predictions = fetch_predictions()

    # Calculate accuracy
    accuracy = calculate_accuracy(predictions)

    # Print the result
    print(f"Model Accuracy: {accuracy:.2f}%")
