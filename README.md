# Space Exploration API

A backend-focused Java/Spring Boot project for exploring space-related data.

The current version focuses on asteroids and comets stored through Spring Data JPA in an H2 in-memory database, with sample records seeded at startup. It includes a layered REST API, validation, structured error handling, DTO responses, and automated tests.

The long-term goal is to integrate real external space data through NASA APIs and expand the project with additional space objects and more production-style backend features.

## Current Features

- Spring Boot REST API
- Database-backed `Asteroid` and `Comet` JPA entities sharing the `NearEarthObject` mapped superclass
- `AsteroidRepository` and `CometRepository` for database access
- Service layer for business logic
- DTO-based API responses
- Asteroid derived queries: `findByThreatLevel` for exact matches and `findByThreatLevelGreaterThanEqual` for minimum threat levels
- Closest near-Earth object lookup
- Threat-level input validation
- Global exception handling with structured JSON responses for invalid arguments
- Swagger / OpenAPI documentation
- Health endpoint
- JUnit/Mockito service tests, controller/MockMvc tests, and JPA repository tests for both entities
- Startup data seeding for both asteroids and comets

## Tech Stack

- Java 25
- Spring Boot 4.x
- Maven
- JUnit Jupiter, Mockito, and MockMvc
- Spring Data JPA
- H2 Database
- Swagger / OpenAPI
- VS Code

## API Endpoints

### Health

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/health` | Confirms that the API is running |

### Comets and Asteroids

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/comets` | Returns all comet data |
| GET | `/api/asteroids` | Returns all asteroid data |
| GET | `/api/asteroids?minThreatLevel=2` | Returns asteroids at or above the provided threat level |
| GET | `/api/asteroids/threat/2` | Returns asteroids matching an exact threat level |

### Near-Earth Objects

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/near-earth-objects` | Returns all near-Earth objects using a DTO response |
| GET | `/api/near-earth-objects/closest` | Returns the closest near-Earth object |

## Example Response

Example request:

```http
GET /api/near-earth-objects
```

```json
[
  {
    "name": "Halley's Comet",
    "objectType": "Comet",
    "distanceFromEarth": 100.0
  },
  {
    "name": "Red Rocket",
    "objectType": "Comet",
    "distanceFromEarth": 10000.0
  },
  {
    "name": "X-1002342",
    "objectType": "Asteroid",
    "distanceFromEarth": 100.0
  },
  {
    "name": "Devastator",
    "objectType": "Asteroid",
    "distanceFromEarth": 10000.0
  }
]
```

## Validation and Error Handling

Asteroid threat levels are currently restricted to values from 1 through 5.

For example, a request such as:

```http
GET /api/asteroids/threat/7
```

returns `400 Bad Request` with a structured JSON response:

```json
{
  "status": 400,
  "error": "invalid_argument",
  "message": "Threat level must be between 1 and 5."
}
```

Threat-level validation is handled in the service layer. `GlobalExceptionHandler` uses `@RestControllerAdvice` to map `IllegalArgumentException` to an `ApiErrorResponse`.

## Project Structure

```text
demo/src/
├── main/java/com/example/demo/
│   ├── controller/
│   │   ├── SpaceObjectController.java
│   │   └── StatusController.java
│   ├── data/DataSeeder.java
│   ├── dto/
│   │   ├── NearEarthObjectResponse.java
│   │   └── ApiErrorResponse.java
│   ├── exception/GlobalExceptionHandler.java
│   ├── model/
│   │   ├── NearEarthObject.java
│   │   ├── Comet.java
│   │   └── Asteroid.java
│   ├── repository/
│   │   ├── AsteroidRepository.java
│   │   └── CometRepository.java
│   ├── service/SpaceObjectService.java
│   └── DemoApplication.java
├── main/resources/application.properties
└── test/java/com/example/demo/
    ├── controller/SpaceObjectControllerTest.java
    ├── repository/
    │   ├── AsteroidRepositoryTest.java
    │   └── CometRepositoryTest.java
    ├── service/SpaceObjectServiceTest.java
    └── DemoApplicationTests.java
```

## Testing

The project includes JUnit/Mockito service tests, `@WebMvcTest` controller tests using MockMvc, and `@DataJpaTest` repository tests using H2.

Current test coverage includes:

- Service-layer exact/minimum threat-level filtering, invalid input, and closest-object logic
- Successful asteroid and comet endpoint responses
- Invalid threat-level responses and structured error validation
- Asteroid persistence and exact/minimum threat-level repository queries
- Comet persistence and retrieval of names and tail lengths

Run the test suite from the `demo` directory on Windows:

```powershell
.\mvnw.cmd test
```

On macOS or Linux:

```bash
./mvnw test
```

Maven must run with a Java 25 JDK. Verify the active Java version with:

```bash
java -version
```

Check Maven's Java version with `.\mvnw.cmd -version` on Windows or `./mvnw -version` on macOS/Linux.

## Prerequisites

- Java 25
- Git

Maven does not need to be installed separately because the project includes the Maven Wrapper.

Clone the repository:

```bash
git clone https://github.com/jmetcalf54/space-exploration-api.git
```

Navigate into the Spring Boot project:

```bash
cd space-exploration-api/demo
```

## Running the Project Locally

From the `demo` directory on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

The API will run locally at:

```bash
http://localhost:8080
```

## API Documentation

Swagger UI is available locally after starting the app:

```text
http://localhost:8080/swagger-ui/index.html
```

## Current Data

Both asteroids and comets are stored in an H2 in-memory database. `DataSeeder` seeds each entity's sample records at startup when its repository is empty. Data is recreated when the application restarts.

### Asteroids

- X-1002342
  - Distance from Earth: `100.0`
  - Threat Level: `1`

- Devastator
  - Distance from Earth: `10000.0`
  - Threat Level: `2`

### Comets

- Halley's Comet
  - Distance from Earth: `100.0`
  - Tail Length: `500km`

- Red Rocket
  - Distance from Earth: `10000.0`
  - Tail Length: `1000km`

## Roadmap

Planned future improvements include:

- Integrate an external NASA API
- Add an external API DTO/mapping layer
- Consider migrating from H2 to PostgreSQL
- Add scheduled data ingestion
- Add Docker and Docker Compose support
- Add GitHub Actions CI

## Project Goals

This project is being built incrementally as a way to strengthen backend development skills while learning and practicing:

- Java
- Spring Boot
- REST API design
- Layered backend architecture
- Object-oriented programming
- Validation and exception handling
- DTOs and API response design
- Unit testing
- External API integration
- Database persistence
- CI/CD and deployment workflows

The goal is to build each layer gradually and understand how each part of the backend works rather than introducing unnecessary complexity too early.
