package krilovs.andrejs.repo;

import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.domain.CourseItemDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Refactor this class after database implementation
 */
@ApplicationScoped
public class CourseItemRepository {
  private static final int TOTAL_FAKE_COURSE_ITEMS = 18;
  private final List<CourseItemDomain> courseItems;
  private final AtomicLong courseId;

  public CourseItemRepository() {
    courseId = new AtomicLong();
    courseItems = new CopyOnWriteArrayList<>();
    initFakeCourses();
  }

  private void initFakeCourses() {
    Random random = new Random();

    for (int courseIdentifier = 0; courseIdentifier < TOTAL_FAKE_COURSE_ITEMS; courseIdentifier++) {
      long beginDate = LocalDate.now().toEpochDay();
      long endDate = LocalDate.now().plusYears(1L).toEpochDay();

      CourseItemDomain item = new CourseItemDomain(
        courseId.getAndIncrement(),
        "Course name %d".formatted(courseIdentifier),
        LocalDate.ofEpochDay(random.nextLong(beginDate, endDate)),
        BigDecimal.valueOf(random.nextDouble(0, 100))
      );

      courseItems.add(item);
    }
  }

  public List<CourseItemDomain> getCourseItems() {
    return courseItems;
  }

  public boolean addCourse(CourseItemDomain item) {
    item.setId(courseId.getAndIncrement());
    return courseItems.add(item);
  }

  public void removeCourse(CourseItemDomain item) {
    courseItems.remove(item);
  }

  public Optional<CourseItemDomain> findById(Long id) {
    return courseItems.parallelStream()
      .filter(course -> id.equals(course.getId()))
      .findAny();
  }

  public Integer totalElementsCount() {
    return courseItems.size();
  }
}
