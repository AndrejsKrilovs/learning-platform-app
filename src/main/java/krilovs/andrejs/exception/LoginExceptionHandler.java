package krilovs.andrejs.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Map;

@Provider
public class LoginExceptionHandler implements ExceptionMapper<LoginException> {
  @Override
  public Response toResponse(LoginException exception) {
    ExceptionResponse response = new ExceptionResponse(
      "Logging exception",
      Response.Status.BAD_REQUEST.getStatusCode(),
      List.of(Map.of("method", exception.getFailedMethodName(), "message", exception.getMessage()))
    );

    return Response.ok(response).status(Response.Status.BAD_REQUEST).build();
  }
}
