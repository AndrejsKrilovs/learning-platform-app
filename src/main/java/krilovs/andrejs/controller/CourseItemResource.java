package krilovs.andrejs.controller;

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
import jakarta.ws.rs.QueryParam;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.service.CourseItemService;

import java.util.Map;

@Path("/courseItems")
public class CourseItemResource extends AbstractController<CourseItemDomain> {

  @Inject
  CourseItemService service;

  @GET
  public Map<String, ?> getCourseItems(@HeaderParam("username") String username,
                                       @QueryParam("page") @DefaultValue("0") Integer pageNumber) {
    return showGetResponse(
      service.getItems(username, pageNumber),
      service.getMainFieldNames(),
      service.getAvailableCoursesRequestMetadata()
    );
  }

  @GET
  @Path("/{id}")
  @Override
  public CourseItemDomain findById(@PathParam("id") Long itemId) {
    return service.findItemById(itemId);
  }

  @POST
  @Path("/add")
  @Override
  public CourseItemDomain add(@Valid CourseItemDomain courseItem) {
    return service.addOrModifyItem(courseItem);
  }

  @PATCH
  @Path("/update")
  @Override
  public CourseItemDomain modify(@Valid CourseItemDomain courseItem) {
    return service.addOrModifyItem(courseItem);
  }

  @DELETE
  @Override
  @Path("/remove/{id}")
  public void remove(@PathParam("id") Long itemId) {
    service.removeItem(itemId);
  }

  @GET
  @Path("/take/{courseId}")
  public CourseItemDomain takeCourse(@PathParam("courseId") Long courseId,
                                     @HeaderParam("username") String username) {
    return service.takeCourseToUser(username, courseId);
  }

  @GET
  @Path("/userCourses")
  public Map<String, ?> getUserCourseItems(@HeaderParam("username") String username,
                                           @QueryParam("page") @DefaultValue("0") Integer pageNumber) {
    return showGetResponse(
      service.getUserCourses(username, pageNumber),
      service.getMainFieldNames(),
      service.getUserCoursesRequestMetadata()
    );
  }
}
