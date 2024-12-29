# Spring Auth Backend

## Prerequisites

- **Java 17+**
- **Docker**
- **Maven**

## Setup

### 1. Clone the Repository

```bash
git clone https://your-repository-url
cd your-repository-directory
```

### 2. Start the Services (Docker)

Run this command to build and start the services:

```bash
docker-compose up --build
```

### 3. Run Spring Boot Locally (Without Docker)

If you prefer to run the Spring Boot app locally instead of inside the container, use the following Maven command:

```bash
mvn spring-boot:run
```

This will start the Spring Boot application on `http://localhost:8081`.

### 4. Stop the Services

To stop the Docker services:

```bash
docker-compose down
```

---
