# Space Exploration API

A backend-focused Java/Spring Boot project for exploring space-related data.

The current version focuses on near-Earth objects such as asteroids and comets. It currently uses hardcoded sample data while the backend architecture, REST API design, validation, error handling, DTOs, and automated testing are being developed.

The long-term goal is to integrate real external space data, likely through NASA APIs, and expand the project to support persistent storage, additional space objects, and more production-style backend features.

## Current Features

- Spring Boot REST API
- Near-Earth object inheritance model
- Comet and asteroid models
- Service layer for business logic
- DTO-based API responses
- Asteroid filtering by exact threat level
- Asteroid filtering by minimum threat level
- Closest near-Earth object lookup
- Threat-level input validation
- Structured JSON error responses
- Global exception handling
- Swagger / OpenAPI documentation
- Health endpoint
- JUnit service-layer tests

## Tech Stack

- Java 25
- Spring Boot 4.x
- Maven
- JUnit 5
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

``` http
GET /api/asteroids/threat/7
```

returns:

400 Bad Request

with a structured JSON response:

```json
{
  "status": 400,
  "error": "invalid_argument",
  "message": "Threat level must be between 1 and 5."
}
```

Threat-level validation is handled in the service layer.

API error responses are handled through a global exception handler using Spring's exception-handling functionality.

```text
src
├── main
│   └── java
│       └── com
│           └── example
│               └── demo
│                   ├── controller
│                   │   ├── SpaceObjectController.java
│                   │   └── ...
│                   ├── dto
│                   │   ├── NearEarthObjectResponse.java
│                   │   └── ApiErrorResponse.java
│                   ├── exception
│                   │   └── GlobalExceptionHandler.java
│                   ├── model
│                   │   ├── NearEarthObjects.java
│                   │   ├── Comet.java
│                   │   └── Asteroid.java
│                   ├── service
│                   │   └── SpaceObjectService.java
│                   └── DemoApplication.java
│
└── test
    └── java
        └── com
            └── example
                └── demo
                    └── service
                        └── SpaceObjectServiceTest.java
```
## Testing

The project includes JUnit tests for the SpaceObjectService.

Current test coverage includes:

- Exact asteroid threat-level filtering
- Minimum threat-level filtering
- Invalid threat-level input
- Closest near-Earth object logic

To run the test suite on Windows:

```powershell
.\mvnw.cmd test
```

On macOS or Linux:

```bash
./mvnw test
```

The project currently targets Java 25, so Maven must also be running with a Java 25 JDK.

You can verify the active Java version with:

```bash
java -version
```

You can verify which Java version Maven is using with:

Windows
```powershell
.\mvnw.cmd -version
```

macOS / Linux
```bash
./mvnw -version
```

## Prerequisites
- Java 25
- Git

Maven does not need to be installed separately because the project includes the Maven Wrapper.

Clone the Repository
```bash
git clone https://github.com/jmetcalf54/space-exploration-api.git
```

Navigate into the Spring Boot project:
```bash
cd space-exploration-api/demo
```

## Running the Project Locally

On Windows:

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

## Current Sample Data

The project currently uses hardcoded sample data.

This is intentional while the core backend architecture and API behavior are being developed.

### Comets
- Halley's Comet
 - Distance from Earth: 100.0
 - Tail Length: 500km
- Red Rocket
 - Distance from Earth: 10000.0
 - Tail Length: 1000km

### Asteroids
- X-1002342
 - Distance from Earth: 100.0
 - Threat Level: 1
- Devastator
 - Distance from Earth: 10000.0
 - Threat Level: 2

Future versions will replace or supplement this sample data with real external space data.

## Roadmap

Planned future improvements include:

- Add controller/API integration tests
- Improve OpenAPI response documentation
- Rename NearEarthObjects to the singular NearEarthObject
- Expand DTO usage where appropriate
- Add database persistence
- Integrate an external NASA API
- Add scheduled data ingestion
- Add Docker and Docker Compose support
- Add GitHub Actions CI
- Expand the API to additional space-related objects

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