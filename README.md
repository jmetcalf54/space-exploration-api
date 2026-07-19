# Space Exploration API

A Java/Spring Boot backend project for exploring space-related data. The current version uses hardcoded near-Earth object data to practice backend architecture, REST API design, service layers, DTOs, and filtering logic.

## Current Features

- Spring Boot REST API
- Comet and asteroid models
- Abstract near-Earth object parent class
- Service layer for business logic
- DTO response for near-Earth object endpoints
- Filtering asteroids by threat level
- Closest near-Earth object endpoint
- Basic health/status endpoint

## Tech Stack

- Java
- Spring Boot
- Maven
- VS Code

## API Endpoints

### Health

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/health` | Confirms the API is running |

### Space Objects

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/comets` | Returns all comet data |
| GET | `/api/asteroids` | Returns all asteroid data |
| GET | `/api/asteroids?minThreatLevel=2` | Returns asteroids at or above a minimum threat level |
| GET | `/api/asteroids/threat/2` | Returns asteroids matching a specific threat level |
| GET | `/api/near-earth-objects` | Returns all near-Earth objects using a DTO response |
| GET | `/api/near-earth-objects/closest` | Returns the closest near-Earth object |

## Example Response

`GET /api/near-earth-objects`

```json
[
  {
    "distanceFromEarth": 100.0,
    "name": "Halley's Comet",
    "objectType": "Comet"
  },
  {
    "distanceFromEarth": 10000.0,
    "name": "Devastator",
    "objectType": "Asteroid"
  }
]
```

```text
src/main/java/com/example/demo
├── controller
│   └── SpaceObjectController.java
├── dto
│   └── NearEarthObjectResponse.java
├── model
│   ├── NearEarthObjects.java
│   ├── Comet.java
│   └── Asteroid.java
├── service
│   └── SpaceObjectService.java
└── DemoApplication.java
```

## Running the Project Locally

From the `demo` directory:

```powershell
.\mvnw.cmd spring-boot:run
```

## API Documentation

Swagger UI is available locally after starting the app:

```text
http://localhost:8080/swagger-ui/index.html
```

## Roadmap

Planned next steps:

- Add validation/error handling for invalid query parameters
- Add more DTOs for consistent API responses
- Add external NASA API integration
- Add database persistence
- Add tests for service and controller logic