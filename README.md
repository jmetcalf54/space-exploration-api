# Space Exploration API

A backend-focused Java/Spring Boot project for exploring space-related data.

The current version focuses on asteroids and comets stored through Spring Data JPA in PostgreSQL, with sample records seeded at startup when the corresponding repository is empty. It includes a layered REST API, validation, structured error handling, DTO responses, automated tests, and NASA NeoWs asteroid imports with mapping, application-defined risk scoring, and duplicate import protection. Flyway tracks the database schema, Docker Compose runs the containerized application and PostgreSQL, and normal tests use H2 in isolation from external services.

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
- PostgreSQL persistence with a named Docker volume
- Flyway initial schema migration and version tracking with Hibernate schema validation
- Spring Boot application containerization through `demo/Dockerfile`
- Docker Compose for the application and PostgreSQL
- H2 test isolation and separation of live NASA integration tests from the normal Maven suite
- GitHub Actions CI for the normal test suite on pushes and pull requests to `main`
- NASA NeoWs browse integration through `NasaApiClient` using Spring's `RestClient`
- NASA API key configuration through the `NASA_API_KEY` environment variable
- External NASA DTOs for browse responses, asteroid responses, close approach data, and miss distance
- `NasaAsteroidMapper` maps NASA asteroid IDs as `nasaId` values, names, the first close approach's miss distance in kilometers, and NASA's potentially hazardous flag into the internal `Asteroid` model
- `AsteroidRiskCalculator` calculates an application-defined `riskScore` from the hazardous flag and close-approach distance
- `NasaAsteroidImportService` filters NASA records, skips previously imported NASA IDs, and persists valid mapped asteroids to PostgreSQL
- Separate live NASA API connectivity/deserialization tests, asteroid mapper tests, and import service tests

## Tech Stack

- Java 25
- Spring Boot 4.x
- Maven
- JUnit Jupiter, Mockito, and MockMvc
- Spring Data JPA
- PostgreSQL for the main database
- Flyway for database schema migrations and version tracking
- H2 for normal tests
- Docker and Docker Compose
- GitHub Actions CI configuration
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
├── main/resources/
│   ├── application.properties
│   └── db/migration/V1__create_initial_schema.sql
├── test/resources/application.properties
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

The normal `mvn test` suite runs **15 tests** without a live NASA API call, a real NASA API key, or a PostgreSQL instance. It includes JUnit/Mockito service tests (including the NASA import service), `@WebMvcTest` controller tests using MockMvc, `@DataJpaTest` repository tests, an application context test, and a unit test for the NASA asteroid mapper.

Test-specific configuration in `demo/src/test/resources/application.properties` isolates normal tests from the main application database:

```properties
spring.datasource.url=jdbc:h2:mem:space_exploration_test
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.flyway.enabled=false
nasa.api.key=test-key
```

H2 provides an in-memory database, Hibernate creates and drops the test schema, and Flyway is disabled for normal test execution. The fake NASA key only allows the Spring test context to load without a real secret.

Current test coverage includes:

- Service-layer exact/minimum risk score filtering, invalid input, and closest-object logic
- Successful asteroid and comet endpoint responses
- Invalid risk score filter responses and structured error validation
- Asteroid persistence and exact/minimum risk score repository queries
- Comet persistence and retrieval of names and tail lengths
- Mapping a NASA asteroid's name and kilometer miss distance into the internal `Asteroid` model
- Import service tests verifying persistence, NASA ID mapping in the returned records, and duplicate import protection using `nasaId` checks
- Risk scoring for non-potentially-hazardous asteroids and each distance band for potentially hazardous asteroids

`NasaAPIClientTest` uses `@SpringBootTest` and is tagged `@Tag("integration")`. Maven Surefire excludes the `integration` tag from normal test execution. These separate live tests cover NASA API key configuration, NeoWs connectivity, browse responses, and nested close-approach/miss-distance deserialization. Running them explicitly requires network access and a real key supplied as a Spring property override, because the test configuration otherwise supplies `test-key`.

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

## Continuous Integration

The GitHub Actions CI configuration targets pushes to `main` and pull requests targeting `main`. It uses Ubuntu, Java 25 from Temurin, and Maven dependency caching. Commands run from the `demo` directory, with the test step running:

