# Health Tracker API

A Spring Boot application for tracking health-related activities with a focus on outdoor running.

## Overview

Health Tracker API is a microservice-based application that allows users to track their outdoor running activities, earn awards, and manage their profiles. The application uses MongoDB for data storage and Kafka for event-driven communication between services.

## Technology Stack

- **Java** with Spring Boot
- **MongoDB** for data persistence
- **Kafka** for event messaging
- **Spring Security** for authentication and authorization
- **MapStruct** for DTO-Entity mapping

## Architecture

The application follows a standard Spring Boot microservice architecture with the following components:

### Domain Layer
- Entity objects representing core domain concepts (User, OutdoorRunning, Award)
- DTOs for data transfer between layers
- Enums for static domain values (Status, AwardType, Gender)

### Repository Layer
- MongoDB repositories for data access
- Standard CRUD operations for each entity

### Service Layer
- Business logic implementation
- Integration between domain components
- User management, outdoor running tracking, and award services

### Messaging Layer
- Kafka producers for publishing events
- Topics for user registration, user updates, and new outdoor running records

### Controller Layer
- REST endpoints for client interaction
- Secured with Spring Security

### Schedulers
- Periodic tasks that report on user running statistics
- Conditional execution based on configuration

## Features

- User registration and profile management
- Recording and tracking outdoor running activities
- Award system
- Scheduled reporting of user achievements
- Security with role-based access control

## Security

The application implements basic authentication with in-memory user details. The security configuration ensures that all endpoints require authentication.

## Configuration

The application can be configured through properties files with settings for:
- MongoDB connection
- Kafka broker and topics
- Security credentials
- Scheduling behavior

## Running the Application

```bash
./mvnw spring-boot:run
```

## Requirements

- Java 11 or higher
- MongoDB
- Kafka

## Development Notes

- Password validation requires special characters, uppercase letters, and numbers
- Email validation currently only accepts Yahoo email addresses (@yahoo.com)
- The application uses MapStruct for object mapping between entities and DTOs
