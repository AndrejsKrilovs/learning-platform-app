package krilovs.andrejs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.repo.CourseItemRepository;
import krilovs.andrejs.repo.UserCourseItemRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CourseItemService {
  @Inject
  CourseItemRepository repository;

  @Inject
  UserCourseItemRepository userCourseRepository;

  public List<CourseItemDomain> getCourseItems(Integer firstElementIndex, Integer lastElementIndex) {
    if (lastElementIndex >= totalElementCount()) {
      lastElementIndex = totalElementCount();
    }

    return repository.getCourseItems().subList(firstElementIndex, lastElementIndex);
  }

  public CourseItemDomain addCourseItem(CourseItemDomain itemToAdd) {
    return repository.addCourse(itemToAdd) ? itemToAdd : new CourseItemDomain(null, null, null, null);
  }

  public int totalElementCount() {
    return repository.totalElementsCount();
  }

  public Optional<CourseItemDomain> takeCourse(Long courseToTakeIdentifier) {
    Optional<CourseItemDomain> courseToTake = repository.findById(courseToTakeIdentifier);

    courseToTake.ifPresent(course -> {
      userCourseRepository.appendCourseToUser(course);
      repository.removeCourse(course);
    });

    return courseToTake;
  }

  public List<CourseItemDomain> getUserCourseItems() {
    return userCourseRepository.getUserCourseItems();
  }
}
