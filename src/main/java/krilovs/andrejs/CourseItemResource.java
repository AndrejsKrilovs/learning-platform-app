package krilovs.andrejs;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.domain.CourseItemDomain;
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
      "metadata", service.getAvailableCoursesRequestMetadata()
    );
  }

  @GET
  @Path("/{id}")
  public CourseItemDomain findCourseById(@PathParam("id") Long itemId) {
    return service.findItemById(itemId);
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
  public void removeCourseItem(@PathParam("id") Long itemId) {
    service.removeItem(itemId);
  }

  @GET
  @Path("/take/{courseId}")
  public CourseItemDomain takeCourse(@PathParam("courseId") Long courseId, @HeaderParam("username") String username) {
    return service.takeCourseToUser(username, courseId);
  }

  @GET
  @Path("/userCourses")
  public Map<String, ?> getUserCourseItems(@HeaderParam("username") String username) {
    return Map.of(
      "items", service.getUserCourseItems(username),
      "headers", service.getMainFieldNames(),
      "metadata", service.getUserCoursesRequestMetadata()
    );
  }
}
