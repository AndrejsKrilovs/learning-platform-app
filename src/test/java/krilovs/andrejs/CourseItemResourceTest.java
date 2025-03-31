package krilovs.andrejs;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@QuarkusTest
class CourseItemResourceTest {
  @Test
  void getCourseItems() {
    RestAssured.given()
        .contentType(ContentType.JSON)
      .when()
        .get("/courseItems")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("headers", CoreMatchers.is(List.of("id","label","startDate","price","currency")))
        .body("items", CoreMatchers.any(List.class))
        .body("metadata", CoreMatchers.is(Map.of("totalPages",2,"currentPage",0,"totalElements",10)));
  }

  @Test
  void addCourseItemSuccess() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(Map.of("label", "New Course", "startDate", "2026-01-01", "price", 13.13))
      .when()
        .post("/courseItems/add")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("id", CoreMatchers.notNullValue());
  }

  @Test
  void addCourseItemFailed() {
    List<Map<String, String>> violationResponse = List.of(
      Map.of("field", "addCourseItem.courseItem.label", "message", "Course item should be defined")
    );

    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(Map.of(
          "label", Mockito.anyString(),
          "startDate", LocalDate.parse("2026-01-01"),
          "price", BigDecimal.ZERO)
        )
      .when()
        .post("/courseItems/add")
      .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("id", CoreMatchers.nullValue())
        .body("title", CoreMatchers.containsString("Constraint Violation"))
        .body("status", CoreMatchers.is(400))
        .body("violations", CoreMatchers.is(violationResponse));
  }

  @Test
  void takeCourseSuccess() {
    RestAssured.given()
        .contentType(ContentType.JSON)
      .when()
        .get("/courseItems/take/0")
      .then()
        .statusCode(Response.Status.NO_CONTENT.getStatusCode());
  }

  @Test
  void takeCourseFailed() {
    List<Map<String, String>> notExistingCoursesResponse = List.of(
      Map.of("method", "takeCourse", "message", "Selected course does not exists")
    );

    RestAssured.given()
        .contentType(ContentType.JSON)
      .when()
        .get("/courseItems/take/10000")
      .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("title", CoreMatchers.containsString("Taking course exception"))
        .body("status", CoreMatchers.is(400))
        .body("violations", CoreMatchers.is(notExistingCoursesResponse));
    ;
  }
}