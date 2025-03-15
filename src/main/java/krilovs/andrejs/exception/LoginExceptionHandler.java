package krilovs.andrejs.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LoginExceptionHandler implements ExceptionMapper<LoginException> {
  @Override
  public Response toResponse(LoginException exception) {
    ExceptionResponse response = new ExceptionResponse(
      exception.getFailedMethodName(),
      exception.getMessage()
    );

    return Response.ok(response).build();
  }
}
