# Planification d'entretien

Planification d'entretien entre un recruteur et un candidat

### Requirement

- Version du JDK 17
- Docker

### Build

```
mvn build
```

### Test

Start postgres docker container first

```
mvn test
```

### Start Postgres

```
docker-compose up -d
```

### Boot of service

```
mvn exec:java -Dexec.mainClass="com.soat.planification_entretien.Application"
```

#### Run the service in local mode

```
mvn exec:java -Dexec.mainClass="com.soat.planification_entretien.Application" -Dspring.profiles.active=local
```
