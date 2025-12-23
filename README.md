# Banking App

A simple banking application built with Spring Boot, PostgreSQL, and a basic HTML/JS frontend. This application allows users to create accounts, log in, view their balance, transfer money to other users, and view their transaction history.

## Features

*   **User Registration**: Create a new account with an email and password. New accounts start with a balance of $1000.
*   **Authentication**: Simple login mechanism.
*   **Money Transfer**: Transfer funds between accounts (atomic transactions).
*   **Transaction History**: View a list of past transactions (sent and received).
*   **Dashboard**: Real-time view of balance and history.

## Technologies

*   **Backend**: Java 17, Spring Boot 3 (Web, Data JPA)
*   **Database**: PostgreSQL 15 (running in Docker)
*   **Frontend**: HTML, CSS, Vanilla JavaScript
*   **Build Tool**: Maven

## Prerequisites

*   Java 17 or higher
*   Docker & Docker Compose
*   Maven (optional, wrapper included)

## Getting Started

### 1. Start the Database

The application uses a PostgreSQL database running in a Docker container.

```bash
docker-compose up -d
```

This will start a PostgreSQL instance on port `5432` with the following credentials (defined in `docker-compose.yml`):
*   **Database**: `banking_db`
*   **User**: `bank_user`
*   **Password**: `bank_password`

### 2. Run the Application

You can run the application using the Maven wrapper or your IDE.

**Using Maven Wrapper:**

```bash
./mvnw spring-boot:run
```

**Using IDE (IntelliJ / Android Studio):**
Run the `BankingAppApplication` class located in `src/main/java/com/bank/app/BankingAppApplication.java`.

The application will start on `http://localhost:8080`.

### 3. Access the App

Open your web browser and navigate to:

[http://localhost:8080](http://localhost:8080)

## API Endpoints

The backend exposes the following REST endpoints:

*   `POST /api/register`: Create a new account.
    *   Body: `{ "email": "user@example.com", "password": "password" }`
*   `POST /api/login`: Authenticate a user.
    *   Body: `{ "email": "user@example.com", "password": "password" }`
*   `POST /api/transfer`: Transfer money.
    *   Body: `{ "senderId": 1, "receiverEmail": "other@example.com", "amount": 100 }`
*   `GET /api/history/{accountId}`: Get transaction history for an account.
*   `GET /api/account/{accountId}`: Get current account details.

## Project Structure

```
src/
├── main/
│   ├── java/com/bank/app/
│   │   ├── controller/       # REST Controllers
│   │   ├── entity/           # JPA Entities (Account, Transaction)
│   │   ├── repository/       # Data Access Layer
│   │   ├── service/          # Business Logic
│   │   └── BankingAppApplication.java
│   └── resources/
│       ├── static/           # Frontend (index.html)
│       └── application.properties
├── docker-compose.yml        # Database configuration
└── pom.xml                   # Dependencies
```
