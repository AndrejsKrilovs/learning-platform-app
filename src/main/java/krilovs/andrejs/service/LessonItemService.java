package krilovs.andrejs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import krilovs.andrejs.domain.LessonItemDomain;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.repo.LessonItemRepository;
import krilovs.andrejs.request.LessonResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class LessonItemService {
  private String courseName;

  @Inject
  LessonItemRepository lessonItemRepository;

  public Map<String, ?> getLessonRequestMetadata() {
    return Map.of(
      "courseName", courseName,
      "currentPage", lessonItemRepository.getCurrentPage(),
      "totalPages", lessonItemRepository.getTotalPageNumber(),
      "totalElements", lessonItemRepository.getTotalElementsCount()
    );
  }

  public List<LessonResponse> showLessonsForSelectedCourse(Long courseId, Integer pageNumber) {
    return lessonItemRepository.getCourseLessons(courseId, pageNumber)
      .peek(lessonItem -> courseName = Objects.requireNonNullElse(lessonItem.getCourse().getLabel(), ""))
      .map(this::mapLessonResponse)
      .toList();
  }

  private LessonResponse mapLessonResponse(LessonItemDomain entity) {
    UserDomain lecturer = entity.getLecturer();
    return new LessonResponse(
      entity.getId(),
      entity.getName(),
      entity.getStartsAt(),
      "%s %s".formatted(lecturer.getName(), lecturer.getSurname()));
  }
}
