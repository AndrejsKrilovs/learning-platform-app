package krilovs.andrejs.service;

import jakarta.inject.Singleton;
import krilovs.andrejs.domain.UserDomain;

@Singleton
public class UserService {
  public Boolean authenticateUser(UserDomain credentials) {
    return
      credentials.login().equalsIgnoreCase("username") &&
      credentials.password().equalsIgnoreCase("password");
  }
}
