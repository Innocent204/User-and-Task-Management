# User and Task Management System

A secure, production-ready RESTful web application built with Java Spring Boot. This system allows registered users to manage their personal tasks, with support for user registration, JWT-based login, and role-based access control (USER and ADMIN roles).

## Features

- **User Registration & Login**: Register and authenticate using JWT tokens.
- **Role-Based Access Control**: USERs manage their own tasks; ADMINs can manage all users and tasks.
- **Task Management**: Create, retrieve, update, and delete personal tasks.
- **Task Filtering & Sorting**: Filter/sort tasks by status, due date, and creation date.
- **Admin Privileges**: List all users, view or delete any user's tasks.

## Getting Started

### Prerequisites
- Java 17+
- Maven or Gradle
- (Optional) Postman or curl for API testing

### Running the Application

1. Clone the repository:
   ```sh
   git clone <your-repo-url>
   cd User-and-Task-Management-System
   ```
2. Build and run:
   ```sh
   ./mvnw spring-boot:run
   # or
   mvn spring-boot:run
   ```
   The app will start at `http://localhost:8080/`

## API Usage

### 1. Register a User
- **POST** `/api/auth/register`
- **Body:**
  ```json
  {
    "username": "user1",
    "password": "password123",
    "roles": ["USER"]
  }
  ```

### 2. Login
- **POST** `/api/auth/login`
- **Body:**
  ```json
  {
    "username": "user1",
    "password": "password123"
  }
  ```
- **Returns:** JWT token

### 3. Task Endpoints (Authenticated)
- **Create Task:** `POST /api/tasks`
- **List Tasks:** `GET /api/tasks?status=TODO&sortBy=dueDate`
- **Get Task:** `GET /api/tasks/{id}`
- **Update Task:** `PUT /api/tasks/{id}`
- **Delete Task:** `DELETE /api/tasks/{id}`
- **Headers:**
  - `Authorization: Bearer <JWT>`

### 4. Admin Endpoints (ADMIN role)
- **List Users:** `GET /api/admin/users`
- **View User Tasks:** `GET /api/admin/users/{userId}/tasks`
- **Delete User Task:** `DELETE /api/admin/users/{userId}/tasks/{taskId}`

## Roles
- `USER`: Can manage own tasks
- `ADMIN`: Can manage all users and tasks

## Technologies Used
- Java 17+
- Spring Boot
- Spring Security (JWT)
- JPA/Hibernate
- H2/MySQL/PostgreSQL (configurable)

## Notes
- All endpoints except `/` and `/api/auth/**` require authentication.
- Use Postman or curl for testing API endpoints.

## License
MIT
