package krilovs.andrejs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.exception.CourseException;
import krilovs.andrejs.exception.IncorrectNumberException;
import krilovs.andrejs.exception.UserException;
import krilovs.andrejs.repo.CourseItemRepository;
import krilovs.andrejs.repo.UserRepository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Stream;

@ApplicationScoped
public class CourseItemService {
  private final static int MAXIMAL_COURSE_COUNT_PER_PERSON = 10;

  @Inject
  CourseItemRepository repository;

  @Inject
  UserRepository userRepository;

  public List<String> getMainFieldNames() {
    return Stream.of(CourseItemDomain.class.getDeclaredFields())
      .map(Field::getName)
      .filter(excludeFields())
      .toList();
  }

  public Map<String, Number> getAvailableCoursesRequestMetadata() {
    return Map.of(
      "currentPage", repository.getCurrentPageForAvailableCourses(),
      "totalPages", repository.getTotalPageNumberForAvailableCourses(),
      "totalElements", repository.getTotalElementsCountForAvailableCourses()
    );
  }

  public Map<String, Number> getUserCoursesRequestMetadata() {
    return Map.of(
      "currentPage", repository.getCurrentPageForUserCourses(),
      "totalPages", repository.getTotalPageNumberForUserCourses(),
      "totalElements", repository.getTotalElementsCountForUserCourses()
    );
  }

  public List<CourseItemDomain> getItems(String username, int pageNumber) {
    if (pageNumber < 0) {
      throw new IncorrectNumberException(
        "Incorrect page number",
        "CourseItemService.getItems",
        "Page number cannot be less than 0"
      );
    }

    return repository.getAvailableCourses(username, pageNumber);
  }

  public CourseItemDomain findItemById(long id) {
    return repository.findByIdOptional(id, LockModeType.OPTIMISTIC)
      .orElseThrow(() -> new CourseException(
        "Course not found",
        "CourseItemService.findItemById",
        "Course with current identifier %d not found".formatted(id)
      ));
  }

  public List<CourseItemDomain> getUserCourses(String username, Integer pageNumber) {
    return repository.getUserCourses(username, pageNumber);
  }

  @Transactional
  public CourseItemDomain addOrModifyItem(@Valid CourseItemDomain item) {
    if (item.getId() == null) {
      repository.persist(item);
    }
    else {
      StringJoiner updateQueryJoiner = new StringJoiner(", ", "", " where id = :id");
      Map<String, Object> queryParams = new HashMap<>();

      if (item.getLabel() != null) {
        updateQueryJoiner.add("label = :label");
        queryParams.put("label", item.getLabel());
      }
      if (item.getPrice() != null) {
        updateQueryJoiner.add("price = :price");
        queryParams.put("price", item.getPrice());
      }
      if (item.getStartDate() != null) {
        updateQueryJoiner.add("startDate = :startDate");
        queryParams.put("startDate", item.getStartDate());

      }
      queryParams.put("id", item.getId());
      repository.update(updateQueryJoiner.toString(), queryParams);
    }

    return item;
  }

  @Transactional
  public void removeItem(long itemId) {
    repository.deleteById(itemId);
  }

  @Transactional
  public CourseItemDomain takeCourseToUser(String username, Long courseId) {
    UserDomain userFromDatabase = userRepository.findByIdOptional(username, LockModeType.OPTIMISTIC)
      .orElseThrow(() -> new UserException(
        "CourseItemService.takeCourseToUser",
        "User '%s' not exists or do not logged yet".formatted(username)
      ));

    CourseItemDomain courseToTake = repository.findByIdOptional(courseId, LockModeType.OPTIMISTIC)
      .orElseThrow(() -> new CourseException(
        "Course not found",
        "CourseItemService.takeCourseToUser",
        "Course with id %d not exists".formatted(courseId)
      ));

    List<CourseItemDomain> userCourses = userFromDatabase.getUserCourses();
    if (userCourses.contains(courseToTake)) {
      throw new CourseException(
        "Take course exception",
        "CourseItemService.takeCourseToUser",
        "Course with id %d already applied for user '%s'".formatted(courseId, username)
      );
    }
    if (userCourses.size() > MAXIMAL_COURSE_COUNT_PER_PERSON) {
      throw new CourseException(
        "Taking course exception",
        "CourseItemService.takeCourse",
        "User cannot take more than %d courses".formatted(MAXIMAL_COURSE_COUNT_PER_PERSON)
      );
    }

    userCourses.add(courseToTake);
    return courseToTake;
  }

  private Predicate<String> excludeFields() {
    return field -> !field.contains("$") &&
      !field.equalsIgnoreCase("COURSE_ITEM_SEQUENCE") &&
      !field.equalsIgnoreCase("version");
  }
}
