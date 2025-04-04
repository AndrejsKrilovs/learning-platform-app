package krilovs.andrejs;

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

import java.util.List;
import java.util.Map;

@Path("/courseItems")
@Produces(MediaType.APPLICATION_JSON)
public class CourseItemResource {
  private final static int ITEM_COUNT_PER_PAGE = 10;

  @GET
  public Map<String, ?> getCourseItems(@QueryParam("page") @DefaultValue("0") Integer pageNumber) {
    Map<String, ?> metadata = Map.of(
      "currentPage", 0,
      "totalPages", 0,
      "totalElements", 0
    );

    return Map.of(
      "items", List.of(),
      "headers", List.of(),
      "metadata", metadata
    );
  }

  @POST
  @Path("/add")
  public CourseItemDomain addCourseItem(@Valid CourseItemDomain courseItem) {
    return courseItem;
  }

  @GET
  @Path("/take/{id}")
  public void takeCourse(@PathParam("id") Long courseId) {
    throw new CourseException(
      "Taking course exception",
      "takeCourse",
      "User cannot take more than %d courses".formatted(ITEM_COUNT_PER_PAGE)
    );
  }

  @GET
  @Path("/userCourses")
  public Map<String, ?> getUserCourseItems() {
    return Map.of(
      "items", List.of(),
      "headers",List.of(),
      "metadata", Map.of("totalElements",0)
    );
  }
}
