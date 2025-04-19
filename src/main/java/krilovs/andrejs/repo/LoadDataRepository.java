package krilovs.andrejs.repo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import krilovs.andrejs.domain.CourseItemDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@ApplicationScoped
public class LoadDataRepository {
  private static final int TOTAL_FAKE_COURSE_ITEMS = 18;

  @Inject
  CourseItemRepository courseItemRepository;

  @Transactional
  public void initFakeCourses() {
    courseItemRepository.deleteAll();
    Random random = new Random();

    for (int courseIdentifier = 0; courseIdentifier < TOTAL_FAKE_COURSE_ITEMS; courseIdentifier++) {
      long beginDate = LocalDate.now().toEpochDay();
      long endDate = LocalDate.now().plusYears(1L).toEpochDay();

      CourseItemDomain item = new CourseItemDomain();
      item.setLabel("Course name %d".formatted(courseIdentifier));
      item.setStartDate(LocalDate.ofEpochDay(random.nextLong(beginDate, endDate)));
      item.setPrice(BigDecimal.valueOf(random.nextDouble(0, 100)));
      courseItemRepository.persist(item);
    }
  }
}
