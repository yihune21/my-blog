# My Blog — Spring Boot REST API

Blog REST API built with **Spring Boot**, following a clean **3-tier layered architecture** (Presentation → Business → Data Access).

---

## Tech Stack

| Layer           | Technology                  |
| --------------- | --------------------------- |
| Framework       | Spring Boot 4               |
| Persistence     | Spring Data JPA + Hibernate |
| Database (dev)  | H2 (in-memory)              |
| Database (prod) | PostgreSQL                  |
| Migrations      | Flyway (prod profile)       |
| Validation      | Jakarta Bean Validation     |
| Build           | Maven                       |

---

## Project Structure

```
src/main/java/com/blog/my_blog/
├── controller/       # REST endpoints (@RestController)
│   ├── AuthController.java
│   ├── UserController.java
│   ├── PostController.java
│   ├── CommentController.java
│   └── TagController.java
├── service/          # Business logic interfaces
│   ├── impl/         # Service implementations
├── repository/       # Spring Data JPA repositories
├── entity/           # JPA entities (User, Post, Comment, Tag)
├── dto/
│   ├── request/      # Validated input records
│   └── response/     # API output records
├── mapper/           # Entity ↔ DTO converters
└── exception/        # GlobalExceptionHandler + custom exceptions
```

---

## Data Model

```
users ──< posts >──< post_tags >── tags
               │
               └──< comments
```

- **User** has many **Posts** (as author)
- **Post** has many **Tags** (many-to-many via `post_tags`)
- **Post** has many **Comments**
- **Comment** belongs to a **Post** and a **User** (author)

---

## API Endpoints

### Auth

| Method | Path              | Description                 |
| ------ | ----------------- | --------------------------- |
| `POST` | `/api/auth/login` | Login (returns dummy token) |

### Users

| Method   | Path              | Description    |
| -------- | ----------------- | -------------- |
| `POST`   | `/api/users`      | Create user    |
| `GET`    | `/api/users`      | List all users |
| `GET`    | `/api/users/{id}` | Get user by ID |
| `DELETE` | `/api/users/{id}` | Delete user    |

### Posts

| Method   | Path              | Description                                    |
| -------- | ----------------- | ---------------------------------------------- |
| `POST`   | `/api/posts`      | Create post                                    |
| `GET`    | `/api/posts`      | List all posts (or `?authorId=uuid` to filter) |
| `GET`    | `/api/posts/{id}` | Get post by ID                                 |
| `PUT`    | `/api/posts/{id}` | Update post                                    |
| `DELETE` | `/api/posts/{id}` | Delete post                                    |

### Comments

| Method   | Path                        | Description             |
| -------- | --------------------------- | ----------------------- |
| `POST`   | `/api/comments`             | Create comment          |
| `GET`    | `/api/comments?postId=uuid` | Get comments for a post |
| `GET`    | `/api/comments/{id}`        | Get comment by ID       |
| `DELETE` | `/api/comments/{id}`        | Delete comment          |

### Tags

| Method   | Path             | Description   |
| -------- | ---------------- | ------------- |
| `POST`   | `/api/tags`      | Create tag    |
| `GET`    | `/api/tags`      | List all tags |
| `GET`    | `/api/tags/{id}` | Get tag by ID |
| `DELETE` | `/api/tags/{id}` | Delete tag    |

---

## Running Locally

```bash
# Run in dev mode (H2 in-memory, no external DB needed)
mvn spring-boot:run

# Run tests
mvn clean test
```

The app will start on **http://localhost:8080**.

---

## Running in Production (PostgreSQL)

Set the `prod` profile and provide DB credentials via environment variables:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/blogdb
export DB_USER=postgres
export DB_PASSWORD=yourpassword

mvn spring-boot:run -Dspring.profiles.active=prod
```

Flyway will automatically run migrations from `src/main/resources/db/migration/`.

---

## Example Request

**Create a user:**

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","email":"alice@example.com","password":"secret","role":"AUTHOR"}'
```

**Create a post:**

```bash
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Hello World",
    "content": "My first blog post",
    "status": "published",
    "authorId": "<user-id-from-above>",
    "tagNames": ["java", "spring"]
  }'
```
