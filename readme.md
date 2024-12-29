# Spring Auth Backend - Setup and Run

## Prerequisites

Before running the project, ensure the following tools are installed on your machine:

- Java 17+ (for Spring Boot)
- Docker (for containerization)

## Setup

### 1. Clone the Repository

Clone the repository to your local machine.

```bash
git clone https://your-repository-url
cd your-repository-directory
```

### 2. Docker Compose Setup

In the project directory, you should already have the `docker-compose.yml` file. This file defines the services required for the project:

- **PostgreSQL**: The database for the Spring Boot app.
- **pgAdmin**: A GUI tool for managing the PostgreSQL database.
- **MailDev**: A tool for handling and viewing test emails.
- **Spring Boot App**: The backend application.

### 3. Build and Start the Services

To build and start all the services defined in `docker-compose.yml`, run the following command:

```bash
docker-compose up --build
```

### 4. Access the Services

- **Spring Boot Application**: The Spring Boot app will be available at `http://localhost:8081`.
- **pgAdmin**: Access the pgAdmin web UI at `http://localhost:5050`, with the following credentials:
    - Email: `admin@example.com`
    - Password: `admin`
- **MailDev**: Access the MailDev interface at `http://localhost:1080` for checking the emails sent by the application.

### 5. Stopping the Services

To stop the running services, use the following command:

```bash
docker-compose down
```

---