# Smart Campus Sensor & Room Management API

## Student Details
- Name: Thevarasa Thivishan
- Student ID: 20232840 / w2153448
- Module: 5COSC022W Client-Server Architectures


## Overview
This project is a RESTful API for a smart campus system. It is used to manage rooms, sensors, and sensor readings.
The main idea of this API is to help store and manage smart campus data in an organized way. For example, a room can have sensors, and each sensor can store readings like temperature or CO2 values.
This project solves the problem of managing room and sensor data manually. Instead of keeping everything in random files or writing it down by hand, this API gives a proper system to create, view, and manage the data.
This project uses in-memory storage, which means the data is stored only while the server is running. If the server stops, the data will be lost.

## Technologies Used
- **Java**
- **Maven**
- **JAX-RS / Jersey**
- **Grizzly HTTP Server**
- **Jackson / JSON**
- **Postman**
- **Git / GitHub**

## Main Features
- Room management
- Sensor management
- Sensor readings
- Filtering sensors by type
- Logging requests and responses
- Custom exception handling

## API Endpoints

### Discovery
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



How to Run the Project
1. Open the project in Apache NetBeans or VS Code.
2. Make sure Java and Maven are installed.
3. Open the terminal inside the project folder.
4. Run these commands:
mvn clean compile
mvn exec:java

5. The server will run on: http://localhost:8080/
Example JSON Requests
Create Room
{
  "id": "LIB-301",
  "name": "Library Quiet Study",
  "capacity": 40
}

Create Sensor
{
  "id": "CO2-001",
  "type": "CO2",
  "status": "ACTIVE",
  "currentValue": 0.0,
  "roomId": "LIB-301"
}

Add Reading
{
  "value": 550.5
}

Sample curl Commands
Get Discovery Endpoint
curl http://localhost:8080/

Get All Rooms
curl http://localhost:8080/rooms

Create Room
curl -X POST http://localhost:8080/rooms \
-H "Content-Type: application/json" \
-d "{\"id\":\"LIB-301\",\"name\":\"Library Quiet Study\",\"capacity\":40}"

Get All Sensors
curl http://localhost:8080/sensors

Create Sensor
curl -X POST http://localhost:8080/sensors \
-H "Content-Type: application/json" \
-d "{\"id\":\"CO2-001\",\"type\":\"CO2\",\"status\":\"ACTIVE\",\"currentValue\":0.0,\"roomId\":\"LIB-301\"}"

Get Sensor Readings
curl http://localhost:8080/sensors/CO2-001/readings

Add Sensor Reading
curl -X POST http://localhost:8080/sensors/CO2-001/readings \
-H "Content-Type: application/json" \
-d "{\"value\":550.5}"


Error Handling

This API also handles errors properly.
•	403 Forbidden – This happens when a reading is added to a sensor that is in maintenance mode.

•	404 Not Found – This happens when the client tries to access a room or sensor that does not exist.

•	409 Conflict – This happens when trying to delete a room that still has sensors inside it.

•	422 Unprocessable Entity – This happens when a sensor is created with a room ID that does not exist.
Notes

•	This project uses in-memory storage, so data is lost when the server stops.


•	This API was made for coursework purposes.

•	Postman was used to test all endpoints.

•	Git and GitHub were used to save and manage the code.



