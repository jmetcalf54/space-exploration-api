# Space Exploration API

A backend-focused Java/Spring Boot project for exploring space-related data.

The current version focuses on asteroids and comets stored through Spring Data JPA in PostgreSQL, with sample records seeded at startup when the corresponding repository is empty. It includes a layered REST API, validation, structured error handling, DTO responses, automated tests, and NASA NeoWs asteroid imports with mapping, application-defined risk scoring, and duplicate import protection. H2 remains available for repository tests.

The long-term goal is to expand NASA data ingestion, add additional space objects, and introduce more production-style backend features.

## Current Features

- Spring Boot REST API
- Database-backed `Asteroid` and `Comet` JPA entities sharing the `NearEarthObject` mapped superclass
- `AsteroidRepository` and `CometRepository` for database access
- Service layer for business logic
- DTO-based API responses
- Asteroid derived queries: `findByRiskScore` for exact matches and `findByRiskScoreGreaterThanEqual` for minimum risk scores
- Closest near-Earth object lookup
- Risk score input validation (1–5)
- Global exception handling with structured JSON responses for invalid arguments
- Swagger / OpenAPI documentation
- Health endpoint
- JUnit/Mockito service tests, controller/MockMvc tests, and JPA repository tests for both entities
- Startup data seeding for both asteroids and comets
- NASA NeoWs browse integration through `NasaApiClient` using Spring's `RestClient`
- NASA API key configuration through the `NASA_API_KEY` environment variable
- External NASA DTOs for browse responses, asteroid responses, close approach data, and miss distance
- `NasaAsteroidMapper` maps NASA asteroid IDs as `nasaId` values, names, the first close approach's miss distance in kilometers, and NASA's potentially hazardous flag into the internal `Asteroid` model
- `AsteroidRiskCalculator` calculates an application-defined `riskScore` from the hazardous flag and close-approach distance
- `NasaAsteroidImportService` filters NASA records, skips previously imported NASA IDs, and persists valid mapped asteroids to PostgreSQL
- NASA API connectivity/deserialization tests, asteroid mapper tests, and import service tests

## Tech Stack

- Java 25
- Spring Boot 4.x
- Maven
- JUnit Jupiter, Mockito, and MockMvc
- Spring Data JPA
- PostgreSQL for the main database
- H2 for repository tests
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
| GET | `/api/asteroids?minRiskScore=2` | Returns asteroids with `riskScore` at or above 2 |
| GET | `/api/asteroids/risk/2` | Returns asteroids with `riskScore` equal to 2 |
| POST | `/api/asteroids/import` | Imports valid NASA NeoWs asteroid records into PostgreSQL and returns the saved asteroids |

`POST /api/asteroids/import` fetches asteroid data from the NASA NeoWs browse endpoint. `NasaAsteroidImportService` filters out records without close-approach data or a nonblank NASA ID, skips NASA IDs already stored in the database, and maps the remaining records into the internal `Asteroid` model. Mapping calculates the application-defined `riskScore` using NASA's potentially hazardous flag and the first close approach's miss distance. The service persists the mapped asteroids to PostgreSQL and returns the saved records.

NASA's external asteroid identifier is stored in `nasaId`. The import service uses `existsByNasaId` to prevent duplicate NASA asteroid imports, and the entity declares `nasaId` unique. The database-generated `id` is the application's internal database ID; it is separate from NASA's external `nasaId`.

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

## Asteroid Risk Scoring

`riskScore` is an application-defined score from 1–5. NASA provides the potentially hazardous flag (`is_potentially_hazardous_asteroid`); this application calculates the numeric risk score. The score is not a NASA-provided rating.

A non-potentially-hazardous asteroid receives score **1**, regardless of distance. Potentially hazardous asteroids receive scores **2–5** based on close-approach distance, with closer approaches receiving higher scores:

| Potentially hazardous | Close-approach distance (km) | `riskScore` |
| --- | --- | --- |
| No | Any distance | 1 |
| Yes | Less than 100,000 | 5 |
| Yes | 100,000 to less than 384,400 | 4 |
| Yes | 384,400 to less than 1,000,000 | 3 |
| Yes | 1,000,000 or more | 2 |

`NasaAsteroidMapper` passes NASA's flag and the first close approach's miss distance in kilometers to `AsteroidRiskCalculator` to calculate `riskScore`.

## Validation and Error Handling

Asteroid risk score filters are restricted to values from 1 through 5.

For example, a request such as:

```http
GET /api/asteroids/risk/7
```

returns `400 Bad Request` with a structured JSON response:

```json
{
  "status": 400,
  "error": "invalid_argument",
  "message": "Risk score must be between 1 and 5."
}
```

