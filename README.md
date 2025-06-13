# 💸 Expense Tracker API (Spring Boot + PostgreSQL)

This is a real-world backend application for tracking expenses, built using **Spring Boot**, **PostgreSQL**, and **Maven**. It supports RESTful APIs for creating, reading, updating, and deleting expense records.

---

## 📌 Features

- Add new expenses
- View all expenses or by ID
- Filter expenses by date or category
- Update existing expense records
- Delete expenses
- Validation and exception handling
- Unit testing with JUnit
- PostgreSQL persistent storage

---

## 🧰 Prerequisites

- Java 17+
- PostgreSQL
- Maven 3.x
- IntelliJ IDEA (recommended)

---

## ⚙️ Setup Instructions

### 1. ✅ Create Database & User in PostgreSQL

```sql
CREATE DATABASE expense_tracker;
CREATE USER expense_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE expense_tracker TO expense_user;
```


### ⚙️ Configure application.properties
Edit src/main/resources/application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/expense_tracker
spring.datasource.username=expense_user
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
3. 🏗️ Build and Run
Build the project:
```
mvn clean install
Run the application:
mvn spring-boot:run
```
Or run ExpenseTrackerApplication.java from your IDE.

### 📮 API Endpoints Documentation
Method	Endpoint	Description
GET	/api/expenses	Get all expenses
GET	/api/expenses/{id}	Get expense by ID
POST	/api/expenses	Create new expense
PUT	/api/expenses/{id}	Update an existing expense
DELETE	/api/expenses/{id}	Delete expense by ID

### ✅ Testing Instructions
Run unit tests using:
```
mvn test
```
Tests are located in:
src/test/java/
