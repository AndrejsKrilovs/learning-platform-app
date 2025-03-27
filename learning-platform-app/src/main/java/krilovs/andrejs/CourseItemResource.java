package krilovs.andrejs;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.exception.CourseException;
import krilovs.andrejs.service.CourseItemService;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Path("/courseItems")
@Produces(MediaType.APPLICATION_JSON)
public class CourseItemResource {
  private final static int ITEM_COUNT_PER_PAGE = 10;

  @Inject
  private CourseItemService service;

  @GET
  public Map<String, ?> getCourseItems(@QueryParam("page") @DefaultValue("0") Integer pageNumber) {
    int startIndex = pageNumber * ITEM_COUNT_PER_PAGE;
    double totalPageCalculation = (double) service.totalElementCount() / ITEM_COUNT_PER_PAGE;
    List<CourseItemDomain> courseItems = service.getCourseItems(startIndex, startIndex + ITEM_COUNT_PER_PAGE);

    Map<String, ?> metadata = Map.of(
      "currentPage", pageNumber,
      "totalPages", Double.valueOf(Math.ceil(totalPageCalculation)).intValue(),
      "totalElements", courseItems.size()
    );

    return Map.of(
      "items", courseItems,
      "headers", Stream.of(CourseItemDomain.class.getDeclaredFields()).map(Field::getName).toList(),
      "metadata", metadata
    );
  }

  @POST
  @Path("/add")
  public CourseItemDomain addCourseItem(@Valid CourseItemDomain courseItem) {
    return service.addCourseItem(courseItem);
  }

  @GET
  @Path("/take/{id}")
  public void takeCourse(@PathParam("id") Long courseId) {
    if (service.getUserCourseItems().size() <= ITEM_COUNT_PER_PAGE) {
      service.takeCourse(courseId)
        .orElseThrow(() -> new CourseException("Taking course exception", "takeCourse", "Cannot take this course"));
      return;
    }

    throw new CourseException(
      "Taking course exception",
      "takeCourse",
      "User cannot take more than %d courses".formatted(ITEM_COUNT_PER_PAGE)
    );
  }

  @GET
  @Path("/userCourses")
  public Map<String, ?> getUserCourseItems() {
    List<CourseItemDomain> userCourseList = service.getUserCourseItems();

    return Map.of(
      "items", userCourseList,
      "headers", Stream.of(CourseItemDomain.class.getDeclaredFields()).map(Field::getName).toList(),
      "metadata", Map.of("totalElements", userCourseList.size())
    );
  }
}
