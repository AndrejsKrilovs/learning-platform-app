package krilovs.andrejs;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.LessonItemDomain;
import krilovs.andrejs.exception.CourseException;
import krilovs.andrejs.service.LessonItemService;

import java.util.List;
import java.util.Map;

@Path("/lessons")
@Produces(MediaType.APPLICATION_JSON)
public class LessonResource {
  private final static int ITEM_COUNT_PER_PAGE = 10;

  @Inject
  private LessonItemService lessonService;

  @GET
  @Path("/take/{courseId}")
  public Map<String, ?> showLessonsForSelectedCourse(
    @PathParam("courseId")Long courseId,
    @QueryParam("page") @DefaultValue("0") Integer pageNumber
  ) {
    CourseItemDomain selectedCourse = lessonService.findUserCourse(courseId)
      .orElseThrow(() -> new CourseException(
        "Course not found exception",
        "showLessonsForSelectedCourse",
        "Cannot load lessons for selected course"
      ));

    int startIndex = pageNumber * ITEM_COUNT_PER_PAGE;
    double totalPageCalculation = (double) lessonService.totalLessonsCount() / ITEM_COUNT_PER_PAGE;
    List<LessonItemDomain> lessons = lessonService.showLessonsForSelectedCourse(
      courseId,
      startIndex,
      startIndex + ITEM_COUNT_PER_PAGE
    );

    Map<String, ?> metadata = Map.of(
      "courseName", selectedCourse.getLabel(),
      "currentPage", pageNumber,
      "totalPages", Double.valueOf(Math.ceil(totalPageCalculation)).intValue(),
      "totalElements", lessonService.totalLessonsCount()
    );

    return Map.of(
      "lessons", lessons,
      "metadata", metadata
    );
  }
}
