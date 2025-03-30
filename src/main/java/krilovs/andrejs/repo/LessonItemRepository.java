package krilovs.andrejs.repo;

import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.domain.LessonItemDomain;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Refactor this class after database implementation
 */
@ApplicationScoped
public class LessonItemRepository {
  private static final int TOTAL_FAKE_COURSE_ITEMS = 18;
  private static final int TOTAL_FAKE_LESSONS_PER_COURSE = 20;
  private final List<LessonItemDomain> userLessonItems;
  private final AtomicLong lessonId;

  public LessonItemRepository() {
    this.lessonId = new AtomicLong();
    this.userLessonItems = new CopyOnWriteArrayList<>();
    initFakeLessons();
  }

  public List<LessonItemDomain> getLessonsForCourse(Long courseId) {
    return userLessonItems.stream()
      .filter(lesson -> courseId.equals(lesson.getCourseId()))
      .toList();
  }

  public int totalElementCount() {
    return userLessonItems.size();
  }

  private void initFakeLessons() {
    for (long courseIdentifier = 0; courseIdentifier < TOTAL_FAKE_COURSE_ITEMS; courseIdentifier++) {
      for (long lessonIdentifier = 0; lessonIdentifier < TOTAL_FAKE_LESSONS_PER_COURSE; lessonIdentifier++) {
        long nextLong = lessonId.getAndIncrement();
        LessonItemDomain item = new LessonItemDomain(nextLong, "Lesson with id: %d".formatted(nextLong), courseIdentifier);
        userLessonItems.add(item);
      }
    }
  }
}
