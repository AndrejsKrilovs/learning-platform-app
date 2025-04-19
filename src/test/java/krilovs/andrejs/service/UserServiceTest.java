package krilovs.andrejs.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.exception.UserException;
import krilovs.andrejs.repo.UserRepository;
import krilovs.andrejs.request.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

@QuarkusTest
class UserServiceTest {
  @InjectMock
  UserRepository userRepository;

  @Inject
  UserService userService;

  @Test
  void authenticateUserSuccess() {
    Mockito.when(userRepository.findByLoginAndPassword(Mockito.anyString(), Mockito.anyString()))
      .thenReturn(Optional.of(new UserDomain()));

    LoginRequest userLogin = new LoginRequest("login", BcryptUtil.bcryptHash("pwd"));
    Optional<UserDomain> result = userService.authenticateUser(userLogin);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertNotNull(result.get().getLastVisitedDate());
  }

  @Test
  void authenticateUserFailed() {
    Mockito.when(userRepository.findByLoginAndPassword(Mockito.anyString(), Mockito.anyString()))
      .thenReturn(Optional.empty());

    LoginRequest userLogin = new LoginRequest("login", BcryptUtil.bcryptHash("pwd"));
    Optional<UserDomain> result = userService.authenticateUser(userLogin);
    Assertions.assertFalse(result.isPresent());
  }

  @Test
  void registerUserSuccess() {
    Mockito.when(userRepository.findByIdOptional(Mockito.anyString(), Mockito.any())).thenReturn(Optional.empty());

    UserDomain result = userService.registerUser(generateTestUser());
    Assertions.assertNotNull(result.getRegisteredDate());
    Mockito.verify(userRepository, Mockito.atMostOnce()).persist(generateTestUser());
  }

  @Test
  void registerDuplicateUser() {
    Mockito.when(userRepository.findByIdOptional(Mockito.anyString(), Mockito.any()))
      .thenReturn(Optional.of(new UserDomain()));

    UserException registeredResult = Assertions.assertThrows(
      UserException.class, () -> userService.registerUser(generateTestUser())
    );

    Assertions.assertEquals("User 'login' already exists in system", registeredResult.getMessage());
    Mockito.verify(userRepository, Mockito.never()).persist(generateTestUser());
  }

  @Test
  void findAllUsers() {
    Mockito.when(userRepository.getUserList()).thenReturn(List.of(new UserDomain()));
    List<UserDomain> resultList = userService.findAllUsers();
    Assertions.assertNotNull(resultList);
    Assertions.assertFalse(resultList.isEmpty());
  }

  private UserDomain generateTestUser() {
    UserDomain userToRegister = new UserDomain();
    userToRegister.setLogin("login");
    userToRegister.setPassword(BcryptUtil.bcryptHash("pwd"));
    userToRegister.setName("Name");
    userToRegister.setSurname("Surname");
    userToRegister.setRole("student");
    return userToRegister;
  }
}