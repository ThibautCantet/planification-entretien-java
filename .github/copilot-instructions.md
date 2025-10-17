# AI Agent Instructions for planification-entretien-java

## Project Overview
This is a Spring Boot application for managing technical recruitment interviews at SOAT. The system matches candidates with recruiters based on technology expertise, experience levels, and availability.

## Architecture
- Spring Boot 2.6.0 with Java 17
- Layered architecture: Controller → Service → Repository
- Components:
  - `CandidatController`: Manages candidate operations
  - `RecruteurController`: Handles recruiter operations
  - `EntretienController`: Coordinates interview scheduling
  - `EntretienService`: Core business logic for interview matching
  - `DummyEmailService`: Email notifications for scheduled interviews

## Key Business Rules
1. Interview Matching Requirements:
   - Recruiter and candidate must share the same technology expertise
   - Recruiter must have more experience years than the candidate
   - Both parties must be available at the scheduled time
   - See `planification-entretien.feature` for detailed scenarios

## Database Schema
- Three main tables: `candidat`, `recruteur`, and `entretien`
- All entities have `id`, `language`, `email`, and `experienceInYears` fields
- Interview (`entretien`) links candidates and recruiters with a timestamp

## Testing
- Cucumber BDD tests in `src/test/resources/*.feature`
- Integration tests in `src/test/java/com/soat/planification_entretien/`
- Run tests: `mvn test`

## Development Workflow
1. Build: `mvn clean install`
2. Run: `mvn spring-boot:run`
3. Database migrations in `src/main/resources/db/migration/`

## Common Patterns
- DTOs follow naming convention `EntityDto` (e.g., `CandidatDto`)
- Controllers use ResponseEntity for HTTP responses
- Feature files are written in French using Gherkin syntax

## Integration Points
- Email notifications via `EmailService` interface (currently using `DummyEmailService`)
- REST endpoints for candidate/recruiter management and interview scheduling

When adding new features:
1. Start with a feature file in `src/test/resources/`
2. Implement step definitions in corresponding test classes
3. Add business logic in service layer
4. Create/update controllers as needed