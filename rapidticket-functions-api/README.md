## API Endpoints ⚡

The following endpoints are available for interacting with the **RapidTicket API**:

## Endpoints

### 1. Create a New Function
**Endpoint:** `POST /functions`

**Description:**
Saves a new function in the database.

**Request Body:**
- `FunctionCreateRequestDTO`

**Responses:**
- `201 Created` - Function created successfully
- `400 Bad Request` - Invalid input data
- `500 Internal Server Error` - Server error

---

### 2. List All Functions with Filters
**Endpoint:** `GET /functions`

**Description:**
Retrieves all available functions from the system with optional filters.

**Request Query Parameters:**
- `FunctionListRequestDTO`

**Responses:**
- `200 OK` - List of functions retrieved successfully
- `500 Internal Server Error` - Server error

---

### 3. Find a Function by Code
**Endpoint:** `GET /functions/{code}`

**Description:**
Retrieves details of a specific function using its unique code.

**Path Parameters:**
- `code` (String) - Unique identifier of the function

**Responses:**
- `200 OK` - Function found successfully
- `404 Not Found` - Function not found
- `500 Internal Server Error` - Server error

---

### 4. Update a Function by Code
**Endpoint:** `PATCH /functions/{code}`

**Description:**
Modifies an existing function in the system.

**Path Parameters:**
- `code` (String) - Unique identifier of the function

**Request Body:**
- `FunctionUpdateRequestDTO`

**Responses:**
- `204 No Content` - Function updated successfully
- `400 Bad Request` - Invalid input data
- `404 Not Found` - Function not found
- `500 Internal Server Error` - Server error

---

### 5. Delete a Function by Code
**Endpoint:** `DELETE /functions/{code}`

**Description:**
Removes a function from the system using its unique code.

**Path Parameters:**
- `code` (String) - Unique identifier of the function

**Responses:**
- `204 No Content` - Function deleted successfully
- `404 Not Found` - Function not found
- `500 Internal Server Error` - Server error

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
