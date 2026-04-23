# Smart Campus Sensor & Room Management API

## Student Details
- Name: Thevarasa Thivishan
- Student ID: 20232840 / w2153448
- Module: 5COSC022W Client-Server Architectures


## Overview
This project is a RESTful API built using JAX-RS (Jersey) and Grizzly HTTP Server for managing rooms, sensors, and sensor readings in a smart campus environment. The API uses in-memory data structures instead of a database, as required by the coursework.


## Part 1.2 - Why Hypermedia is Important.

Hypermedia assists the client to find the next place to go by giving him a link back or providing him with a path of related resources in the response. This minimizes the amount of hard-coded assumptions in the client and simplifies the API to read and navigate. The discovery endpoint in this project is a link to the core collections like rooms and sensors.

## Part 2.1 - IDs Only vs Full Room Objects.

Sending out only IDs makes responses smaller and saves on bandwidth, particularly when there are numerous related resources. Sending complete objects is more convenient to the client since he can get more information at a single request. The full room objects are more convenient in this project to test and demonstrate since the responses of the API are easier to view in Postman.

## 2.2 - Is DELETE Idempotent.

DELETE is said to be idempotent since making the same delete request again and again does not continue to alter the system even when the initial deletion has been successfully accomplished. After removing the room, no new effect is created when making additional DELETE requests. The resulting system state is the same and hence the operation is idempotent.

## Part 3.1 - What Will Happen when the Client Sends the Wrong Type of Content.

The POST endpoints consume Media type via @Consumes(Mediatype.APPLICATION_JSON) and the API uses JSON request bodies. When a client makes a request in another form of content like plain text or XML, JAX-RS will not handle the request as valid JSON. This helps the API to avoid accepting non-valid data formats as inputs.

## Part 3.2 - Why Query Parameters are superior with respect to filtering.

Filtering with query parameters would be more appropriate since the client is still asking the same collection resource with optional conditions applied. In the case of /sensors?type=CO2, it is clear that the sensors collection is being filtered by type. This can be extended more easily and flexibly than encoding filters directly into the path.

## Part 4.1 - The advantages of the Sub-Resource Locator Pattern.

The sub-resource locator pattern is used to enhance organization by putting nested resource logic into a specific class. Sensor-specific information in this project is managed by SensorResource and sensor-reading history is managed by SensorReadingResource. This simplifies the code to read, maintain and expand.

## Part 4.3 -Companies and organisations in historical contexts.

The sensors are capable of generating numerous readings as time goes on and, therefore, the project keeps a history of the reading in a list linked with the sensor ID. Once a new reading is added, the new reading is stored in the history list and the current value of the sensor is changed. This enables the API to give the most recent sensor state as well as the entire history of sensor state.

## Part 5.1 – Why 422 Is Better Than 404 Here

The HTTP 422 is more suitable when the endpoint is present and the request is in the JSON format, but one of the fields is semantically wrong. Here, a roomId can be a non-existent room. A 404 would normally imply that the endpoint itself was not found, which is another kind of issue.

## Part 5.2 - Stack Traces should not be exposed.

Stack traces are not to be publicized as they give internal information that includes class names, package structure, methods and framework. This may introduce security threats, as well as provide clients with confusing technical output. Instead, the API must record detailed errors internally and respond to the external error with safe and well-structured error responses.

## Part 5.3.1 - Reasoning why you should use filters to log.

Logging is more suited to filters since logging is a cross-cutting issue, impacting all requests and responses. The inclusion of logging code within the body of each resource method would cause duplication and make the code more difficult to maintain. With request and response filters, logging is implemented in the API in a single central location.



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
