package krilovs.andrejs.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import krilovs.andrejs.dto.LessonDto;
import krilovs.andrejs.service.LessonItemService;

import java.util.Map;

@Path("/lessons")
public class LessonResource extends AbstractController<LessonDto> {

  @Inject
  LessonItemService lessonService;

  @GET
  @Path("/take/{courseId}")
  public Map<String, ?> showLessonsForSelectedCourse(@PathParam("courseId") Long courseId,
                                                     @QueryParam("page") @DefaultValue("0") Integer pageNumber) {
    return showGetResponse(
      lessonService.showLessonsForSelectedCourse(courseId, pageNumber),
      lessonService.getMainFieldNames(),
      lessonService.getLessonRequestMetadata()
    );
  }

  @Override
  public LessonDto findById(Long id) {
    throw new RuntimeException("LessonResource.findById not implemented yet");
  }

  @Override
  public LessonDto add(LessonDto item) {
    throw new RuntimeException("LessonResource.add not implemented yet");
  }

  @Override
  public LessonDto modify(LessonDto item) {
    throw new RuntimeException("LessonResource.modify not implemented yet");
  }

  @Override
  public void remove(Long id) {
    throw new RuntimeException("LessonResource.remove not implemented yet");
  }
}
