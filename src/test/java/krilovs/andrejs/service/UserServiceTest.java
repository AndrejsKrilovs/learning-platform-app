package krilovs.andrejs.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import krilovs.andrejs.domain.UserDomain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class UserServiceTest {
  @Inject
  private UserService userService;

  @Test
  void authenticateUserSuccessTest() {
    UserDomain successUser = new UserDomain("username", "password");
    Assertions.assertTrue(userService.authenticateUser(successUser));
  }

  @Test
  void authenticateUserFailedTest() {
    UserDomain randomUser = new UserDomain(Mockito.anyString(), Mockito.anyString());
    Assertions.assertFalse(userService.authenticateUser(randomUser));
  }
}