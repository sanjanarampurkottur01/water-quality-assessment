# Use the official Python image as a base
FROM python:3.9-slim

# Set environment variables
ENV AWS_ACCESS_KEY_ID=your_access_key_id
ENV AWS_SECRET_ACCESS_KEY=your_secret_access_key
ENV AWS_SESSION_TOKEN=your_secret_access_key
ENV AWS_REGION=us-east-1

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file first to leverage Docker's caching mechanism
COPY requirements.txt .

# Install dependencies
RUN pip install -r requirements.txt

# Copy the entire contents of the current directory to the container
COPY . .

# Expose port 80
EXPOSE 80

# Command to run the Flask application
CMD ["python", "server.py"]