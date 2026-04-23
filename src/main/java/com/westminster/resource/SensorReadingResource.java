package com.westminster.resource;

import com.westminster.exception.SensorUnavailableException;
import com.westminster.model.Sensor;
import com.westminster.model.SensorReading;
import com.westminster.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getAllReadings() {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found: " + sensorId);
        }

        return DataStore.getOrCreateReadings(sensorId);
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found: " + sensorId);
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException(
                    "Cannot add reading because sensor is in MAINTENANCE state."
            );
        }

        if (reading == null) {
            throw new BadRequestException("Reading payload is required.");
        }

        if (reading.getId() == null || reading.getId().trim().isEmpty()) {
            reading.setId(UUID.randomUUID().toString());
        }

        if (reading.getTimestamp() <= 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        DataStore.getOrCreateReadings(sensorId).add(reading);
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}