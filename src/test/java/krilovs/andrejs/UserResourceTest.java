package krilovs.andrejs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

@QuarkusTest
class UserResourceTest {
  @Test
  void loginSuccess() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(Map.of("login", "username", "password", "password"))
      .when()
        .post("/users/login")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("login", CoreMatchers.is("username"));
  }

  @Test
  void loginFailed() {
    Map<String, String> responseViolations = Map.of(
      "method", "login", "message", "Incorrect credentials, try again"
    );

    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(Map.of("login", "incorrect_login", "password", "incorrect_password"))
      .when()
        .post("/users/login")
      .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("title", CoreMatchers.containsString("Logging exception"))
        .body("status", CoreMatchers.is(400))
        .body("violations", CoreMatchers.is(List.of(responseViolations)));
  }
}