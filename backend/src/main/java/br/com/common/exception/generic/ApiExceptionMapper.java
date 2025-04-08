package br.com.common.exception.generic;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Map;

import br.com.common.exception.categoria.CategoriaDuplicadaException;
import br.com.common.exception.tag.TagDuplicadaException;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

        @Override
        public Response toResponse(ApiException exception) {
                Map<String, Object> violation = Map.of(
                                "constraintType", "PARAMETER",
                                "message", exception.getMessage(),
                                "path", exception.getPath(),
                                "value", exception.getValue());

                Map<String, Object> responseBody = Map.of(
                                "classViolations", List.of(),
                                "parameterViolations", List.of(violation),
                                "propertyViolations", List.of(),
                                "returnValueViolations", List.of());

                Response.Status status = (exception instanceof CategoriaDuplicadaException
                                || exception instanceof TagDuplicadaException)
                                                ? Response.Status.CONFLICT
                                                : Response.Status.BAD_REQUEST;

                return Response.status(status).entity(responseBody).build();
        }
}