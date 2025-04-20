# Sports Tracker Application Development Guidelines

This document provides essential information for developers working on the Sports Tracker application.

## Build/Configuration Instructions

### Prerequisites
- Java 21 (the project uses Java toolchain 21)
- Gradle 8.x (wrapper included)
- MongoDB (local instance or container)
- Kafka (for messaging)

### Building the Application
```bash
# Build the application
./gradlew build

# Build without running tests
./gradlew build -x test

# Run the application
./gradlew bootRun
```

### Configuration
The application uses two configuration files:
- `application.properties`: Contains core Spring Boot configuration
- `application.yml`: Contains application-specific configuration

Key configuration properties:
- MongoDB connection: `spring.data.mongodb.uri=mongodb://localhost:27017/HealthTracker`
- Server port: `server.port=8087`
- Kafka configuration: `spring.kafka.properties.bootstrap.servers=pkc-419q3.us-east4.gcp.confluent.cloud:9092`
- WhatsApp API: `whatsapp.api` and `whatsapp.api-key` in application.yml

## Testing Information

### Running Tests
```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests "health.tracker.api.service.WhatsappApiServiceTest"

# Run a specific test method
./gradlew test --tests "health.tracker.api.service.WhatsappApiServiceTest.shouldSendMessageWhenBothParametersAreValid"
```

### Code Style
The project uses Google Java Format for code formatting, enforced by the Spotless Gradle plugin:

### Project Structure
- `src/main/java/health/tracker/api/controller`: REST controllers
- `src/main/java/health/tracker/api/service`: Business logic services
- `src/main/java/health/tracker/api/repository`: Data access repositories
- `src/main/java/health/tracker/api/domain`: Domain models (entities and DTOs)
- `src/main/java/health/tracker/api/config`: Configuration classes
- `src/main/java/health/tracker/api/producer`: Kafka producers
- `src/main/java/health/tracker/api/mappers`: MapStruct mappers for object conversion

### Key Technologies
- Spring Boot 3.3.1
- Spring Data MongoDB
- Spring Security
- Spring Kafka
- MapStruct for object mapping
- Thymeleaf for server-side templates
- WebClient for HTTP requests

### Debugging
- Use Spring Boot Actuator endpoints for monitoring and debugging
- Enable debug logging in application.properties: `logging.level.health.tracker=DEBUG`
- Use Spring Boot DevTools for automatic restarts during development