package krilovs.andrejs;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.exception.LoggingException;
import krilovs.andrejs.service.UserService;

import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
  @Inject
  private UserService userService;

  @POST
  @Path("/login")
  public Map<String, String> login(@Valid UserDomain credentials) {
    if (userService.authenticateUser(credentials)) {
      return Map.of("login", credentials.login());
    }

    throw new LoggingException("login", "Incorrect credentials, try again");
  }
}
