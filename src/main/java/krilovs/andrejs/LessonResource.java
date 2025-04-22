package krilovs.andrejs;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.service.LessonItemService;

import java.util.Map;

@Path("/lessons")
@Produces(MediaType.APPLICATION_JSON)
public class LessonResource {

  @Inject
  LessonItemService lessonService;

  @GET
  @Path("/take/{courseId}")
  public Map<String, ?> showLessonsForSelectedCourse(@PathParam("courseId") Long courseId,
                                                     @QueryParam("page") @DefaultValue("0") Integer pageNumber) {
    return Map.of(
      "lessons", lessonService.showLessonsForSelectedCourse(courseId, pageNumber),
      "metadata", lessonService.getLessonRequestMetadata(),
      "headers", lessonService.getMainFieldNames()
    );
  }
}
