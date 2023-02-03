package dev.vicaw.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.Data;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof ApiException) {
            ApiException e = (ApiException) exception;
            return Response.status(Response.Status.fromStatusCode(e.getCode()))
                    .entity(new ErrorResponseBody(e.getCode(), e.getMessage()))
                    .build();
        }

        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponseBody(500, "Algo inesperado aconteceu. Tente novamente."))
                .build();
    }

    @Data
    public static final class ErrorResponseBody {

        private final int code;
        private final String message;

    }
}