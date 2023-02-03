package dev.vicaw.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<String> errorMessages = e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .collect(Collectors.toList());
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponseBody(400, errorMessages)).build();
    }

    @Data
    public static final class ErrorResponseBody {
        private final int code;
        private final List<String> message;
    }
}