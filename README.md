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

## IAM / Seguridad

Todos los endpoints (excepto `POST /api/users/register`, `POST /api/users/login` y `GET /api/health`) requieren un **Bearer Token** JWT.

- Variables nuevas:
  - `JWT_SECRET`: clave simetrica (>=32 caracteres).
  - `JWT_EXPIRATION_MS`: vigencia (default `3600000` = 1h).
- Flujo:
  1. `POST /api/users/register` (o `POST /api/users/login`) retorna `token`, `expiresIn` (segundos) y el usuario.
  2. Consumir los endpoints protegidos enviando el header `Authorization: Bearer <token>`.
- Roles disponibles: `ADMIN`, `USER`. Si no envias `role`, se asigna `USER`. Para listar usuarios se requiere `ADMIN`.

## Endpoints

- `GET /api/health`
- `POST /api/users/register`
- `POST /api/users/login`
- `GET /api/occupancy`
- `GET /api/members` `POST /api/members` `GET /api/members/{id}` `PUT /api/members/{id}` `DELETE /api/members/{id}`
- `GET /api/members/{id}/payments` `GET /api/members/{id}/checkins`
- `GET /api/plans`
- `GET /api/checkins` `POST /api/checkins`
- `GET /api/payments`
- `GET /api/equipment`
