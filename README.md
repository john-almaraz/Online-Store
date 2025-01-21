# *PROJECT IN PROGRESS*

# Online-Store - Microservices Project
This is a reference project for an online store developed as a microservices-based system using modern technologies. It is designed to demonstrate technical skills and serve as a professional portfolio.
# Technologies Used
**BACKEND**
* *Spring Boot:* Main framework for developing microservices.
* *Kafka:* Messaging system for event synchronization.
* *Hexagonal Architecture:* Modular design to maintain separation between business logic and infrastructure details.
* *API Gateway:* Centralization of calls to microservices.
* *Circuit Breaker:* Failure management in the communication between services.
  
**DATA BASE**
* *MySQL:* Transactional data management.
* *MongoDB:* Log storage and auditing.

**Testing**
* *Mockito:* Creation of unit tests for quality assurance.

**CONTAINERS**
* *Docker:* Packaging and deployment of services.
Running the Project with Docker and Kafka

# *Running the Project with Docker and Kafka*
To run the project locally with Docker and set up Kafka, follow these steps:

**Prerequisites**
* Docker installed on your system.
* Docker Compose installed (often included with Docker Desktop).

**Steps**
1. **Clone the Repository**
```sh
git clone https://github.com/your-username/online-store.git
```

2. **Start Kafka and Zookeeper** Run the following command to start the Kafka and Zookeeper services using the provided `docker-compose.yml` file:
```sh
docker-compose up -d
```
3. **Verify Kafka is Running**
   * Ensure the Kafka broker is running by checking the Docker containers:
   ```sh
    docker ps
   ```
   * You should see containers named kafka and zookeeper running.
4. **Run the microservices**
   * Build and run your microservices locally and make all requests via postman on port `http://localhost:8080`
