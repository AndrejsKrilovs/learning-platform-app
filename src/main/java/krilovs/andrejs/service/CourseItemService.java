package krilovs.andrejs.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.exception.IncorrectNumberException;
import krilovs.andrejs.repo.CourseItemRepository;
import krilovs.andrejs.repo.UserCourseItemRepository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Stream;

@ApplicationScoped
public class CourseItemService {

  @Inject
  CourseItemRepository repository;

  @Inject
  UserCourseItemRepository userCourseRepository;

  public List<CourseItemDomain> getItems(int pageNumber) {
    if (pageNumber < 0) {
      throw new IncorrectNumberException(
        "Incorrect page number",
        "CourseItemService.getItems",
        "Page number cannot be less than 0");
    }

    return repository.getAvailableCourses(pageNumber);
  }

  public Optional<CourseItemDomain> findItemById(long id) {
    return repository.findByIdOptional(id, LockModeType.OPTIMISTIC);
  }

  @Transactional
  public CourseItemDomain addOrModifyItem(@Valid CourseItemDomain item) {
    if (item.getId() == null) {
      repository.persist(item);
    }
    else {
      StringJoiner stringBuilder = new StringJoiner(", ", "", " where id = :id");
      Map<String, Object> queryParams = new HashMap<>();

      if (item.getLabel() != null) {
        stringBuilder.add("label = :label");
        queryParams.put("label", item.getLabel());
      }
      if (item.getPrice() != null) {
        stringBuilder.add("price = :price");
        queryParams.put("price", item.getPrice());
      }
      if (item.getStartDate() != null) {
        stringBuilder.add("startDate = :startDate");
        queryParams.put("startDate", item.getStartDate());

      }
      queryParams.put("id", item.getId());
      repository.update(stringBuilder.toString(), queryParams);
    }

    return item;
  }

  @Transactional
  public void removeItem(long itemId) {
    repository.deleteById(itemId);
  }

  public Map<String, Number> getRequestMetadata() {
    return Map.of(
      "currentPage", repository.getCurrentPage(),
      "totalPages", repository.getTotalPageNumber(),
      "totalElements", repository.count()
    );
  }

  public List<String> getMainFieldNames() {
    return Stream.of(CourseItemDomain.class.getDeclaredFields())
      .map(Field::getName)
      .filter(excludeFields())
      .toList();
  }

//  public Optional<CourseItemDomain> takeCourse(Long courseToTakeIdentifier) {
//    Optional<CourseItemDomain> courseToTake = repository.findByIdOptional(courseToTakeIdentifier, LockModeType.OPTIMISTIC);
//
//    courseToTake.ifPresent(course -> {
//      userCourseRepository.appendCourseToUser(course);
//      repository.delete(course);
//    });
//
//    return courseToTake;
//  }
//
//  public List<CourseItemDomain> getUserCourseItems() {
//    return userCourseRepository.getUserCourseItems();
//  }

  private Predicate<String> excludeFields() {
    return field -> !field.contains("$") &&
      !field.equalsIgnoreCase("COURSE_ITEM_SEQUENCE");
  }
}
