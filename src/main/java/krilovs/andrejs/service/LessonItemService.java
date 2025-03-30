package krilovs.andrejs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.LessonItemDomain;
import krilovs.andrejs.repo.LessonItemRepository;
import krilovs.andrejs.repo.UserCourseItemRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LessonItemService {
  @Inject
  UserCourseItemRepository userCourseItemRepository;

  @Inject
  LessonItemRepository lessonItemRepository;

  public List<LessonItemDomain> showLessonsForSelectedCourse(Long courseId, Integer startElementNumber, Integer lastElementNumber) {
    return lessonItemRepository.getLessonsForCourse(courseId).subList(startElementNumber, lastElementNumber);
  }

  public int totalLessonsForSelectedCourse() {
    return lessonItemRepository.totalElementsCount();
  }

  public Optional<CourseItemDomain> findUserCourse(Long courseId) {
    return userCourseItemRepository.findById(courseId);
  }
}
