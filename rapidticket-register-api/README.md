## API Endpoints ⚡

The following endpoints are available for interacting with the **RapidTicket API**:

## Endpoints

### Register a New User
#### Request
**Method:** `POST`

**URL:** `/register`

**Consumes:** `application/json`

**Request Body:**
```json
{
  "username": "string",
  "email": "string",
  "password": "string"
}
```

**Headers:**
```
Content-Type: application/json
```

#### Response
| Status Code | Description |
|-------------|-------------|
| `201 Created` | User registered successfully |
| `400 Bad Request` | Invalid input data |
| `500 Internal Server Error` | Unexpected server error |

#### Example Success Response
```json
{
  "status": "success",
  "message": "User registered successfully"
}
```

#### Example Error Response
```json
{
  "status": "error",
  "message": "Invalid input data"
}
```

---

## Database Setup 🗃️

The API uses a **PostgreSQL** database to store show and reservation data.

### Steps to Set Up the Database:

1. **Run the PostgreSQL container**:

   ```bash
   docker run --name postgres_rapidticket -e POSTGRES_PASSWORD=postgres -d -p 5455:5432 postgres
   ```

2. **Enter the PostgreSQL container**:

   ```bash
   docker exec -it postgres_rapidticket bash
   ```

3. **Create the database**:

   ```bash
   createdb -U postgres rapidticket_local
   ```

---

## Technical Specifications ⚙️

- **Framework**: Spring Boot (Java)
- **Database**: PostgreSQL (relational database)
- **API Design**: RESTful, with JSON responses
- **Security**: JWT-based authentication (not detailed in the problem statement, but a good practice to implement)

## License 📄

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



##PENDING

docker run -d --name sonar -p 9000:9000 sonarqube:lts-community
