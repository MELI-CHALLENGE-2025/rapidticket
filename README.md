# RapidTicket API 🚀

## Overview 📊

Welcome to the **RapidTicket API**! This API is designed to manage ticket sales for live events, such as concerts, theater shows, and more. RapidTicket connects customers and event organizers, allowing them to seamlessly manage event data, ticket reservations, and show details. It's built for scalability, flexibility, and optimized for high-traffic environments.

---

## Key Features 🔑

### 1. **Manage Shows & Events** 🎭
- **Create, Update, and Delete** shows.
- Allows different pricing models for different sections.
- Supports the concept of numbered seats in venues.

### 2. **Reserve Tickets** 🎫
- Allows users to **reserve tickets** for shows, selecting seats if available.
- Prevents double booking: Once a seat is reserved, no other user can select it.
- Each reservation records customer details like **DNI** (ID), name, and seat selections.

### 3. **Show Availability and Pricing** 💵
- List all available shows with their schedules, prices, and seat availability.
- Shows with different sections may have varied pricing.

### 4. **Complex Search & Filter** 🔍
- Search shows based on multiple criteria like:
    - Date range 🎯
    - Price range 💲
    - Sorting by various attributes (e.g., price, date, etc.)

---

## System Design 🖥️

The RapidTicket API is designed with scalability and efficiency in mind. The API is built using **Spring Boot** and interacts with a **PostgreSQL** database for persistent storage. Here’s how the system is structured:

1. **Controllers**: Expose API endpoints for interacting with shows and reservations.
2. **Services**: Handle business logic and communication with the database.
3. **DTOs (Data Transfer Objects)**: Used for transferring show and reservation data between layers.
4. **Response Wrapper**: Consistent structure for all responses, including errors and data.

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

---

## Handling High Traffic 🚦

This API is designed to handle between **1K to 20K requests per minute**, ensuring that the architecture scales smoothly under load. The following strategies are used:

- **Database connection pooling** to optimize access.
- **Caching strategies** for frequently accessed data.
- **Load balancing** for high availability.

---

## Handling Reservation Conflicts 🚫

To avoid double bookings, the system checks the availability of each seat before confirming a reservation. If a seat is already taken, the API will reject the reservation request with an error message:

```json
{
  "error": "The requested seat has already been reserved."
}
```

---

## Test & Coverage 🧪

While **test coverage** is **desirable** but not mandatory for this project, it’s highly recommended to ensure that your code is reliable and functional.

### To Run Tests:
Use **JUnit** for unit and integration testing:

```bash
./gradlew test
```

```bash
./rapidticket-shows-api/gradlew test
```  
```bash
./rapidticket-venues-api/gradlew test
```  

---  

## Instructions for Running Locally 🖥️

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/yourusername/rapidticket-api.git
   cd rapidticket-api
   ```

2. **Set Up Dependencies**:

   ```bash
   ./gradlew install
   ```

3. **Run the Application**:

   ```bash
   ./gradlew spring-boot:run
   ```

---

## Contribution Guidelines 👐

We welcome contributions! To contribute to this project:

1. **Fork** the repository.
2. **Create a feature branch** (`git checkout -b feature/your-feature`).
3. **Commit your changes** (`git commit -am 'Add new feature'`).
4. **Push to the branch** (`git push origin feature/your-feature`).
5. Open a **pull request**.

---

## License 📄

This project is licensed under the MIT License - see the [LICENSE](rapidticket-shows-api/LICENSE) file for details.