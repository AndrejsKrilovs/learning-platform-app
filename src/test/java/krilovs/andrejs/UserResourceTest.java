package krilovs.andrejs;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.repo.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@QuarkusTest
class UserResourceTest {

  @InjectMock
  UserRepository repository;

  @Test
  void loginSuccess() {
    Mockito.when(repository.findByLoginAndPassword(Mockito.anyString(), Mockito.anyString()))
      .thenReturn(Optional.of(generateTestUser()));

    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(Map.of("login", "username", "password", "password"))
      .when()
        .post("/users/login")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("login", CoreMatchers.is("username"))
        .body("lastVisitedDate", CoreMatchers.notNullValue(LocalDateTime.class));
  }

  @Test
  void loginFailed() {
    Map<String, String> responseViolations = Map.of(
      "method", "UserResource.login", "message", "Incorrect credentials, try again"
    );

    Mockito.when(repository.findByLoginAndPassword(Mockito.anyString(), Mockito.anyString()))
      .thenReturn(Optional.empty());

    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(Map.of("login", "incorrect_login", "password", "incorrect_password"))
      .when()
        .post("/users/login")
      .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("title", CoreMatchers.containsString("User exception"))
        .body("status", CoreMatchers.is(400))
        .body("violations", CoreMatchers.is(List.of(responseViolations)));
  }

  private UserDomain generateTestUser() {
    UserDomain userToRegister = new UserDomain();
    userToRegister.setLogin("username");
    userToRegister.setPassword("password");
    userToRegister.setName("Name");
    userToRegister.setSurname("Surname");
    userToRegister.setRole("student");
    return userToRegister;
  }
}