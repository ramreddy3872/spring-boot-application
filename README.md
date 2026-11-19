# Spring Boot REST API Demo

A lightweight Spring Boot 3 application built with Java 21 showcasing RESTful API endpoints, request validation, and clean layered architecture.

---

## Project Structure

```text
spring-boot-demo/
├── pom.xml
├── mvnw / mvnw.cmd
└── src/
    ├── main/
    │   ├── java/com/example/demo/
    │   │   ├── SpringBootDemoApplication.java   # Application entry point
    │   │   ├── controller/
    │   │   │   ├── HelloController.java         # Health & greeting API
    │   │   │   └── ItemController.java          # Item CRUD REST endpoints
    │   │   ├── model/
    │   │   │   └── Item.java                    # Item data model with validations
    │   │   ├── service/
    │   │   │   └── ItemService.java             # In-memory service logic
    │   │   └── exception/
    │   │       ├── ResourceNotFoundException.java
    │   │       └── GlobalExceptionHandler.java  # Centralized error handler
    │   └── resources/
    │       └── application.properties           # Application configuration
    └── test/
        └── java/com/example/demo/
            ├── SpringBootDemoApplicationTests.java
            └── ItemControllerIntegrationTest.java # MockMvc endpoint tests
```

---

## How to Run

From this directory, run:

```powershell
# Using Maven wrapper
.\mvnw.cmd spring-boot:run

# Or with installed Maven
mvn spring-boot:run
```

The application starts on `http://localhost:8080`.

---

## API Endpoints

### 1. Health & Greeting

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/hello` | Returns a greeting and application status (`UP`) |
| `GET` | `/api/hello?name=John` | Returns a customized greeting |

**Example Request:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/hello?name=Alice" -Method Get
```

---

### 2. Items CRUD API

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/items` | List all items |
| `GET` | `/api/items/{id}` | Get item by ID |
| `POST` | `/api/items` | Create a new item |
| `PUT` | `/api/items/{id}` | Update an existing item |
| `DELETE` | `/api/items/{id}` | Delete an item |

#### Examples (PowerShell)

**List all items:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/items" -Method Get
```

**Get item by ID:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/items/1" -Method Get
```

**Create a new item:**
```powershell
$body = @{
    name = "Smartwatch"
    description = "Fitness tracking smartwatch"
    price = 149.99
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/items" -Method Post -Body $body -ContentType "application/json"
```

**Update an item:**
```powershell
$body = @{
    name = "Smartwatch Ultra"
    description = "Upgraded fitness tracking smartwatch"
    price = 199.99
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/items/1" -Method Put -Body $body -ContentType "application/json"
```

**Delete an item:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/items/1" -Method Delete
```

---

## Running the Tests

Execute:

```powershell
.\mvnw.cmd test
```




lkjhgfds
