package krilovs.andrejs;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.exception.CourseException;
import krilovs.andrejs.service.CourseItemService;

import java.util.Map;

@Path("/courseItems")
@Produces(MediaType.APPLICATION_JSON)
public class CourseItemResource {

  @Inject
  private CourseItemService service;

  @GET
  public Map<String, ?> getCourseItems(@QueryParam("page") @DefaultValue("0") Integer pageNumber) {
    return Map.of(
      "items", service.getItems(pageNumber),
      "headers", service.getMainFieldNames(),
      "metadata", service.getRequestMetadata()
    );
  }

  @GET
  @Path("/{id}")
  public CourseItemDomain findCourseById(@PathParam("id") long itemId) {
    return service.findItemById(itemId)
      .orElseThrow(() -> new CourseException(
        "Course not found",
        "courseItemResource.findCourseById",
        "Course with current identifier %d not found".formatted(itemId)
      ));
  }

  @POST
  @Path("/add")
  public CourseItemDomain addCourseItem(@Valid CourseItemDomain courseItem) {
    return service.addOrModifyItem(courseItem);
  }

  @PATCH
  @Path("/update")
  public CourseItemDomain modifyCourseItem(@Valid CourseItemDomain courseItem) {
    return service.addOrModifyItem(courseItem);
  }

  @DELETE
  @Path("/remove/{id}")
  public void removeCourseItem(@PathParam("id") long itemId) {
    service.removeItem(itemId);
  }

//  @GET
//  @Path("/take/{id}")
//  public void takeCourse(@PathParam("id") Long courseId) {
//    if (service.getUserCourseItems().size() < ITEM_COUNT_PER_PAGE) {
//      service.takeCourse(courseId)
//        .orElseThrow(() -> new CourseException(
//          "Taking course exception",
//          "takeCourse",
//          "Selected course does not exists"
//        ));
//      return;
//    }
//
//    throw new CourseException(
//      "Taking course exception",
//      "takeCourse",
//      "User cannot take more than %d courses".formatted(ITEM_COUNT_PER_PAGE)
//    );
//  }

//  @GET
//  @Path("/userCourses")
//  public Map<String, ?> getUserCourseItems() {
//    List<CourseItemDomain> userCourseList = service.getUserCourseItems();
//
//    return Map.of(
//      "items", userCourseList,
//      "headers", Stream.of(CourseItemDomain.class.getDeclaredFields()).map(Field::getName).toList(),
//      "metadata", Map.of("totalElements", userCourseList.size())
//    );
//  }
}
