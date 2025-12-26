# Tasker API

REST API built with Spring Boot for task management with JWT authentication, roles and protected access.

## 🚀 Technologies
- Spring Boot 3
- Spring Security 6 (JWT, roles, custom filters)
- JPA + Hibernate
- PostgreSQL / Docker
- Swagger OpenAPI 3
- Maven

## ✨ Features
- User registration and login with JWT
- USER and ADMIN roles
- Personal task management
- User-scoped CRUD
- Admin panel:
  - List all users
  - List all tasks
  - Delete users with safe task cascade
- Bean Validation
- Global error handling

## 🔑 Main Endpoints
### Auth
- POST `/api/auth/register`
- POST `/api/auth/login`

### Tasks (USER)
- GET `/api/tasks`
- GET `/api/tasks/{id}`
- POST `/api/tasks`
- PUT `/api/tasks/{id}`
- DELETE `/api/tasks/{id}`

### Admin
- GET `/admin/users`
- GET `/admin/tasks`
- DELETE `/admin/users/{id}`
- DELETE `/admin/tasks/{id}`

## 📘 Documentation
Swagger available at:  
`/swagger-ui/index.html`

## 🐳 Docker
The project works with PostgreSQL locally or via Docker.  
A simple `docker-compose.yml` can be added to spin up the database quickly.

## 🏁 Project Goal
Strengthen knowledge of Spring Security, JWT, layered architecture and good development practices through a realistic case.
