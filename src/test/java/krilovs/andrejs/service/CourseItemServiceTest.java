package krilovs.andrejs.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.exception.CourseException;
import krilovs.andrejs.exception.UserException;
import krilovs.andrejs.repo.CourseItemRepository;
import krilovs.andrejs.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@QuarkusTest
class CourseItemServiceTest {
  @InjectMock
  CourseItemRepository repository;

  @InjectMock
  UserRepository userRepository;

  @Inject
  CourseItemService service;

  @Test
  void getItems() {
    Mockito.when(repository.getAvailableCourses(Mockito.anyInt()))
      .thenReturn(List.of(generateValidCourseItem()));

    List<CourseItemDomain> resultList = service.getItems(Integer.MAX_VALUE);
    Assertions.assertNotNull(resultList);
    Assertions.assertFalse(resultList.isEmpty());
  }

  @Test
  void getItemById() {
    Mockito.when(repository.findByIdOptional(Mockito.anyLong(), Mockito.any()))
      .thenReturn(Optional.of(generateValidCourseItem()));

    CourseItemDomain result = service.findItemById(Long.MAX_VALUE);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1L, result.getId());
  }

  @Test
  void addCourseSuccess() {
    CourseItemDomain itemToInsert = new CourseItemDomain();
    itemToInsert.setLabel("Test success label");
    itemToInsert.setStartDate(LocalDate.now());
    itemToInsert.setPrice(BigDecimal.ZERO);

    CourseItemDomain result = service.addOrModifyItem(itemToInsert);
    Assertions.assertNotNull(result.getCurrency());
    Mockito.verify(repository, Mockito.atMostOnce()).persist(itemToInsert);
  }

  @Test
  void addCourseFailed() {
    Assertions.assertThrows(ConstraintViolationException.class, () -> service.addOrModifyItem(new CourseItemDomain()));
    Mockito.verify(repository, Mockito.never()).persist(new CourseItemDomain());
  }

  @Test
  void updateCourseSuccess() {
    CourseItemDomain itemToModify = new CourseItemDomain();
    itemToModify.setId(1L);
    itemToModify.setLabel("Test to modify label");
    itemToModify.setPrice(BigDecimal.ZERO.setScale(2, RoundingMode.UP));

    CourseItemDomain result = service.addOrModifyItem(itemToModify);
    Assertions.assertEquals(1L, result.getId());
    Assertions.assertNotNull(result.getCurrency());

    Map<String, Object> parameterMap = Map.of(
      "price", BigDecimal.ZERO.setScale(2, RoundingMode.UP),
      "label", "Test to modify label",
      "id", 1L
    );

    Mockito.verify(repository, Mockito.atMostOnce())
      .update("label = :label, price = :price where id = :id", parameterMap);
  }

  @Test
  void updateCourseFailed() {
    Assertions.assertThrows(RuntimeException.class, () -> service.addOrModifyItem(new CourseItemDomain()));
    Mockito.verify(repository, Mockito.never()).update("", Map.of());
  }

  @Test
  void removeItem() {
    service.removeItem(Mockito.anyLong());
    Mockito.verify(repository, Mockito.atMostOnce()).deleteById(Mockito.anyLong());
  }

  @Test
  void takeCourseForUserSuccess() {
    Mockito.when(userRepository.findByIdOptional(Mockito.anyString(), Mockito.any()))
      .thenReturn(Optional.of(generateValidUser()));
    Mockito.when(repository.findByIdOptional(Mockito.anyLong(), Mockito.any()))
      .thenReturn(Optional.of(generateValidCourseItem()));

    CourseItemDomain result = service.takeCourseToUser(Mockito.anyString(), Mockito.anyLong());
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1L, result.getId());
  }

  @Test
  void takeCourseOneMoreTime() {
    Mockito.when(userRepository.findByIdOptional(Mockito.anyString(), Mockito.any()))
      .thenReturn(Optional.of(generateUserWithExistingCourse()));
    Mockito.when(repository.findByIdOptional(Mockito.anyLong(), Mockito.any()))
      .thenReturn(Optional.of(generateValidCourseItem()));

    CourseException duplicateResult = Assertions.assertThrows(
      CourseException.class, () -> service.takeCourseToUser("username", 1L)
    );
    Assertions.assertEquals(
      "Course with id 1 already applied for user 'username'",
      duplicateResult.getMessage()
    );
  }

  @Test
  void takeCourseForNotExistingUser() {
    Mockito.when(userRepository.findByIdOptional(Mockito.anyString(), Mockito.any()))
      .thenReturn(Optional.of(generateValidUser()));

    UserException notExistingUser = Assertions.assertThrows(
      UserException.class, () -> service.takeCourseToUser("fake_user", Mockito.anyLong())
    );
    Assertions.assertEquals("User 'fake_user' not exists or do not logged yet", notExistingUser.getMessage());
  }

  @Test
  void takeNotExistingCourse() {
    Mockito.when(userRepository.findByIdOptional(Mockito.anyString(), Mockito.any()))
      .thenReturn(Optional.of(generateValidUser()));
    Mockito.when(repository.findByIdOptional(Mockito.anyLong(), Mockito.any()))
      .thenReturn(Optional.of(generateValidCourseItem()));

    CourseException notExistingCourse = Assertions.assertThrows(
      CourseException.class, () -> service.takeCourseToUser(Mockito.anyString(), 100L)
    );
    Assertions.assertEquals("Course with id 100 not exists", notExistingCourse.getMessage());
  }

  private CourseItemDomain generateValidCourseItem() {
    CourseItemDomain courseItem = new CourseItemDomain();
    courseItem.setId(1L);
    courseItem.setLabel("Valid course label");
    courseItem.setPrice(BigDecimal.ONE);
    courseItem.setStartDate(LocalDate.now());
    return courseItem;
  }

  private UserDomain generateValidUser() {
    UserDomain validUser = new UserDomain();
    validUser.setLogin("username");
    validUser.setUserCourses(new ArrayList<>());
    return validUser;
  }

  private UserDomain generateUserWithExistingCourse() {
    ArrayList<CourseItemDomain> userCourses = new ArrayList<>();
    userCourses.add(generateValidCourseItem());

    UserDomain validUser = new UserDomain();
    validUser.setLogin("username");
    validUser.setUserCourses(userCourses);
    return validUser;
  }
}