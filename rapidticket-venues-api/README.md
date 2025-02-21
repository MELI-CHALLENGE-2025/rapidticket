## API Endpoints ⚡

The following endpoints are available for interacting with the **RapidTicket API**:

## Venue API Documentation

### 1. **Create Venue**
- **Endpoint**: `POST /venues`
- **Description**: Add a new venue with its details.
- **Request Headers**:
    - `Authorization`: Bearer token for authentication.
- **Request Body**: `VenueDTO`
    - Venue name, location, capacity, and other details.
- **Response**:
    - `201 Created`: Venue successfully created.
    - `400 Bad Request`: Invalid input data.
    - `500 Internal Server Error`: Unexpected error.

### 2. **List Venues**
- **Endpoint**: `GET /venues`
- **Description**: Retrieve a list of all available venues.
- **Query Parameters**:
    - `code` (optional): Filter by venue code.
    - `name` (optional): Filter by venue name.
    - `minCapacity` (optional): Minimum capacity filter.
    - `maxCapacity` (optional): Maximum capacity filter.
    - `location` (optional): Filter by venue location.
    - `page` (default: 1): Pagination page number.
    - `size` (default: 10): Number of items per page.
- **Response**:
    - `200 OK`: List of venues.
    - `500 Internal Server Error`: Unexpected error.

### 3. **Search Venue by Code**
- **Endpoint**: `GET /venues/{code}`
- **Description**: Retrieve details of a specific venue by its unique code.
- **Path Parameters**:
    - `code` (required): Unique identifier of the venue.
- **Response**:
    - `200 OK`: Venue details.
    - `404 Not Found`: Venue does not exist.
    - `500 Internal Server Error`: Unexpected error.

### 4. **Update Venue**
- **Endpoint**: `PATCH /venues/{code}`
- **Description**: Update an existing venue by its unique code.
- **Path Parameters**:
    - `code` (required): Unique identifier of the venue.
- **Request Body**: `VenueUpdateRequestDTO`
    - Updated venue details such as name, location, or capacity.
- **Response**:
    - `204 No Content`: Venue updated successfully.
    - `400 Bad Request`: Invalid input data.
    - `404 Not Found`: Venue does not exist.
    - `500 Internal Server Error`: Unexpected error.

### 5. **Delete Venue**
- **Endpoint**: `DELETE /venues/{code}`
- **Description**: Remove a venue from the system by its unique code.
- **Path Parameters**:
    - `code` (required): Unique identifier of the venue.
- **Response**:
    - `204 No Content`: Venue deleted successfully.
    - `404 Not Found`: Venue does not exist.
    - `500 Internal Server Error`: Unexpected error.

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
