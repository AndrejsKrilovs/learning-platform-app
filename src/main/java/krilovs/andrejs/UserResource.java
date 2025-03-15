package krilovs.andrejs;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.exception.LoginException;
import krilovs.andrejs.service.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    public String login(UserDomain credentials) {
        if (userService.authenticateUser(credentials)) {
            return credentials.login();
        }

        throw new LoginException("#login", "Incorrect credentials, try again");
    }
}
