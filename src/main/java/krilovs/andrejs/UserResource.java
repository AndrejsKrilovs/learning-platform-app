package krilovs.andrejs;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.exception.UserException;
import krilovs.andrejs.request.LoginRequest;
import krilovs.andrejs.service.UserService;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
  @Inject
  private UserService userService;

  @POST
  @Path("/login")
  public UserDomain login(LoginRequest credentials) {
    return userService.authenticateUser(credentials);
  }

  @POST
  @Path("/register")
  public UserDomain register(@Valid UserDomain credentials) {
      return userService.registerUser(credentials);
  }

  @GET
  @Path("/all")
  public List<UserDomain> allUsers() {
    return userService.findAllUsers();
  }
}