Risk score validation is handled in the service layer. `GlobalExceptionHandler` uses `@RestControllerAdvice` to map `IllegalArgumentException` to an `ApiErrorResponse`.

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
│   ├── external/nasa/
│   │   ├── NasaApiClient.java
│   │   ├── NasaAsteroidMapper.java
│   │   └── dto/
│   │       ├── NasaNeoBrowseResponse.java
│   │       ├── NasaAsteroidResponse.java
│   │       ├── NasaCloseApproachResponse.java
│   │       └── NasaMissDistanceResponse.java
│   ├── model/
│   │   ├── NearEarthObject.java
│   │   ├── Comet.java
│   │   └── Asteroid.java
│   ├── repository/
│   │   ├── AsteroidRepository.java
│   │   └── CometRepository.java
│   ├── service/
│   │   ├── SpaceObjectService.java
│   │   ├── NasaAsteroidImportService.java
│   │   └── AsteroidRiskCalculator.java
│   └── DemoApplication.java
├── main/resources/application.properties
└── test/java/com/example/demo/
    ├── controller/SpaceObjectControllerTest.java
    ├── external/nasa/
    │   ├── NasaAPIClientTest.java
    │   └── NasaAsteroidMapperTest.java
    ├── repository/
    │   ├── AsteroidRepositoryTest.java
    │   └── CometRepositoryTest.java
    ├── service/
    │   ├── SpaceObjectServiceTest.java
    │   ├── NasaAsteroidImportServiceTest.java
    │   └── AsteroidRiskCalculatorTest.java
    └── DemoApplicationTests.java
```

## Testing

The project includes JUnit/Mockito service tests (including the NASA import service), `@WebMvcTest` controller tests using MockMvc, `@DataJpaTest` repository tests using H2, live NASA API tests using `@SpringBootTest`, and a unit test for the NASA asteroid mapper.

Current test coverage includes:

- Service-layer exact/minimum risk score filtering, invalid input, and closest-object logic
- Successful asteroid and comet endpoint responses
- Invalid risk score filter responses and structured error validation
- Asteroid persistence and exact/minimum risk score repository queries
- Comet persistence and retrieval of names and tail lengths
- NASA API key configuration, live NeoWs connectivity, and browse response deserialization
- Nested NASA close approach and miss distance deserialization
- Mapping a NASA asteroid's name and kilometer miss distance into the internal `Asteroid` model
- Import service tests verifying persistence, NASA ID mapping in the returned records, and duplicate import protection using `nasaId` checks
- Risk scoring for non-potentially-hazardous asteroids and each distance band for potentially hazardous asteroids

Set `NASA_API_KEY` and `POSTGRES_PASSWORD` before running the full suite (see configuration below), and make sure the configured PostgreSQL database is available for `@SpringBootTest` tests. Repository tests use H2. The NASA client tests call the live NASA API and require network access and a valid API key.

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
- PostgreSQL with the database and user configured in `application.properties`

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

### PostgreSQL Configuration

PostgreSQL connection settings are read from `demo/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/space_exploration
spring.datasource.username=postgres
spring.datasource.password=${POSTGRES_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

Ensure PostgreSQL is running and the `space_exploration` database exists and is accessible to the configured user. Set the password in the terminal used to run the application or full test suite. On Windows:

```powershell
$env:POSTGRES_PASSWORD = "your-postgres-password"
```

On macOS or Linux:

```bash
export POSTGRES_PASSWORD="your-postgres-password"
```

### NASA API Configuration

`demo/src/main/resources/application.properties` reads the NASA API key from the environment:

```properties
nasa.api.key=${NASA_API_KEY}
```

Set it in the terminal used to run the application or tests. On Windows:

```powershell
$env:NASA_API_KEY = "your-nasa-api-key"
```

On macOS or Linux:

```bash
export NASA_API_KEY="your-nasa-api-key"
```

### Start the Application

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

PostgreSQL persists seeded asteroid and comet data as well as imported NASA asteroid data across application restarts. `DataSeeder` seeds each entity's sample records at startup only when its repository is empty.

The REST endpoints serve the stored database records. Calling `POST /api/asteroids/import` adds valid NASA asteroid records that have not already been imported, identified by `nasaId`. Local seeded asteroids may have `nasaId = null` because they are not NASA-imported records; they still receive an internal database-generated `id`.

### Asteroids

These startup sample records contain preset `riskScore` values; NASA-mapped asteroids use the calculation described above.

- X-1002342
  - Distance from Earth: `100.0`
  - `riskScore`: `1`

- Devastator
  - Distance from Earth: `10000.0`
  - `riskScore`: `5`

### Comets

- Halley's Comet
  - Distance from Earth: `100.0`
  - Tail Length: `500km`

- Red Rocket
  - Distance from Earth: `10000.0`
  - Tail Length: `1000km`

## Roadmap

Planned future improvements include:

- Expand NASA ingestion to process more complete browse data
- Add scheduled data ingestion
- Add Docker and Docker Compose support
- Add CI/CD workflows, including GitHub Actions CI

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
