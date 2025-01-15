# Use the official Python 3.10 image as the base
FROM python:3.10.12

# Set the working directory inside the container
WORKDIR /app

# Copy the current directory's contents into the container
COPY . .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Expose the port that Flask runs on
EXPOSE 5000

# Command to start the Flask app
CMD ["python", "api.py"]
