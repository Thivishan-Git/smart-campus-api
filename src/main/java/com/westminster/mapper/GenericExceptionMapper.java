package com.westminster.mapper;

import com.westminster.model.ErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GenericExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {

        if (exception instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) exception;
            int status = webEx.getResponse().getStatus();

            ErrorMessage error = new ErrorMessage(
                    status,
                    "HTTP_" + status,
                    webEx.getMessage()
            );

            return Response.status(status)
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        LOGGER.log(Level.SEVERE, "Unexpected server error", exception);

        ErrorMessage error = new ErrorMessage(
                500,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred."
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}