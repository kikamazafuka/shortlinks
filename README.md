# URL Shortener Service

This is a URL Shortening service built using Spring Boot that allows users to shorten URLs and retrieve the short URL using a REST API.

## Features
- Save the abridged version of provided URL through REST API using a POST request.
- Retrieve generated shortened URL through REST API using a GET request.
- Error handling for invalid shortened URLs.
- Swagger API documentation for easy testing.


## Technologies Used
- **Java**: Programming language used to build the application.
- **Spring Boot**: Framework for building the REST API.
- **PostgreSQL**: Database for storing URL data.
- **Docker**: Containerization tool for deployment.
- **Maven**: Dependency management and build tool.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/kikamazafuka/shortlinks.git

2. **Set Up PostgreSQL**:
   You can run PostgreSQL locally

3. **Configure Application Properties**:
   Update the application.properties or application-docker.properties file to connect to your PostgreSQL database.

4. **Build the Application**

5. **Run the Application**

6. **Accessing the API**:
   The application will be running on http://localhost:8080/api/shortenUrl
   