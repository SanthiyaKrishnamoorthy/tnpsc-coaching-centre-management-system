# 🏫 TNPSC Coaching Centre Management System

[![Java Version](https://img.shields.io/badge/Java-17-blue.svg)](https://jdk.java.net/17/)
[![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL Version](https://img.shields.io/badge/MySQL-8.0-orange.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A comprehensive **Spring Boot backend system** for managing coaching institute operations with **role-based access control**. This application supports three roles: **ADMIN**, **STAFF**, and **STUDENT**, secured using **JWT authentication**.

---

## 📖 About the Project

This project helps a TNPSC coaching institute manage:
- Users (Admin/Staff/Student)
- Courses
- Student enrollments
- Enrollment status tracking
- Fee tracking (per enrollment)

**Base URL (Local):** `http://localhost:8080`

---

## 🎯 Key Features

| Feature | Description |
|--------|-------------|
| 🔐 JWT Authentication | Token-based authentication with 1-hour expiry |
| 👥 Role-Based Access | ADMIN / STAFF / STUDENT with granular permissions |
| 📚 Course Management | CRUD operations for courses |
| 📝 Enrollment System | Track enrollments with status: ACTIVE / COMPLETED / DROPPED |
| 💰 Fee Tracking | Store and track fee paid per enrollment |
| 🛡️ Password Security | BCrypt hashing for secure password storage |

---

## 👑 Role Permissions

| Permission | ADMIN | STAFF | STUDENT |
|-----------|-------|-------|---------|
| Create Course | ✅ | ❌ | ❌ |
| Update Course | ✅ | ✅ | ❌ |
| View All Courses | ✅ | ✅ | ✅ |
| View All Students | ✅ | ✅ | ❌ |
| View Enrollments | ✅ | ✅ | ✅ (own only) |
| Enroll in Course | ❌ | ❌ | ✅ |
| Update User Roles | ✅ | ❌ | ❌ |

---

## 🛠️ Technology Stack

| Component | Version / Tech |
|----------|-----------------|
| Framework | Spring Boot 4.0.3 |
| Security | Spring Security 7.0.3 + JWT (jjwt 0.11.5) |
| Database | MySQL 8.0 + JPA/Hibernate 7.2.4 |
| Build Tool | Maven 3.9.12 |
| Language | Java 17 |
| IDE | IntelliJ IDEA (Recommended) |

---

## 📦 Dependencies (High Level)

- `spring-boot-starter-data-jpa` (Database ORM)
- `spring-boot-starter-security` (Authentication + Authorization)
- `spring-boot-starter-webmvc` (REST APIs)
- `mysql-connector-j` (MySQL driver)
- `jjwt-api`, `jjwt-impl` (JWT generation/validation)
- `lombok` (Reduce boilerplate code)

---

## 📋 Prerequisites

Make sure you have:

### ✅ Java 17+
```bash
java --version
# Expected: openjdk 17.x.x
```

### ✅ MySQL 8.0+
```bash
mysql --version
# Expected: mysql  Ver 8.0.x
```

### ✅ Maven 3.9+ (or Maven Wrapper)
```bash
mvn --version
# Expected: Apache Maven 3.9.x
```

### ✅ Git
```bash
git --version
```

---

## 🚀 Installation & Setup

### Step 1: Clone the repository
```bash
git clone https://github.com/SanthiyaKrishnamoorthy/tnpsc-coaching-centre-management-system.git
cd tnpsc-coaching-centre-management-system
```

### Step 2: Create database
```sql
CREATE DATABASE tnpsc_db;
USE tnpsc_db;
```

### Step 3: Configure database connection

Copy the template:
```bash
cp src/main/resources/application.properties.template src/main/resources/application-local.properties
```

Update `src/main/resources/application-local.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tnpsc_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

> Tip: Ensure your `application.properties` is set to read `application-local.properties` if you are using profiles, or manually configure accordingly.

### Step 4: Build the application

**Windows:**
```bash
mvnw.cmd clean install
```

**Mac/Linux:**
```bash
./mvnw clean install
```

### Step 5: Run the application

Option A (Recommended):
```bash
./mvnw spring-boot:run
```

Option B (After build):
```bash
java -jar target/coaching-0.0.1-SNAPSHOT.jar
```

### Step 6: Verify health endpoint
Open:
```text
http://localhost:8080/test/ping
```

Expected response:
```text
pong
```

---

## 📚 API Documentation

## 🔓 Public Endpoints (No Token Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login and get JWT |
| GET  | `/test/ping` | Health check |

### Register
**POST** `/auth/register`
```json
{
  "name": "John Student",
  "email": "john@example.com",
  "password": "password123",
  "role": "STUDENT"
}
```

### Login
**POST** `/auth/login`
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

---

## 👨‍🎓 Student Endpoints (Requires `ROLE_STUDENT`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/student/courses` | View all available courses |
| GET | `/student/courses/{id}` | View course details |
| POST | `/student/enroll` | Enroll in a course |
| GET | `/student/my-enrollments` | View own enrollments |

---

## 👔 Staff Endpoints (Requires `ROLE_STAFF`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/staff/students` | View all students |
| GET | `/staff/courses` | View all courses |
| PUT | `/staff/courses/{id}` | Update course details |
| GET | `/staff/enrollments/course/{courseId}` | View course enrollments |
| PUT | `/staff/enrollments/{id}/status` | Update enrollment status |

---

## 👑 Admin Endpoints (Requires `ROLE_ADMIN`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/admin/courses` | Create new course |
| GET | `/admin/students` | View all students |
| GET | `/admin/staff` | View all staff |
| GET | `/admin/users/{id}` | View user by ID |
| PUT | `/admin/users/{id}/role` | Change user role |
| GET | `/admin/enrollments` | View all enrollments |

---

## 🔐 API Usage Examples (cURL)

### 1) Register
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Student",
    "email": "john@example.com",
    "password": "password123",
    "role": "STUDENT"
  }'
```

### 2) Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### 3) View Courses (Student)
```bash
curl -X GET http://localhost:8080/student/courses \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

### 4) Create Course (Admin)
```bash
curl -X POST http://localhost:8080/admin/courses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_TOKEN" \
  -d '{
    "courseName": "TNPSC Group 1 Coaching",
    "description": "Complete preparation for Group 1 examination",
    "price": 15000.0
  }'
```

### 5) Enroll in a Course (Student)
```bash
curl -X POST http://localhost:8080/student/enroll \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_STUDENT_TOKEN" \
  -d '{
    "courseId": 1,
    "feePaid": 15000.0
  }'
```

---

## 🗄️ Database Schema

### Entity Relationship Diagram
```text
┌─────────────┐              ┌─────────────┐
│    User     │              │   Course    │
├─────────────┤              ├─────────────┤
│ id (PK)     │              │ id (PK)     │
│ name        │              │ courseName  │
│ email (UQ)  │              │ description │
│ password    │              │ price       │
│ role (ENUM) │              └─────────────┘
└─────────────┘                    │
      │                             │
      │ 1                           │ 1
      │                             │
      └──────────┐        ┌─────────┘
                 ▼        ▼
          ┌─────────────────┐
          │   Enrollment    │
          ├─────────────────┤
          │ id (PK)         │
          │ student_id (FK) │──→ User.id
          │ course_id (FK)  │──→ Course.id
          │ enrollmentDate  │
          │ status (ENUM)   │
          │ feePaid         │
          └─────────────────┘
```

### Sample SQL Queries
```sql
-- View all students with their enrollments
SELECT u.name, u.email, c.course_name, e.status, e.fee_paid
FROM users u
JOIN enrollments e ON u.id = e.student_id
JOIN course c ON e.course_id = c.id
WHERE u.role = 'STUDENT';

-- Calculate total revenue per course
SELECT c.course_name, SUM(e.fee_paid) as total_revenue, COUNT(e.id) as student_count
FROM course c
JOIN enrollments e ON c.id = e.course_id
GROUP BY c.id;
```

---

## 🏗️ Project Structure

```text
tnpsc-coaching-centre-management-system/
├── src/
│   ├── main/
│   │   ├── java/com/tnpsc/coaching/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── StaffController.java
│   │   │   │   ├── StudentController.java
│   │   │   │   └── HomeController.java
│   │   │   ├── service/
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   ├── CourseService.java
│   │   │   │   ├── EnrollmentService.java
│   │   │   │   └── UserService.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── CourseRepository.java
│   │   │   │   └── EnrollmentRepository.java
│   │   │   ├── entity/
│   │   │   │   ├── User.java
│   │   │   │   ├── Course.java
│   │   │   │   ├── Enrollment.java
│   │   │   │   └── Role.java
│   │   │   ├── security/
│   │   │   │   ├── JwtUtil.java
│   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── AuthResponse.java
│   │   │   │   └── EnrollmentRequest.java
│   │   │   └── TnpscCoachingApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application.properties.template
│   └── test/
├── pom.xml
├── LICENSE
└── README.md
```

---

## 🔄 JWT Authentication Flow

```text
┌──────────┐         ┌──────────┐         ┌──────────┐
│  Client  │         │  Server  │         │ Database │
└────┬─────┘         └────┬─────┘         └────┬─────┘
     │ POST /login           │                    │
     │ email/password        │                    │
     │──────────────────────>│                    │
     │                       │ SELECT user        │
     │                       │───────────────────>│
     │                       │ User data          │
     │                       │<───────────────────│
     │                       │ Validate password  │
     │                       │ Generate JWT       │
     │ JWT Token             │                    │
     │<──────────────────────│                    │
     │ Request + JWT         │                    │
     │──────────────────────>│ Validate JWT       │
     │                       │ Extract role       │
     │                       │ Check permissions  │
     │ Response              │                    │
     │<──────────────────────│                    │
```

---

## 🧪 Testing the Application

### ✅ Using the built-in HTML dashboard
1. Start the application
2. Open in browser:
   ```text
   http://localhost:8080/dashboard.html
   ```
3. Register or login
4. Test APIs from UI

### ✅ Using Postman collection
Import this minimal collection:
```json
{
  "info": {
    "name": "TNPSC Coaching API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Register",
          "request": {
            "method": "POST",
            "url": "http://localhost:8080/auth/register",
            "body": {
              "mode": "raw",
              "raw": "{\"name\":\"Test User\",\"email\":\"test@test.com\",\"password\":\"password123\",\"role\":\"STUDENT\"}"
            }
          }
        },
        {
          "name": "Login",
          "request": {
            "method": "POST",
            "url": "http://localhost:8080/auth/login",
            "body": {
              "mode": "raw",
              "raw": "{\"email\":\"test@test.com\",\"password\":\"password123\"}"
            }
          }
        }
      ]
    }
  ]
}
```

---

## ❗ Troubleshooting

| Issue | Solution |
|------|----------|
| Port 8080 already in use | Set `server.port=8081` in `application.properties` |
| MySQL connection refused | Ensure MySQL is running |
| Access denied for user | Verify MySQL username/password |
| JWT token expired | Login again (token expires in 1 hour) |
| 403 Forbidden | Ensure token role matches endpoint |
| Table not found | Set `spring.jpa.hibernate.ddl-auto=update` |

### Enable Debug Logging
Add to `application.properties`:
```properties
logging.level.org.springframework.security=DEBUG
logging.level.com.tnpsc.coaching=DEBUG
```

---

## 🚀 Future Enhancements

- Refresh token mechanism
- Email notifications for enrollment confirmation
- Payment gateway integration (Razorpay/Stripe)
- Admin dashboard with analytics
- Export reports (PDF/Excel)
- Rate limiting
- Swagger/OpenAPI documentation
- Docker support
- Unit + integration tests (JUnit/Mockito)
- Redis caching

---

## 👩‍💻 Author

**Santhiya Krishnamoorthy**  
GitHub: `@SanthiyaKrishnamoorthy`

---

## 📞 Support

If you face any issues:
1. Check the **Troubleshooting** section
2. Open an issue in GitHub Issues
3. Contact the author
