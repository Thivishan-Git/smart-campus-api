package com.westminster.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {

    @GET
    public Map<String, Object> getDiscovery() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("name", "Smart Campus Sensor API");
        data.put("version", "v1");
        data.put("contact", "w2153448@westminster.ac.uk");

        Map<String, String> resources = new LinkedHashMap<>();
        resources.put("rooms", "/api/v1/rooms");
        resources.put("sensors", "/api/v1/sensors");

        data.put("resources", resources);

        return data;
    }
}