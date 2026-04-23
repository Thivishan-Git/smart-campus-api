package com.westminster.resource;

import com.westminster.exception.RoomNotEmptyException;
import com.westminster.model.Room;
import com.westminster.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @Context
    private UriInfo uriInfo;

    @GET
    public List<Room> getAllRooms() {
        return new ArrayList<>(DataStore.rooms.values());
    }

    @POST
    public Response createRoom(Room room) {
        validateRoom(room);

        if (DataStore.rooms.containsKey(room.getId())) {
            throw new BadRequestException("Room already exists: " + room.getId());
        }

        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }

        DataStore.rooms.put(room.getId(), room);

        URI uri = uriInfo.getAbsolutePathBuilder().path(room.getId()).build();
        return Response.created(uri).entity(room).build();
    }

    @GET
    @Path("/{roomId}")
    public Room getRoomById(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            throw new NotFoundException("Room not found: " + roomId);
        }

        return room;
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            throw new NotFoundException("Room not found: " + roomId);
        }

        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Cannot delete room because it still has assigned sensors.");
        }

        DataStore.rooms.remove(roomId);
        return Response.noContent().build();
    }

    private void validateRoom(Room room) {
        if (room == null) {
            throw new BadRequestException("Room payload is required.");
        }
        if (room.getId() == null || room.getId().trim().isEmpty()) {
            throw new BadRequestException("Room id is required.");
        }
        if (room.getName() == null || room.getName().trim().isEmpty()) {
            throw new BadRequestException("Room name is required.");
        }
        if (room.getCapacity() <= 0) {
            throw new BadRequestException("Room capacity must be greater than 0.");
        }
    }
}