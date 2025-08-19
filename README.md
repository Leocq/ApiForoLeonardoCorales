# API Foro – README (breve)

## Descripción
API REST de un foro (solo **backend**) construida con **Spring Boot**.  
Incluye autenticación **JWT**, migraciones **Flyway**, **Bean Validation**, paginación/orden y reglas de integridad (evitar tópicos duplicados).

**Paquete base:** `AluraChallenge.ApiForoLeonardoCorales`  


## Stack
- Java 24 · Maven · Spring Boot
- Spring Web, Data JPA, Validation, Security
- Flyway Migration, MySQL Driver
- Lombok, DevTools
- JWT (Auth0 Java JWT)

## Requisitos
- Java 24 (o 21 LTS)
- Maven
- MySQL en `127.0.0.1:3306` (DB `foro_db`)

## Configuración
Archivo `src/main/resources/application.yml` (editar credenciales y secreto):
```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/foro_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: foro
    password: "Foro#2025!"
  jpa:
    hibernate.ddl-auto: validate
    properties:
      hibernate.format_sql: true
    open-in-view: false
  flyway:
    enabled: true

app:
  jwt:
    secret: "cambia-esta-clave-super-secreta"
    expiration: 3600000
```

> Crea la DB si no existe:
> ```sql
> CREATE DATABASE IF NOT EXISTS foro_db;
> ```

## Ejecutar
```bash
mvn clean spring-boot:run
```

## Autenticación
1) **Login** `POST /auth/login`  
   Body:
   ```json
   { "email": "admin@foro.com", "password": "admin" }
   ```
   Respuesta:
   ```json
   { "tokenType": "Bearer", "accessToken": "<JWT>", "expiresInMs": 3600000 }
   ```
2) Usar el token en `Authorization: Bearer <JWT>` para acceder al resto.

## Endpoints principales
- **POST** `/topicos` → crear tópico (**201** + `Location`)  
  Body:
  ```json
  { "title":"...", "message":"...", "authorId":1, "courseId":1 }
  ```
- **GET** `/topicos` → listar (paginado y ordenado)  
  Query: `page`, `size`, `sort` (ej: `sort=createdAt,desc`)
- **GET** `/topicos/{id}` → detalle
- **PUT** `/topicos/{id}` → actualizar (título, mensaje, `status`)
- **DELETE** `/topicos/{id}` → eliminar (**204**)

### Filtros útiles
- **GET** `/topicos?course=Java&year=2025`
- **GET** `/topicos?ascTop10=true` (primeros 10 por `createdAt` ASC)

## Reglas de negocio y validaciones
- **Todos los campos obligatorios** (Bean Validation en DTOs **record**).
- **Sin duplicados**: no permite dos tópicos con mismo `title` + `message`.  
  (Índice único + validación de servicio).
- **HTTP 201** al crear.
- **Email inmutable** (no hay endpoint para modificar el email del usuario).
- **Enum** `TopicStatus`: `ABIERTO`, `CERRADO`, `RESUELTO`, `PENDIENTE`.



## Ejemplos rápidos (cURL)
```bash
# Login
curl -s -X POST http://localhost:8080/auth/login  -H "Content-Type: application/json"  -d '{"email":"admin@foro.com","password":"admin"}'

# Crear tópico (reemplaza <JWT>)
curl -s -X POST http://localhost:8080/topicos  -H "Authorization: Bearer <JWT>" -H "Content-Type: application/json"  -d '{"title":"Error Flyway","message":"Ayuda","authorId":1,"courseId":1}'
```

## Notas
- `open-in-view: false`; para evitar `LazyInitializationException` el repo usa `@EntityGraph` y los métodos de lectura están anotados con `@Transactional(readOnly = true)`.
- Si cambias `JWT secret`, renová el token en `/auth/login`.
