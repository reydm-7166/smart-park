# Smart Park API

A Spring Boot REST API for managing vehicles, parking lots, and parking records.

## Base URL

```text
http://localhost:8080
```

## Authentication

The API uses JWT authentication.

### Login

**Request**
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Response**

```json
{
  "token": "your-jwt-token"
}
```

Use the returned token for protected endpoints:

```http
Authorization: Bearer <your-jwt-token>
```

---

## API Endpoints
---

### Vehicles

#### Create Vehicle (Valid types only are CAR, MOTORCYCLE, TRUCK)

```http
POST http://localhost:8080/api/v1/vehicle
Content-Type: application/json
Authorization: Bearer <your-jwt-token>

{
  "plateNumber": "ABC-999",
  "type": "CAR",
  "name": "mond"
}
```

#### Get All Vehicles

```http
GET http://localhost:8080/api/v1/vehicle
```

### Response
```json
[
  {
    "id": 16,
    "plateNumber": "7",
    "type": "CAR",
    "name": "mond"
  }
]
```
---

### Parking Lots

#### Create Parking Lot

> **Note:** Occupied spaces are not stored as a column in the `ParkingLot` table because the column is heavily dynamic which means it will be read/updated very often will put a lot of unnecessary load in the database (i mean in a real scenario becos this is just a test) and depends on the current `ParkingRecord` entries.
>
> Instead, occupied spaces are calculated by querying active `ParkingRecord` records and included in the API response. This keeps the `ParkingLot` domain model focused on its static properties while ensuring the occupied and available spaces remain accurate.
```http
POST http://localhost:8080/api/v1/parking-lot
Content-Type: application/json

{
  "location": "aaa",
  "capacity": 5,
  "costPerMin": 50,
  "occupiedSpaces": 0
}
```

#### Get All Parking Lots

```http
GET http://localhost:8080/api/v1/parking-lot
```

#### Get Currently Parked Vehicles

```http
GET http://localhost:8080/api/v1/parking-lot/1/currently-parked
```

---

### Parking

#### Check In Vehicle

```http
POST http://localhost:8080/api/v1/smart-park/check-in
Content-Type: application/json

{
  "vehicleId": 3,
  "parkingId": 1
}
```

#### Get Parking Records

```http
GET http://localhost:8080/api/v1/smart-park
```

#### Check Out Vehicle

```http
PATCH http://localhost:8080/api/v1/smart-park/4/check-out
Content-Type: application/json
```

#### Check Parking Availability

```http
GET http://localhost:8080/api/v1/smart-park/1/check-available
```

---

## Complete IntelliJ HTTP Client Requests

```http
### Login

POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

###

### Create Vehicle

POST http://localhost:8080/api/v1/vehicle
Content-Type: application/json
Authorization: Bearer <your-jwt-token>

{
  "plateNumber": "7",
  "type": "CAR",
  "name": "mond"
}

###

### Get All Vehicles

GET http://localhost:8080/api/v1/vehicle

###

### Create Parking Lot

POST http://localhost:8080/api/v1/parking-lot
Content-Type: application/json

{
  "location": "aaa",
  "capacity": 5,
  "costPerMin": 50
}

###

### Get All Parking Lots

GET http://localhost:8080/api/v1/parking-lot

###

### Check In Vehicle

POST http://localhost:8080/api/v1/smart-park/check-in
Content-Type: application/json

{
  "vehicleId": 3,
  "parkingId": 1
}

###

### Get Parking Records

GET http://localhost:8080/api/v1/smart-park

###

### Check Out Vehicle

PATCH http://localhost:8080/api/v1/smart-park/4/check-out
Content-Type: application/json

###

### Check Parking Availability

GET http://localhost:8080/api/v1/smart-park/1/check-available

###

### Get Currently Parked Vehicles

GET http://localhost:8080/api/v1/parking-lot/1/currently-parked
```

## Endpoint Summary

| Method | Endpoint                                    | Description                        |
| ------ | ------------------------------------------- | ---------------------------------- |
| POST   | `/api/v1/auth/login`                        | Authenticate and receive JWT       |
| POST   | `/api/v1/vehicle`                           | Create a vehicle                   |
| GET    | `/api/v1/vehicle`                           | Get all vehicles                   |
| POST   | `/api/v1/parking-lot`                       | Create a parking lot               |
| GET    | `/api/v1/parking-lot`                       | Get all parking lots               |
| POST   | `/api/v1/smart-park/check-in`               | Check a vehicle into a parking lot |
| GET    | `/api/v1/smart-park`                        | Get parking records                |
| PATCH  | `/api/v1/smart-park/{id}/check-out`         | Check a vehicle out                |
| GET    | `/api/v1/smart-park/{id}/check-available`   | Check parking availability         |
| GET    | `/api/v1/parking-lot/{id}/currently-parked` | Get currently parked vehicles      |


## Exception Handling

The API uses a global exception handler (`GlobalExceptionHandler`) to handle application-specific exceptions and return appropriate HTTP status codes.

### Error Response Format

Most application exceptions return the following format:

```json
{
  "status": 409,
  "message": "Vehicle already exists"
}
```

### Application Exceptions

| Exception | HTTP Status | Description | Example Message |
|---|---:|---|---|
| `VehicleAlreadyExistsException` | `409 Conflict` | Vehicle already exists | `Vehicle already exists` |
| `VehicleAlreadyParkedException` | `422 Unprocessable Content` | Vehicle is already parked | `This vehicle is already parked somewhere. Please Checkout first!` |
| `VehicleNotExistingException` | `422 Unprocessable Content` | Vehicle does not exist | `Vehicle not found` |
| `ParkingLotNotExistingException` | `422 Unprocessable Content` | Parking lot does not exist | `Parking lot not found` |
| `ParkingRecordNotExistingException` | `400 Bad Request` | Vehicle is not parked in the specified parking lot | `This vehicle is not parked here.` |
| `ParkingRecordAlreadyCheckoutException` | `422 Unprocessable Content` | Vehicle has already been checked out | `Vehicle already checkout here.` |
| `ParkingLotFullCapacityException` | `422 Unprocessable Content` | Parking lot has reached its capacity | `Parking spaces for this lot is already at full capacity` |
| `InvalidCredentialsProvidedException` | `401 Unauthorized` | Invalid username or password | `Invalid username or password.` |

### Validation Errors

Invalid request fields are handled using `MethodArgumentNotValidException`.

Example request:

```json
{
  "plateNumber": "",
  "type": "CAR",
  "name": ""
}
```

Example response:

```json
{
  "plateNumber": "Plate number is required",
  "name": "Name is required"
}
```

### Invalid Request Body

Malformed or unreadable JSON request bodies return `400 Bad Request`.

This can happen when an invalid value is provided for a field such as `type`.

Example request:

```json
{
  "plateNumber": "ABC-123",
  "type": "INVALID_TYPE",
  "name": "Toyota"
}
```

Example response:

```json
{
  "status": 400,
  "message": "Invalid request body — check 'type' field and check the value."
}
```

### HTTP Status Summary

| Status | Meaning | Examples |
|---:|---|---|
| `400 Bad Request` | Invalid request or parking record state | `ParkingRecordNotExistingException`, validation errors, invalid request body |
| `401 Unauthorized` | Authentication failed | `InvalidCredentialsProvidedException` |
| `409 Conflict` | Resource already exists | `VehicleAlreadyExistsException` |
| `422 Unprocessable Content` | Request cannot be processed due to the current application state | Vehicle, parking lot, and parking record exceptions |