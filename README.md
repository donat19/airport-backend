# Airport Management System - Backend

This is the REST API for our airport management system. It's built with Spring Boot and handles all the backend stuff.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**
- **Docker & Docker Compose**
- **JUnit 5 & Mockito** (for testing)
- **Swagger/OpenAPI** (API documentation)

## Project Architecture

Our project follows a layered architecture pattern:
```
com.airport
├── controller/       # REST controllers - handles HTTP requests
├── service/         # Business logic layer
├── repository/      # Data access layer
├── model/           # Entity models
├── dto/             # Data Transfer Objects
├── exception/       # Exception handling
└── config/          # App configuration
```

## Main Entities

1. **Airport** - stores airport info (code, name, city, country)
2. **Flight** - flight details (flight number, departure/arrival times, status)
3. **Airline** - airline info (code, name, country)
4. **Aircraft** - aircraft data (registration number, model, manufacturer)
5. **Gate** - gate info (number, terminal, status)
6. **User** - user data (name, email, role)

## API Endpoints

### Airports
- `GET /api/airports` - Get all airports
- `GET /api/airports/{id}` - Get airport by ID
- `GET /api/airports/code/{code}` - Get airport by code
- `POST /api/airports` - Create new airport
- `PUT /api/airports/{id}` - Update airport
- `DELETE /api/airports/{id}` - Delete airport

### Flights
- `GET /api/flights` - Get all flights
- `GET /api/flights/{id}` - Get flight by ID
- `GET /api/flights/departures/airport/{airportId}` - Get departures from airport
- `GET /api/flights/arrivals/airport/{airportId}` - Get arrivals to airport
- `POST /api/flights` - Create new flight
- `PUT /api/flights/{id}` - Update flight
- `DELETE /api/flights/{id}` - Delete flight

### Airlines, Aircraft, Gates
Same CRUD operations available for each entity.

## Running the Project

### With Docker (recommended)

```bash
# Start MySQL and backend
docker-compose up -d

# Stop everything
docker-compose down
```

### Running Locally

1. Install MySQL and create the database:
```sql
CREATE DATABASE airport_db;
```

2. Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/airport_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Run the application:
```bash
mvn spring-boot:run
```

## Testing

Run unit tests:
```bash
mvn test
```

Run tests with coverage report:
```bash
mvn test jacoco:report
```

## API Documentation

After starting the app, Swagger UI is available at:
- http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Database URL | `jdbc:mysql://localhost:3306/airport_db` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `password` |
| `SERVER_PORT` | Application port | `8080` |

## Database Structure

- `airports` - Airports table
- `flights` - Flights table
- `airlines` - Airlines table
- `aircraft` - Aircraft table
- `gates` - Gates table
- `users` - Users table

## Git Workflow

We use trunk-based development:
1. Create a feature branch: `git checkout -b feature/your-feature`
2. Make your changes and commit
3. Create a Pull Request to main
4. After code review - merge it

## CI/CD

GitHub Actions is set up to automatically run tests on every PR.

## Deploying to AWS

Backend can be deployed to:
- AWS Elastic Beanstalk
- AWS ECS (with Docker)
- AWS EC2

Database:
- AWS RDS (MySQL)

## Team Members

- [Team member names go here]

## License

MIT
