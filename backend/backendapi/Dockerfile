# Use the official Python image as a base
FROM python:3.9-slim

# Set environment variables
ENV AWS_ACCESS_KEY_ID=your_access_key_id
ENV AWS_SECRET_ACCESS_KEY=your_secret_access_key
ENV AWS_SESSION_TOKEN=your_secret_access_key
ENV AWS_REGION=us-east-1

# Install dependencies
RUN pip install -r ./requirements.txt

# Copy application code
COPY server.py .

# Expose port
EXPOSE 80

# Command to run the Flask application
CMD ["python", "server.py"]
