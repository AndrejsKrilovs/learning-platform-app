package krilovs.andrejs.repo;

import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.LessonItemDomain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Refactor this class after database implementation
 */
@ApplicationScoped
public class LessonItemRepository {
  private static final int TOTAL_FAKE_LESSONS_PER_COURSE = 20;
  private final List<LessonItemDomain> userLessonItems;
  private List<LessonItemDomain> lessonsForSelectedCourse;
  private final AtomicLong lessonId;
  private final CourseItemRepository courseItemRepository;

  public LessonItemRepository() {
    this.lessonId = new AtomicLong();
    this.courseItemRepository = new CourseItemRepository();
    this.userLessonItems = new CopyOnWriteArrayList<>();
    this.lessonsForSelectedCourse = new CopyOnWriteArrayList<>();
    initFakeLessons();
  }

  public List<LessonItemDomain> getLessonsForCourse(Long courseId) {
    lessonsForSelectedCourse = userLessonItems.stream()
      .filter(lesson -> courseId.equals(lesson.getCourse().getId()))
      .sorted(Comparator.comparing(LessonItemDomain::getStartsAt))
      .toList();

    return lessonsForSelectedCourse;
  }

  public boolean addLesson(LessonItemDomain item) {
    item.setId(lessonId.getAndIncrement());
    return userLessonItems.add(item);
  }

  public Integer totalElementsCount() {
    return lessonsForSelectedCourse.size();
  }

  private void initFakeLessons() {
    Random random = new Random();
    int totalCourseCount = (int) courseItemRepository.count();

    for (long courseIdentifier = 0; courseIdentifier < totalCourseCount; courseIdentifier++) {
      for (long lessonIdentifier = 0; lessonIdentifier < TOTAL_FAKE_LESSONS_PER_COURSE; lessonIdentifier++) {
        LessonItemDomain item = new LessonItemDomain();
        CourseItemDomain course = courseItemRepository
          .findByIdOptional(courseIdentifier)
          .orElse(new CourseItemDomain());
        long beginDate = Objects.requireNonNullElse(course.getStartDate(), LocalDate.now())
          .toEpochSecond(LocalTime.of(0,0), ZoneOffset.ofHours(2));
        long endDate = LocalDateTime.now().plusYears(1L).toEpochSecond(ZoneOffset.ofHours(2));

        item.setName("Lesson %d for course %d".formatted(lessonIdentifier, courseIdentifier));
        item.setCourse(course);
        item.setLecturer("Name Surname");
        item.setStartsAt(LocalDateTime.ofEpochSecond(random.nextLong(beginDate, endDate), 0, ZoneOffset.ofHours(2)));
        addLesson(item);
      }
    }
  }
}
