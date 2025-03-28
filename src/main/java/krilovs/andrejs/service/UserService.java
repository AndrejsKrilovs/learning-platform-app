package krilovs.andrejs.service;

import jakarta.inject.Singleton;
import krilovs.andrejs.domain.UserDomain;

@Singleton
public class UserService {
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";

  public Boolean authenticateUser(UserDomain credentials) {
    return
      credentials.login().equalsIgnoreCase(USERNAME) &&
      credentials.password().equalsIgnoreCase(PASSWORD);
  }
}
