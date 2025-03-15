package krilovs.andrejs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class UserResourceTest {
  @Test
  void loginTestSuccess() {
    JsonObject request = Json.createObjectBuilder()
      .add("login", "username")
      .add("password", "password")
      .build();

    JsonObject response = Json.createObjectBuilder()
      .add("login", "username")
      .build();

    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(request.toString())
      .when()
        .post("/users/login")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body(Matchers.is(response.toString()));
  }

  @Test
  void loginTestFailed() {
    JsonObject request = Json.createObjectBuilder()
      .add("login", Mockito.anyString())
      .add("password", Mockito.anyString())
      .build();

    JsonObject response = Json.createObjectBuilder()
      .add("methodName", "#login")
      .add("errorMessage", "Incorrect credentials, try again")
      .build();

    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(request.toString())
      .when()
        .post("/users/login")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body(Matchers.is(response.toString()));
  }
}