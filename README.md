# Gym Admin Backend — Spring Boot 3 (Java 24) + MySQL

- Java **24**
- Spring Boot **3.3.3**
- **MySQL** (único `application.properties`)
- CORS para `http://localhost:5173`
- Swagger UI: `/swagger`

## Levantar MySQL (Docker)

```bash
docker compose up -d
# Crea DB gymdb y usuario gymuser/gympass
```

## Ejecutar la app

```bash
mvn clean package
mvn spring-boot:run
```

## Endpoints
- `GET /api/health`
- `GET /api/occupancy`
- `GET /api/members` `POST /api/members` `GET /api/members/{id}` `PUT /api/members/{id}` `DELETE /api/members/{id}`
- `GET /api/members/{id}/payments` `GET /api/members/{id}/checkins`
- `GET /api/plans`
- `GET /api/checkins` `POST /api/checkins`
- `GET /api/payments`
- `GET /api/equipment`
