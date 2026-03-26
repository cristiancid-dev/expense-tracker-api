# Expense Tracker API

A REST API for managing personal finances built with **Java, Spring Boot and PostgreSQL**.

This project is part of my backend development portfolio and focuses on building a **secure, scalable and production-ready API**, following modern backend best practices.

---

# Project Status

This project is currently **under active development**.

### Completed

- Domain model design (User, Account, Category, Transaction)
- Database schema with Flyway
- PostgreSQL setup using Docker
- JPA entities and relationships
- Repository layer
- Authentication foundation (Spring Security + JWT setup)

### In Progress

- JWT authentication implementation
- AuthService (register & login)
- Security configuration

---

# Project Goals

This project aims to demonstrate:

- Clean backend architecture
- Secure authentication with JWT
- Proper domain modeling
- Scalable REST API design
- Real-world backend development practices

---

# Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL
- Flyway
- Docker
- JWT (jjwt)
- Maven

---

# Architecture

The application follows a layered architecture:

Controller → Service → Repository → Database

---

# Domain Model

## Entities

- User
- Account
- Category
- Transaction

---

# Authentication

The API uses JWT-based authentication.

Authorization header:

Authorization: Bearer <token>

---

# Database

- PostgreSQL (Docker)
- Flyway migrations

---

# Running the Project

1. Start database:

docker compose up -d

2. Run the application:

./mvnw spring-boot:run

API available at:

http://localhost:8080

---

# Project Structure

src/main/java/com/cristiancid/expensetracker

- controller
- service
- repository
- model
- dto
- security

---

# Planned Features

- JWT authentication
- User registration and login
- Account management
- Category management
- Transaction tracking
- Pagination and filtering
- Global exception handling
- Testing

---

# Author

Cristian Cid  
https://github.com/cristiancid-dev
