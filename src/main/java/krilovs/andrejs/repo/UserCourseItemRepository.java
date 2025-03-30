package krilovs.andrejs.repo;

import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.domain.CourseItemDomain;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class UserCourseItemRepository {
  private final List<CourseItemDomain> userCourseItems;

  public UserCourseItemRepository() {
    userCourseItems = new CopyOnWriteArrayList<>();
  }

  public void appendCourseToUser(CourseItemDomain item) {
    userCourseItems.add(item);
  }

  public List<CourseItemDomain> getUserCourseItems() {
    return userCourseItems;
  }

  public Optional<CourseItemDomain> findById(Long id) {
    return userCourseItems.parallelStream()
      .filter(course -> id.equals(course.getId()))
      .findAny();
  }
}
