# Banking API

## Overview

The Banking API is a backend service that provides functionality for managing users, accounts, transactions, bill payments, and security features such as password changes and two-factor authentication (2FA). This API is built using Spring Boot and follows a monolithic architecture with clear separation of concerns across various controllers and services.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - Prerequisites
  - Installation
  - Running the Application
- [API Endpoints](#api-endpoints)
  - Authentication
  - User Management
  - Account Management
  - Transaction Management
  - Bill Management
  - Security Features
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)
- [Author](#author)

## Features

- User Management: Register, update, and delete user accounts.
- Authentication: Secure login/logout functionality with JWT-based authentication.
- Account Management: Manage user accounts, view balances, and retrieve account details.
- Transaction Management: Handle money transfers and view transaction history.
- Bill Payments: Pay bills using account balance.
- Security Features: Change passwords, set up two-factor authentication (2FA), and view security logs.

## Technologies Used

- Java 21
- Spring Boot
- Spring Security (JWT Authentication)
- Hibernate / JPA (Persistence)
- PostgreSQL (Database)
- ModelMapper (DTO to Entity Conversion)
- Swagger (API Documentation)

## Getting Started

### Prerequisites
Ensure you have the following installed:

- Java 17 or higher
- PostgreSQL database
- Maven (for managing dependencies)

### Installation
1. Clone the repository:
```
git clone https://github.com/bdkamaci/banking-api.git
cd banking-api
```
2. Configure Database:
Set up a PostgreSQL database, and update the application.yml or application.properties with your PostgreSQL credentials.
3. Install dependencies:
Run the following command to install the necessary dependencies:
```
mvn clean install
```

### Running the Application
To start the application, run:
```
mvn spring-boot:run
```

Once the application is running, you can access the API documentation at:
```
http://localhost:8080/swagger-ui/index.htm
```

## API Endpoints

### Authentication
- POST /api/auth/login - Login and receive a JWT token
- POST /api/auth/logout - Logout the user (requires JWT)
### User Management
- POST /api/users/register - Register a new user
- GET /api/users/{id} - Get user details by ID
- PUT /api/users/{id} - Update user details
- DELETE /api/users/{id} - Delete a user
### Account & Transaction Management
- GET /api/accounts - Get all accounts for a user
- GET /api/accounts/{id}/balance - Get account balance by ID
- GET /api/accounts/{id}/transactions - Get all transactions for a specific account
- POST /api/accounts/{id}/transfer - Transfer money between accounts
### Bill Management
- GET /api/bills?accountId={accountId} - Get bills for a specific account
- POST /api/bills/pay/{id} - Pay a specific bill
### Security Features
- POST /api/security/two-factor-auth - Set up two-factor authentication
- POST /api/security/change-password - Change user password
- GET /api/security-logs/logs?userId={userId} - Retrieve security logs for a user

## Project Structure
```
banking-api/
│
├── src/
│   ├── main/
│   │   ├── java/tech/bankapi/
│   │   │   ├── controller/               # REST controllers
│   │   │   ├── core.config/              # Configuration Files For Application Settings and Resources
│   │   │   ├── dto/                      # Data Transfer Objects (Request/Response)
│   │   │   ├── exception/                # Data Transfer Objects (Request/Response)
│   │   │   ├── repository/               # Classes for Interaction with the Database
│   │   │   ├── service/                  # Business logic
│   │   │   ├── security/                 # Security-related classes (JWT, Authentication)
│   │   │   └── model/                    # Entities (JPA models)
│   │   └── resources/
│   │       └── application.properties    # Configuration file
│   └── test/                             # Unit tests
│
├── ERD_BankAPI.png                       # Entity Relationship Diagram
├── pom.xml                               # Maven dependencies
└── README.md                             # Project documentation
```

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your proposed changes.

1. Fork the project
2. Create your feature branch (git checkout -b feature/AmazingFeature)
3. Commit your changes (git commit -m 'Add some AmazingFeature')
4. Push to the branch (git push origin feature/AmazingFeature)
5. Open a pull request

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Author

Burcu Doga KAMACI