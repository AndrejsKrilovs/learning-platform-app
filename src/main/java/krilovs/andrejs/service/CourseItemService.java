package krilovs.andrejs.service;

import jakarta.inject.Singleton;
import krilovs.andrejs.domain.CourseItemDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class CourseItemService {
  /**
   * Remove this constant after go live
   */
  public static final int TOTAL_FAKE_COURSE_ITEMS = 18;
  private final List<CourseItemDomain> courseItems;
  private final List<CourseItemDomain> userCourseItems;
  private final AtomicLong courseId;

  public CourseItemService() {
    courseId = new AtomicLong();
    courseItems = new CopyOnWriteArrayList<>();
    userCourseItems = new CopyOnWriteArrayList<>();
    initFakeCourses();
  }

  public List<CourseItemDomain> getCourseItems(Integer firstElementIndex, Integer lastElementIndex) {
    if (lastElementIndex >= courseItems.size()) {
      lastElementIndex = courseItems.size();
    }

    return courseItems.subList(firstElementIndex, lastElementIndex);
  }

  public CourseItemDomain addCourseItem(CourseItemDomain itemToAdd) {
    itemToAdd.setId(courseId.getAndIncrement());
    courseItems.add(itemToAdd);
    return itemToAdd;
  }

  public int totalElementCount() {
    return courseItems.size();
  }

  public Optional<CourseItemDomain> takeCourse(Long courseToTakeIdentifier) {
    Optional<CourseItemDomain> courseToTake = courseItems.parallelStream()
      .filter(course -> courseToTakeIdentifier.equals(course.getId()))
      .findAny();

    courseToTake.ifPresent(course -> {
      userCourseItems.add(course);
      courseItems.remove(course);
    });

    return courseToTake;
  }

  public List<CourseItemDomain> getUserCourseItems() {
    return userCourseItems;
  }

  /**
   * Remove this method after go live
   */
  private void initFakeCourses() {
    Random random = new Random();

    for (int i = 0; i < TOTAL_FAKE_COURSE_ITEMS; i++) {
      long beginDate = LocalDate.now().toEpochDay();
      long endDate = LocalDate.now().plusYears(1L).toEpochDay();

      CourseItemDomain item = new CourseItemDomain(
        courseId.getAndIncrement(),
        "Course name %d".formatted(i),
        LocalDate.ofEpochDay(random.nextLong(beginDate, endDate)),
        BigDecimal.valueOf(random.nextDouble(0, 100))
      );

      courseItems.add(item);
    }
  }
}
