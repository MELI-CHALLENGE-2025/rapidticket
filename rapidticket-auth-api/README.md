## API Endpoints ⚡

The following endpoints are available for interacting with the **RapidTicket API**:

### **1. User Login**
**POST** `/auth/login`  
Authenticates a user and returns a JWT token.

#### **Request Body**
```json
{
  "username": "user@example.com",
  "password": "password123"
}
```
| Field    | Type   | Required | Description                      |
|----------|--------|----------|----------------------------------|
| username | String | Yes      | Email or username of the user.  |
| password | String | Yes      | User's password.                |

#### **Response (200 - Login successful)**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```
| Field  | Type   | Description                     |
|--------|--------|---------------------------------|
| token  | String | JWT token generated upon login. |

#### **Response Codes**
| Code  | Description                                   |
|-------|-----------------------------------------------|
| 200   | Login successful. Returns a JWT token.       |
| 401   | Unauthorized. Invalid credentials.           |
| 500   | Internal server error.                       |

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
