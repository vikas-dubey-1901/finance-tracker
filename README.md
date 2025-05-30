# 💸 Personal Finance Tracker & Spending Insights Platform

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.2-green)
![WebFlux](https://img.shields.io/badge/WebFlux-Reactive%20Streams-brightgreen)
![CI/CD](https://img.shields.io/github/actions/workflow/status/yourusername/finance-tracker/ci.yml?label=CI%2FCD)
![License](https://img.shields.io/github/license/yourusername/finance-tracker)

---

## 🧠 Overview

A reactive, cloud-ready **personal finance tracker** built with **Spring WebFlux**, designed for high performance and scalability.  
Track expenses, manage budgets, receive alerts, and gain insights with real-time APIs.

> ⚡ Powered by Java 17, Spring Boot 3, MongoDB, and WebFlux.

---

## 🎯 Features

- ✅ Reactive REST APIs using Spring WebFlux
- 👥 Role-based access control (User/Admin)
- 📊 Monthly spending summaries and alerts
- ⏰ Automated budget check via scheduled jobs
- 🧪 Unit & integration testing with JUnit5 + WebTestClient
- 🔐 Secure API endpoints with Basic Auth or JWT
- ☁️ CI/CD with GitHub Actions
- 📈 Ready for Docker & Kubernetes

---

## 📁 Project Structure

 ```plaintext src/ ├── main/ │ ├── java/ │ │ └── com.example.finance/ │ │ ├── config/ # Security & App Config │ │ ├── controller/ # REST Controllers │ │ ├── model/ # Domain Models │ │ ├── repository/ # Reactive Mongo Repositories │ │ ├── service/ # Business Logic │ │ └── scheduler/ # Scheduled Background Jobs │ └── resources/ │ └── application.yml └── test/ └── ... (Unit & Integration tests) ```





---

## ⚙️ Tech Stack

| Layer         | Technology                    |
|---------------|-------------------------------|
| Language      | Java 17                       |
| Backend       | Spring Boot 3 + WebFlux       |
| Database      | MongoDB (Reactive)            |
| Security      | Spring Security               |
| Testing       | JUnit 5, Mockito, WebTestClient |
| CI/CD         | GitHub Actions                |
| Deployment    | Docker/K8s (Optional)         |

---

## 🔐 API Access Control

| Endpoint Prefix       | Role Required |
|------------------------|---------------|
| `/api/v1/user/**`     | USER or ADMIN |
| `/api/v1/admin/**`    | ADMIN only    |

---

## 🚦 CI/CD

✅ Automated via **GitHub Actions**

```yaml
on: [push, pull_request]
jobs:
  build:
    steps:
    - Checkout
    - Set up JDK 17
    - Run Tests
    - Build JAR
    - Upload Artifact

Future steps: Docker build, K8s deployment

🔄 API Examples

➕ Add a Transaction

POST /api/v1/transactions
Content-Type: application/json

{
  "userId": "user1",
  "amount": 150.00,
  "category": "Groceries",
  "description": "Walmart",
  "type": "EXPENSE"
}


📉 Get Monthly Budget Summary

GET /api/v1/user/budgets/status?userId=user1&year=2025&month=5

🚀 Running Locally

# 1. Clone
git clone https://github.com/vikas-dubey-1901/finance-tracker
cd finance-tracker

# 2. Build
./mvnw clean install

# 3. Run
./mvnw spring-boot:run


📌 TODO / Coming Soon
 React UI Dashboard with charts & insights

 Email notifications (alerts/monthly summary)








