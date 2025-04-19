package krilovs.andrejs.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.repo.CourseItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@QuarkusTest
class CourseItemServiceTest {
  @InjectMock
  CourseItemRepository repository;

  @Inject
  CourseItemService service;

  @Test
  void getItems() {
    Mockito.when(repository.getAvailableCourses(Mockito.anyInt()))
      .thenReturn(List.of(new CourseItemDomain()));

    List<CourseItemDomain> resultList = service.getItems(Integer.MAX_VALUE);
    Assertions.assertNotNull(resultList);
    Assertions.assertFalse(resultList.isEmpty());
  }

  @Test
  void getItemById() {
    Mockito.when(repository.findByIdOptional(Mockito.anyLong(), Mockito.any()))
      .thenReturn(Optional.of(new CourseItemDomain()));

    Optional<CourseItemDomain> result = service.findItemById(Long.MAX_VALUE);
    Assertions.assertNotNull(result);
    Assertions.assertTrue(result.isPresent());
  }

  @Test
  void addCourseSuccess() {
    CourseItemDomain itemToInsert = new CourseItemDomain();
    itemToInsert.setLabel("Test success label");
    itemToInsert.setStartDate(LocalDate.now());
    itemToInsert.setPrice(BigDecimal.ZERO);

    service.addOrModifyItem(itemToInsert);
    Mockito.verify(repository,Mockito.times(1)).persist(itemToInsert);
  }

  @Test
  void addCourseFailed() {
    Assertions.assertThrows(RuntimeException.class, () -> service.addOrModifyItem(new CourseItemDomain()));
    Mockito.verify(repository, Mockito.times(0)).persist(new CourseItemDomain());
  }

  @Test
  void updateCourseSuccess() {
    CourseItemDomain itemToInsert = new CourseItemDomain();
    itemToInsert.setId(1L);
    itemToInsert.setLabel("Test to modify label");
    itemToInsert.setPrice(BigDecimal.ZERO);

    service.addOrModifyItem(itemToInsert);

    Map<String, Object> parameterMap = Map.of(
      "price", BigDecimal.ZERO.setScale(2, RoundingMode.UP),
      "label", "Test to modify label",
      "id", 1L);

    Mockito.verify(repository, Mockito.times(1))
      .update("label = :label, price = :price where id = :id", parameterMap);
  }

  @Test
  void updateCourseFailed() {
    Assertions.assertThrows(RuntimeException.class, () -> service.addOrModifyItem(new CourseItemDomain()));
    Mockito.verify(repository, Mockito.times(0)).update("", Map.of());
  }

  @Test
  void removeItem() {
    service.removeItem(Mockito.anyLong());
    Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.anyLong());
  }
}