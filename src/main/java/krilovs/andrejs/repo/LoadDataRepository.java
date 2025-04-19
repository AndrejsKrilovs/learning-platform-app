package krilovs.andrejs.repo;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.UserDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@ApplicationScoped
public class LoadDataRepository {
  private static final int TOTAL_FAKE_COURSE_ITEMS = 18;

  @Inject
  CourseItemRepository courseItemRepository;

  @Inject
  UserRepository userRepository;

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

  @Transactional
  public void initFakeUsers() {
    UserDomain adminUser = new UserDomain();
    adminUser.setLogin("admin");
    adminUser.setPassword(BcryptUtil.bcryptHash("admin"));
    adminUser.setName("Name");
    adminUser.setSurname("Surname");
    adminUser.setRole("admin");
    userRepository.persist(adminUser);

    UserDomain simpleUser = new UserDomain();
    simpleUser.setLogin("username");
    simpleUser.setPassword(BcryptUtil.bcryptHash("password"));
    simpleUser.setName("Name");
    simpleUser.setSurname("Surname");
    simpleUser.setRole("student");
    userRepository.persist(simpleUser);

    UserDomain lecturer = new UserDomain();
    lecturer.setLogin("lecturer");
    lecturer.setPassword(BcryptUtil.bcryptHash("main"));
    lecturer.setName("Name");
    lecturer.setSurname("Surname");
    lecturer.setRole("lecturer");
    userRepository.persist(lecturer);
  }
}
