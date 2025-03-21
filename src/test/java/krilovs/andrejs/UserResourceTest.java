package krilovs.andrejs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

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
      .add("login", "fake login")
      .add("password", "fake password")
      .build();

    JsonObject errorMessagePart = Json.createObjectBuilder()
      .add("method", "login")
      .add("message", "Incorrect credentials, try again")
      .build();

    JsonObject response = Json.createObjectBuilder()
      .add("title", "Logging exception")
      .add("status", Response.Status.BAD_REQUEST.getStatusCode())
      .add("violations", Json.createArrayBuilder().add(errorMessagePart).build())
      .build();

    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(request.toString())
      .when()
        .post("/users/login")
      .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body(Matchers.is(response.toString()));
  }
}