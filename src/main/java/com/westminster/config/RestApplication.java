package com.westminster.config;

import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/v1")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        packages("com.westminster");
    }
}