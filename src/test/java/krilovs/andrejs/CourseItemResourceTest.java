package krilovs.andrejs;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.repo.CourseItemRepository;
import krilovs.andrejs.repo.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@QuarkusTest
class CourseItemResourceTest {

  @InjectMock
  CourseItemRepository courseRepository;

  @InjectMock
  UserRepository userRepository;

  @Test
  void getCourseItems() {
    Mockito.when(courseRepository.getAvailableCourses(Mockito.anyString(), Mockito.anyInt()))
      .thenReturn(generateValidCourseItemList());
    Mockito.when(courseRepository.getTotalElementsCountForAvailableCourses())
      .thenReturn((long) generateValidCourseItemList().size());
    Mockito.when(courseRepository.getTotalPageNumberForAvailableCourses())
      .thenReturn(1);

    RestAssured.given()
        .contentType(ContentType.JSON)
      .when()
        .get("/courseItems")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("headers", CoreMatchers.is(List.of("id","label","startDate","price","currency")))
        .body("items", CoreMatchers.any(List.class))
        .body("metadata", CoreMatchers.is(Map.of("totalPages",1,"currentPage",0,"totalElements",1)));
  }

  @Test
  void addCourseItemSuccess() {
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(Map.of("label", "New Course", "startDate", LocalDate.now(), "price", 13.13))
      .when()
        .post("/courseItems/add")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body(CoreMatchers.notNullValue());
  }

  @Test
  void addCourseItemFailed() {
    Map<String, Object> bodyRequest = Map.of(
      "label", "","startDate", LocalDate.now(),"price", BigDecimal.ZERO
    );

    List<Map<String, String>> violationResponse = List.of(
      Map.of("field", "addCourseItem.courseItem.label", "message", "Course item should be defined")
    );

    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(bodyRequest)
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
    Mockito.when(userRepository.findByIdOptional(Mockito.anyString(), Mockito.any()))
      .thenReturn(Optional.of(generateValidUser()));
    Mockito.when(courseRepository.findByIdOptional(Mockito.anyLong(), Mockito.any()))
      .thenReturn(Optional.of(generateValidCourseItemList().getFirst()));

    RestAssured.given()
        .contentType(ContentType.JSON)
        .header("username", generateValidUser().getLogin())
      .when()
        .get("/courseItems/take/1")
      .then()
        .statusCode(Response.Status.OK.getStatusCode())
        .body(CoreMatchers.notNullValue());
  }

  @Test
  void takeCourseFailed() {
    Mockito.when(userRepository.findByIdOptional(Mockito.anyString(), Mockito.any()))
      .thenReturn(Optional.of(generateValidUser()));

    List<Map<String, String>> notExistingCoursesResponse = List.of(
      Map.of("method", "CourseItemService.takeCourseToUser", "message", "Course with id 10000 not exists")
    );

    RestAssured.given()
        .contentType(ContentType.JSON)
        .header("username", generateValidUser().getLogin())
      .when()
        .get("/courseItems/take/10000")
      .then()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("title", CoreMatchers.containsString("Course not found"))
        .body("status", CoreMatchers.is(400))
        .body("violations", CoreMatchers.is(notExistingCoursesResponse));
  }

  private List<CourseItemDomain> generateValidCourseItemList() {
    CourseItemDomain courseItem = new CourseItemDomain();
    courseItem.setId(1L);
    courseItem.setLabel("Valid course label");
    courseItem.setPrice(BigDecimal.ONE);
    courseItem.setStartDate(LocalDate.now());
    return List.of(courseItem);
  }

  private UserDomain generateValidUser() {
    UserDomain validUser = new UserDomain();
    validUser.setLogin("username");
    validUser.setUserCourses(new ArrayList<>());
    return validUser;
  }
}