```bash
./mvnw clean test
```

The normal CI test run uses the H2 test configuration and excludes live NASA integration tests, so it requires neither a real NASA API key nor a PostgreSQL service.

The workflow is located at `.github/workflows/ci.yml`.

## Prerequisites

- Git
- Docker with Docker Compose for running the full stack
- Java 25 for running the application or Maven tests outside Docker

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

### Full Stack with Docker Compose

Run these commands from the `demo` directory. `compose.yml` defines the Spring Boot `app` service and the `postgres` service. Set both required environment variables in the same terminal before starting the stack.

On Windows:

```powershell
$env:POSTGRES_PASSWORD = "your-postgres-password"
$env:NASA_API_KEY = "your-nasa-api-key"
```

On macOS or Linux:

```bash
export POSTGRES_PASSWORD="your-postgres-password"
export NASA_API_KEY="your-nasa-api-key"
```

Build the application image using `demo/Dockerfile` (the command below runs from `demo`). Compose references this image rather than building it automatically:

```bash
docker build -f Dockerfile -t space-exploration-api .
docker compose up -d
docker compose ps
```

The application is exposed at `http://localhost:8080`, and PostgreSQL is exposed on port `5432`. Inside Compose, the application connects to `jdbc:postgresql://postgres:5432/space_exploration` using the service hostname `postgres`. Compose supplies this URL through `SPRING_DATASOURCE_URL`; `localhost` inside the application container would refer to that container itself.

PostgreSQL stores its data in the named volume `postgres-data`, mounted at `/var/lib/postgresql`. Compose normally prefixes the volume name with the Compose project name. The volume preserves database data across container restarts and stack shutdowns.

View application logs:

```bash
docker logs space-exploration-api
```

Stop the stack while retaining the database volume:

```bash
docker compose down
```

A fresh Docker Compose environment has been successfully verified end-to-end:

- PostgreSQL starts with a fresh named volume.
- Flyway automatically applies version 1 from `V1__create_initial_schema.sql`.
- The `asteroids`, `comets`, and `flyway_schema_history` tables are created, along with the required ID sequences.
- Hibernate validates the schema.
- Spring Boot starts successfully.

No pre-existing application schema or manual Flyway baseline is required for a fresh setup. Sample data seeding runs after schema validation succeeds.

### PostgreSQL Configuration

PostgreSQL connection settings are read from `demo/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/space_exploration
spring.datasource.username=postgres
spring.datasource.password=${POSTGRES_PASSWORD}
spring.flyway.baseline-on-migrate=true
spring.flyway.baseline-version=1
spring.jpa.hibernate.ddl-auto=validate
```

When running the application directly on the host, ensure PostgreSQL is running on `localhost:5432` and the `space_exploration` database exists and is accessible to the configured user. Flyway creates the initial application schema automatically on an empty database. PostgreSQL can also be started separately with `docker compose up -d postgres` after setting both required environment variables shown above. Set the password in the terminal used to run the application. On Windows:

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

Set it in the terminal used to run the application. Normal tests use the fake key from their test configuration. On Windows:

```powershell
$env:NASA_API_KEY = "your-nasa-api-key"
```

On macOS or Linux:

```bash
export NASA_API_KEY="your-nasa-api-key"
```

### Flyway Schema Management

Flyway manages database schema migrations and version tracking. The project includes `src/main/resources/db/migration/V1__create_initial_schema.sql` within `demo`. On a completely fresh PostgreSQL database, Flyway automatically applies this version 1 migration and records it in `flyway_schema_history`.

The migration creates the initial `asteroids` and `comets` tables, including the unique constraint on `asteroids.nasa_id`, and the required `asteroids_seq` and `comets_seq` ID sequences. No pre-existing application schema or manual baseline is needed.

The previously existing development database was originally baselined at version **1**. The main application retains `baseline-on-migrate=true` and `baseline-version=1` for that existing-schema history; baselining is not a setup step for new databases.

The main application uses `spring.jpa.hibernate.ddl-auto=validate`, so Hibernate checks that the schema matches the entities instead of automatically modifying it.

Introduce future schema changes through additional versioned Flyway SQL migration files under `src/main/resources/db/migration` within `demo`.

### Start the Application Outside Docker

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
- Deploy the application

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
