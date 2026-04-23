# Smart Campus Sensor & Room Management API

## Student Details
- Name: Thevarasa Thivishan
- Student ID: 20232840 / w2153448
- Module: 5COSC022W Client-Server Architectures


## Overview
This project is a RESTful API built using JAX-RS (Jersey) and Grizzly HTTP Server for managing rooms, sensors, and sensor readings in a smart campus environment. The API uses in-memory data structures instead of a database, as required by the coursework.

## Technologies Used
- Java 17
- Maven
- JAX-RS (Jersey)
- Grizzly HTTP Server
- JSON (Jackson)
- Postman
- GitHub

## Features
- Discovery endpoint
- Room management
- Sensor management
- Sensor filtering by type
- Sensor reading history
- Custom exception handling
- Request and response logging
- In-memory data storage

## Discovery
- `GET /`

### Rooms
- `GET /rooms`
- `POST /rooms`
- `GET /rooms/{roomId}`
- `DELETE /rooms/{roomId}`

### Sensors
- `GET /sensors`
- `GET /sensors?type=value`
- `POST /sensors`
- `GET /sensors/{sensorId}`

### Sensor Readings
- `GET /sensors/{sensorId}/readings`
- `POST /sensors/{sensorId}/readings`


## Project Structure
```text
smart-campus-api/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── westminster/
                    ├── config/
                    ├── model/
                    ├── store/
                    ├── exception/
                    ├── mapper/
                    ├── filter/
                    └── resource/

## Conceptual Answers

### Part 1.1 - JAX-RS Lifecycle

By default, JAX-RS usually creates a new resource instance for each request rather than using a single shared resource for all clients. This is safer because request-specific information is not shared between users.

However, in this coursework the in-memory collections are stored in a shared data store, which means the application still has shared state. Because of that, concurrency must be considered carefully. In a larger system, thread-safe collections or synchronization mechanisms would be important to avoid inconsistent updates.

### Part 1.2 - Why Hypermedia is Important

Hypermedia helps the client discover where to go next by providing links or related resource paths in the response. This reduces hard-coded assumptions in the client and makes the API easier to understand and navigate.

In this project, the discovery endpoint provides links to the main collections such as rooms and sensors.

### Part 2.1 - IDs Only vs Full Room Objects

Returning only IDs makes responses smaller and saves bandwidth, especially when there are many related resources. Returning full objects is more convenient for the client because it provides more information in a single request.

In this project, returning full room objects is more useful for testing and demonstration because the API responses are easier to inspect in Postman.

### Part 2.2 - Is DELETE Idempotent

DELETE is considered idempotent because sending the same delete request multiple times does not continue changing the system after the first successful deletion.

Once the room has been removed, further DELETE requests do not create any new effect. The final system state remains the same, which is why the operation is still idempotent.

### Part 3.1 - What Happens if the Client Sends the Wrong Content Type

The POST endpoints use `@Consumes(MediaType.APPLICATION_JSON)`, which means the API expects JSON request bodies.

If a client sends a different content type such as plain text or XML, JAX-RS will not process the request as valid JSON. This prevents the API from accepting invalid data formats as normal input.

### Part 3.2 - Why Query Parameters Are Better for Filtering

Query parameters are more suitable for filtering because the client is still requesting the same collection resource, but with optional conditions applied.

For example, `/sensors?type=CO2` clearly means the sensors collection filtered by type. This is more flexible and easier to extend later than encoding filters directly into the path.

### Part 4.1 - Benefits of the Sub-Resource Locator Pattern

The sub-resource locator pattern improves code organization by moving nested resource logic into a dedicated class.

In this project, sensor-related logic is handled in `SensorResource`, while reading history is handled in `SensorReadingResource`. This makes the code easier to read, maintain, and extend.

### Part 4.2 - Historical Data Management

Each sensor can produce many readings over time, so the project stores reading history in a list associated with the sensor ID.

When a new reading is added, it is stored in the history list and the sensor’s `currentValue` is updated. This allows the API to provide both the latest sensor state and the full historical record.

### Part 5.1 - Why 422 Is Better Than 404 Here

HTTP 422 is more appropriate when the endpoint exists and the JSON format is valid, but one field inside the request is semantically incorrect.

In this case, a `roomId` may refer to a room that does not exist. A 404 would normally mean that the endpoint itself could not be found, which is a different problem.

### Part 5.2 - Why Stack Traces Should Not Be Exposed

Stack traces should not be exposed because they reveal internal information such as class names, package structure, methods, and framework details.

This creates security risks and can also confuse clients with technical output they do not understand. Instead, the API should log detailed errors internally and return safe, structured error responses externally.

### Part 5.3 - Why Use Filters for Logging

Filters are better for logging because logging is a cross-cutting concern that affects all requests and responses.

If logging code is written inside every resource method, the code becomes repetitive and harder to maintain. By using request and response filters, logging is applied consistently across the whole API from one central place.