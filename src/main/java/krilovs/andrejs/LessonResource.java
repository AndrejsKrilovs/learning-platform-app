package krilovs.andrejs;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

@Path("/lessons")
@Produces(MediaType.APPLICATION_JSON)
public class LessonResource {

  @GET
  @Path("/take/{courseId}")
  public Map<String, ?> showLessonsForSelectedCourse(
    @PathParam("courseId") Long courseId,
    @QueryParam("page") @DefaultValue("0") Integer pageNumber
  ) {

    Map<String, ?> metadata = Map.of(
      "courseName", "",
      "currentPage", 0,
      "totalPages", 0,
      "totalElements", 0
    );

    return Map.of(
      "lessons", List.of(),
      "metadata", metadata
    );
  }
}
