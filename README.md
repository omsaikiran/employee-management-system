Employee Management System

Spring Boot + RabbitMQ + Docker + MySQL

Project Overview

This project is a Spring Boot based Employee Management System that supports:

Employee Management

Department Management

Leave Management

Notification System using RabbitMQ

Role-based Authentication using Spring Security

Dockerized deployment using Docker Compose

🛠 Tech Stack
Technology	Version
Java	17
Spring Boot	3.3.2
Docker	Latest
Docker Compose	Latest
Spring Security	Basic Auth
Maven	3.x
🏗 Architecture Overview

The application consists of:

Application Service (Spring Boot)

MySQL Database

RabbitMQ Message Broker

Notifications are published when:

New Employee is created

Leave status is updated

RabbitMQ handles:

Message Publishing (Producer)

Message Consumption (Consumer)

Logging received messages (Simulated Email)

🗄 Database Structure
Employee Table

id (Auto-generated)

empName

email (Unique)

salary

joiningDate

department_id (Foreign Key)

createdAt

updatedAt

Department Table

id (Auto-generated)

departmentName

location

createdAt

Leave Table

id (Auto-generated)

employee_id (Foreign Key)

startDate

endDate

status (PENDING/APPROVED/REJECTED)

reason

createdAt

🔐 Security Configuration

Spring Security Basic Authentication implemented.

Roles:
ADMIN

Full access

Create/Update/Delete Employees

Create Departments

Approve/Reject Leave

USER

View own details

Apply Leave

View Leave status

Default Credentials
ADMIN
Username: admin
Password: admin123

USER
Username: user
Password: user123

🚀 Complete Setup Guide (From Scratch)
✅ Step 1: Clone Repository
git clone <repository-url>
cd employee-management

✅ Step 2: Build Project
mvn clean install


If skipping tests:

mvn clean install -DskipTests

✅ Step 3: Docker Build
docker-compose build

✅ Step 4: Start Application
docker-compose up


To run in detached mode:

docker-compose up -d

✅ Step 5: Stop Application
docker-compose down

🐳 Docker Services Included

Docker Compose includes:

Application Service

MySQL Database

RabbitMQ Service

RabbitMQ Ports:

5672 → Application Communication

15672 → Management UI

🐰 RabbitMQ Access

Open in browser:

http://localhost:15672


Default Login:

Username: guest
Password: guest


Queue Name:

leave.notifications

🔔 Notification Flow
1️⃣ Employee Creation

POST /api/employees

→ Employee saved
→ Notification published
→ Consumer receives
→ Log: “New Employee Created”

2️⃣ Leave Status Update

PUT /api/leaves/{id}/status

→ Leave updated
→ Notification published
→ Consumer receives
→ Log: “Leave Status Updated”

📬 API Endpoints
Employee APIs
Create Employee
POST /api/employees

Get All Employees (Pagination Supported)
GET /api/employees?page=0&size=10

Get Employee By ID
GET /api/employees/{id}

Update Employee
PUT /api/employees/{id}

Delete Employee
DELETE /api/employees/{id}

Department APIs
Create Department
POST /api/departments

Get All Departments
GET /api/departments

Leave APIs
Submit Leave
POST /api/leaves

Get Leaves By Employee
GET /api/leaves/employee/{empId}

Update Leave Status
PUT /api/leaves/{id}/status

🧪 Testing Instructions

Start Docker services

Import Postman Collection from /postman folder

Use ADMIN credentials

Test:

Create Department

Create Employee

Submit Leave

Approve Leave

Check RabbitMQ UI for message spike.

Check logs for:

RabbitMQ → Received notification

🐰 How to Verify RabbitMQ

Open:

http://localhost:15672


Go to:

Queues and Streams


Click:

leave.notifications


Observe:

Publish spike

Deliver spike

Queue may show 0 because consumer consumes immediately.

This is expected behavior.

⚙ Error Handling Implemented

Invalid Input Validation

Global Exception Handling

Database Constraint Handling

Basic RabbitMQ Error Handling

Logging all operations

📊 Logging

Logs include:

API operations

Database actions

RabbitMQ publish events

RabbitMQ receive events

Error logs

📁 Project Structure
employee-management/
│
├── src/
├── postman/
│   └── Employee-System.postman_collection.json
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md


⚡ Important Notes

Queue will appear empty after processing (consumer auto-consumes).

Spikes in graph indicate successful publish/delivery.

All services run inside Docker environment.

No external configuration required.

🏁 Final Submission Includes

Source Code

Docker Configuration

Postman Collection

README.md

Database structure

RabbitMQ integration

Security implementation

---------------------------------------------------------------------
🏗 System Architecture Diagram
High-Level Architecture
┌────────────────────────────┐
│        Client (Postman)     │
│  REST API Requests (HTTP)   │
└──────────────┬──────────────┘
│
▼
┌────────────────────────────┐
│   Spring Boot Application  │
│  (Employee Management API) │
│                            │
│  - Controllers             │
│  - Services                │
│  - Repositories            │
│  - Security (Basic Auth)   │
│  - RabbitMQ Producer       │
│  - RabbitMQ Consumer       │
└──────────────┬──────────────┘
│
┌─────────────────────────┼─────────────────────────┐
▼                         ▼                         ▼
┌─────────────────────┐   ┌─────────────────────┐   ┌─────────────────────┐
│      MySQL DB       │   │      RabbitMQ       │   │   Docker Network    │
│                     │   │                     │   │                     │
│ - Employees         │   │ Queue:              │   │ All services run    │
│ - Departments       │   │ leave.notifications │   │ inside Docker       │
│ - Leaves            │   │                     │   │ communicate via     │
│                     │   │ Producer → Consumer │   │ internal network    │
└─────────────────────┘   └─────────────────────┘   └─────────────────────┘

🔁 Notification Flow Diagram
1️⃣ Employee Creation Flow
POST /api/employees
│
▼
EmployeeController
│
▼
EmployeeService
│
▼
Save to MySQL
│
▼
Publish Message to RabbitMQ
│
▼
Queue: leave.notifications
│
▼
RabbitMQ Consumer
│
▼
Log: "New Employee Created Notification"

2️⃣ Leave Status Update Flow
PUT /api/leaves/{id}/status
│
▼
LeaveController
│
▼
LeaveService
│
▼
Update MySQL
│
▼
Publish Message to RabbitMQ
│
▼
Queue: leave.notifications
│
▼
RabbitMQ Consumer
│
▼
Log: "Leave Status Updated Notification"


🔐 Security Flow Diagram
Client Request
│
▼
Spring Security Filter
│
├── Validate Basic Auth
│
├── Check Role (ADMIN / USER)
│
▼
Authorized → Controller
Unauthorized → 401 Error





Author:
Om Sai Kiran