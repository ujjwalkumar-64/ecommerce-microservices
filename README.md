# E-commerce Microservices (Spring Boot & Spring Cloud)

A scalable microservices-based e-commerce backend built with **Spring Boot** and **Spring Cloud**. It demonstrates best practices for distributed system design, including service discovery, centralized configuration, asynchronous messaging, and distributed tracing.

---

## Architecture Overview



### Key Components

- **API Gateway:** Entry point for all client requests (e.g., Angular frontend). Routes traffic to backend services.
- **Keycloak:** Identity and access management for authentication/authorization.
- **Microservices:** 
  - **Customer:** Manages user profiles and authentication.
  - **Product:** Handles product catalog operations.
  - **Order:** Manages order lifecycle and processing.
  - **Payment:** Handles payment transactions and status updates.
  - **Notification:** Consumes Kafka events to send notifications.
- **Service Discovery:** **Eureka Server** enables dynamic lookup of service instances.
- **Config Server:** Centralized configuration management for all microservices.
- **Message Broker:** **Kafka** used for asynchronous messaging (e.g., order/payment confirmation, notifications).
- **Distributed Tracing:** **Zipkin** integration for tracking requests across services.
- **Databases:** Services use PostgreSQL or MongoDB for persistence.

---

## Communication Patterns

- **REST / OpenFeign:** Synchronous calls between microservices for business logic and data exchange.
- **Kafka:** Asynchronous messaging for notifications and status updates.
- **Eureka:** Dynamic service registration/discovery, powering OpenFeign clients.

---

## Service Directory Structure

```
services/
├── config-server/        # Centralized config management (Spring Cloud Config)
├── customer/             # Customer management microservice
├── discovery-server/     # Eureka service discovery
├── notification/         # Notification service (Kafka consumer)
├── order/                # Order management microservice
├── payment/              # Payment processing microservice
└── product/              # Product catalog microservice
```

---

## Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 17+
- Maven

### Quick Start (All Services)

1. Clone this repository:
   ```bash
   git clone https://github.com/ujjwalkumar-64/ecommerce-microservices.git
   cd ecommerce-microservices
   ```

2. Start all services using Docker Compose:
   ```bash
   docker-compose up --build
   ```

3. Access API Gateway (default: `http://localhost:8080`), Eureka dashboard (`http://localhost:8761`), and Zipkin (`http://localhost:9411`).

---

## Service Details

### 1. **config-server**
- Centralized configuration for all microservices.
- Uses Spring Cloud Config.

### 2. **discovery-server**
- Eureka server for service discovery.

### 3. **customer**
- REST API for customer registration, login, profile management.
- Uses PostgreSQL or MongoDB.

### 4. **product**
- REST API for product CRUD operations.
- Uses PostgreSQL.

### 5. **order**
- REST API for order creation, tracking, and management.
- Interacts with customer, product, payment services.

### 6. **payment**
- REST API for payment processing.
- Publishes payment confirmation events to Kafka.

### 7. **notification**
- Listens to Kafka events for order/payment confirmation.
- Sends notifications (e.g., email/SMS).

---

## Observability & Tracing

- All services are instrumented with **Zipkin** for distributed tracing.
- Trace requests end-to-end across microservices.
- [Zipkin UI](http://localhost:9411) for viewing traces.

---

## Security

- **Keycloak** integration for authentication and role-based access control.
- API Gateway enforces security for incoming requests.

---

## Extending & Contributing

- Add new microservices by creating a new folder under `services/`.
- Fork, clone, and submit pull requests for new features or bug fixes.
- See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines (to be added).

---

## License

Ujjwal Kumar

---

## Contact

For questions or support, open an issue or contact [ujjwalkumar-64](https://github.com/ujjwalkumar-64).
