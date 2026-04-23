package com.westminster.resource;

import com.westminster.exception.LinkedResourceNotFoundException;
import com.westminster.model.Room;
import com.westminster.model.Sensor;
import com.westminster.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @Context
    private UriInfo uriInfo;

    @GET
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {
        List<Sensor> allSensors = new ArrayList<>(DataStore.sensors.values());

        if (type == null || type.trim().isEmpty()) {
            return allSensors;
        }

        return allSensors.stream()
                .filter(sensor -> sensor.getType() != null
                        && sensor.getType().toLowerCase(Locale.ROOT)
                        .equals(type.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }

    @POST
    public Response createSensor(Sensor sensor) {
        validateSensor(sensor);

        if (DataStore.sensors.containsKey(sensor.getId())) {
            throw new BadRequestException("Sensor already exists: " + sensor.getId());
        }

        Room room = DataStore.rooms.get(sensor.getRoomId());
        if (room == null) {
            throw new LinkedResourceNotFoundException(
                    "Room with id " + sensor.getRoomId() + " does not exist."
            );
        }

        if (sensor.getStatus() == null || sensor.getStatus().trim().isEmpty()) {
            sensor.setStatus("ACTIVE");
        }

        DataStore.sensors.put(sensor.getId(), sensor);
        room.getSensorIds().add(sensor.getId());

        URI uri = uriInfo.getAbsolutePathBuilder().path(sensor.getId()).build();
        return Response.created(uri).entity(sensor).build();
    }

    @GET
    @Path("/{sensorId}")
    public Sensor getSensorById(@PathParam("sensorId") String sensorId) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            throw new NotFoundException("Sensor not found: " + sensorId);
        }

        return sensor;
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

    private void validateSensor(Sensor sensor) {
        if (sensor == null) {
            throw new BadRequestException("Sensor payload is required.");
        }
        if (sensor.getId() == null || sensor.getId().trim().isEmpty()) {
            throw new BadRequestException("Sensor id is required.");
        }
        if (sensor.getType() == null || sensor.getType().trim().isEmpty()) {
            throw new BadRequestException("Sensor type is required.");
        }
        if (sensor.getRoomId() == null || sensor.getRoomId().trim().isEmpty()) {
            throw new BadRequestException("Sensor roomId is required.");
        }
    }
}