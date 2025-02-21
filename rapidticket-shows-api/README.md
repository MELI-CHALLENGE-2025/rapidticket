## API Endpoints ⚡

The following endpoints are available for interacting with the **RapidTicket API**:

## Endpoints

### 1. Create a Show
**Endpoint:**
```
POST /shows
```
**Description:**
Creates a new show in the system.

**Request Body:**
```json
{
  "code": "string",
  "name": "string",
  "description": "string",
  "startDate": "string",
  "endDate": "string"
}
```

**Responses:**
- `201 Created`: Show created successfully
- `400 Bad Request`: Invalid input data
- `500 Internal Server Error`: Unexpected error

---

### 2. List All Shows
**Endpoint:**
```
GET /shows
```
**Description:**
Retrieves a list of available shows with optional filters.

**Query Parameters:**
- `code` (optional): Filter by show code
- `name` (optional): Filter by show name
- `page` (default: 1): Pagination index
- `size` (default: 10): Number of items per page

**Responses:**
- `200 OK`: Returns a list of shows
- `500 Internal Server Error`: Unexpected error

---

### 3. Get Show by Code
**Endpoint:**
```
GET /shows/{code}
```
**Description:**
Retrieves details of a specific show by its unique code.

**Path Parameters:**
- `code` (required): The unique identifier of the show

**Responses:**
- `200 OK`: Show found successfully
- `404 Not Found`: Show not found
- `500 Internal Server Error`: Unexpected error

---

### 4. Update Show by Code
**Endpoint:**
```
PATCH /shows/{code}
```
**Description:**
Updates an existing show in the system.

**Path Parameters:**
- `code` (required): The unique identifier of the show

**Request Body:**
```json
{
  "name": "string",
  "description": "string",
  "startDate": "string",
  "endDate": "string"
}
```

**Responses:**
- `204 No Content`: Show updated successfully
- `400 Bad Request`: Invalid input data
- `404 Not Found`: Show not found
- `500 Internal Server Error`: Unexpected error

---

### 5. Delete Show by Code
**Endpoint:**
```
DELETE /shows/{code}
```
**Description:**
Deletes a show by its unique code.

**Path Parameters:**
- `code` (required): The unique identifier of the show

**Responses:**
- `204 No Content`: Show deleted successfully
- `404 Not Found`: Show not found
- `500 Internal Server Error`: Unexpected error


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
