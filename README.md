# 🛒 Kotlin Spring Boot Microservices — API Gateway, Eureka, Kafka

## 📌 Overview
This project is a **Kotlin-based Spring Boot** microservices application built with **Gradle**.  
It demonstrates a secure **microservice architecture** with the following key features:

- **API Gateway** for routing and request entry
- **Eureka Service Discovery** for dynamic service registration and discovery
- **Authentication Service** with JWT-based security
- **Order Service** and **Inventory Service** communicating via **Kafka events**
- **Secure REST endpoints** that validate JWT tokens before processing requests

---

 🛠 Future Enhancements
	•	Add Circuit Breaker with Resilience4j
	•	Implement Distributed Tracing with Zipkin/Jaeger
	•	Add Prometheus + Grafana monitoring
	•	Containerize all services with Docker Compose
 	•	Integeration test with test containers
    •   Swagger Documentation 

 
## 🏗 Architecture

### **Service Components**
1. **API Gateway**
   - Acts as a single entry point for all client requests
   - Routes requests to appropriate services
   - Handles token forwarding to backend services

2. **Eureka Discovery Service**
   - Keeps track of all running service instances
   - Enables dynamic service-to-service communication without hardcoding URLs

3. **Authentication Service**
   - Handles **User Registration** and **Login**
   - Generates **JWT tokens**
   - Validates tokens for secured endpoints
   - Stores user credentials in its own database

4. **Order Service**
   - Receives orders from clients (after authentication)
   - Checks inventory via **Kafka events**
   - Publishes `OrderPlacedEvent` to Kafka
   - Maintains order status in its database

5. **Inventory Service**
   - Listens for `OrderPlacedEvent` from Kafka
   - Updates stock levels for **lenses** and **frames**
   - Auto-orders from suppliers if stock is low


⚙️ Technology Stack
	•	Language: Kotlin
	•	Framework: Spring Boot
	•	Build Tool: Gradle
	•	Service Discovery: Netflix Eureka
	•	API Gateway: Spring Cloud Gateway
	•	Messaging: Apache Kafka
	•	Database: PostgreSQL
	•	Security: Spring Security + JWT
	•	Containerization: Docker (optional)
	•	Testing: JUnit 5, MockK


 📂 Project Structure
├── api-gateway/           # API Gateway service
├── eureka-server/         # Eureka Discovery Server
├── authentication-service # Handles user auth and JWT
├── order-service/         # Handles orders, publishes Kafka events
├── inventory-service/     # Listens to Kafka events, updates inventory
└── README.md              # Project documentation


🚀 Getting Started

1️⃣ Prerequisites

Make sure you have installed:
	•	Java 21+
	•	Kotlin
	•	Gradle
	•	Docker (for Kafka & PostgreSQL)
	•	Apache Kafka (if running locally)


2️⃣ Running the Services

Step 1: Start Infrastructure

You can use Docker Compose to start Kafka, SpringBoot and PostgreSQL:


🔑 API Endpoints

Authentication Service
	•	POST api/auth/registerNewUser — Register a new user
	•	POST api/auth/login — Authenticate and receive a JWT token

Order Service (secured)
	•	POST api/orders — Place a new order



🔐 Security Flow
	1.	User registers or logs in via Authentication Service
	2.	Service returns a JWT token
	3.	Client includes Authorization: Bearer <token> header in all further requests
	4.	API Gateway validates the token before forwarding the request
	5.	Invalid tokens result in a 401 Unauthorized response


📡 Kafka Topics
	order-placed — published by Order Service, consumed by Inventory Service
  supplier-order-requested - published by inventory service for restock
  order-status-updated - for updating the order status

 


 📦 Order & Inventory Flow

When a customer places an order, the system ensures stock availability before confirming. The flow is as follows:
	1.	Order Request Received
	•	The client sends an authenticated request to the Order Service through the API Gateway.
	•	The Order Service checks the requested quantity against the Inventory Service.
	2.	If Stock is Available
	•	The Order Service confirms the order immediately.
	•	An OrderPlacedEvent is published to Kafka.
	•	The order status is updated to CONFIRMED via an OrderStatusUpdateEvent.
	3.	If Stock is Low
	•	The Order Service creates a SupplyRequestEvent and publishes it to Kafka.
	•	This event is picked up by the Supplier Service (or supplier ordering logic) to restock the product to the desired restocking level.
	•	The order status is set to WAITING_FOR_RESTOCK.
	4.	Supplier Fulfillment
	•	Once the supplier delivers the required quantity, the Inventory Service updates the stock.
	•	The Order Service is notified (via Kafka or a callback) that the product is now in stock.
	•	The pending order status is updated to CONFIRMED.

