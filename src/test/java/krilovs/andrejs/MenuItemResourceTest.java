package krilovs.andrejs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import krilovs.andrejs.domain.MenuItemDomain;
import krilovs.andrejs.service.MenuItemService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
class MenuItemResourceTest {
  @Inject
  private MenuItemService service;

  @Test
  void getMenuItems() {
    List<MenuItemDomain> menuItems = List.of(
      new MenuItemDomain(1L, "Menu item 1"),
      new MenuItemDomain(2L, "Menu item 2")
    );

    service.setMenuItems(menuItems);
    JsonArray response = Json.createArrayBuilder()
      .add(Json.createObjectBuilder().add("id", 1).add("label", "Menu item 1").build())
      .add(Json.createObjectBuilder().add("id", 2).add("label", "Menu item 2").build())
      .build();

    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON)
      .when()
        .get("/menuItems")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body(Matchers.is(response.toString()));
  }

  @Test
  void addMenuItemTestSuccess() {
    JsonObject request = Json.createObjectBuilder()
      .add("label", "Menu label")
      .build();

    service.setMenuItems(new ArrayList<>());
    JsonObject response = Json.createObjectBuilder()
      .add("id", 1)
      .add("label", "Menu label")
      .build();

    RestAssured.given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(request.toString())
      .when()
        .post("/menuItems/add")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body(Matchers.is(response.toString()));
  }
}