package com.westminster.config;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        return GrizzlyHttpServerFactory.createHttpServer(
                URI.create(BASE_URI),
                new RestApplication()
        );
    }
    

    public static void main(String[] args) {
        HttpServer server = startServer();
        System.out.println("Smart Campus API running at: " + BASE_URI + "api/v1");
        System.out.println("Press CTRL + C to stop");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            server.shutdownNow();
        }
    }
}