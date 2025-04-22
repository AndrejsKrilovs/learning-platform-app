package krilovs.andrejs.repo;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.LessonItemDomain;
import krilovs.andrejs.domain.UserDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Random;

@ApplicationScoped
public class LoadDataRepository {
  private static final int TOTAL_FAKE_COURSE_ITEMS = 18;
  private static final int TOTAL_FAKE_LESSONS_PER_COURSE = 20;

  @Inject
  LessonItemRepository lessonItemRepository;

  @Inject
  CourseItemRepository courseItemRepository;

  @Inject
  UserRepository userRepository;
  Random random = new Random();

  @Transactional
  public void initFakeCourses() {
    courseItemRepository.deleteAll();
    courseItemRepository.deleteAll();

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
    userRepository.deleteAll();
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

  @Transactional
  public void initFakeLessons() {
    lessonItemRepository.deleteAll();
    for (CourseItemDomain course: courseItemRepository.findAll().list()) {
      for (long lessonIdentifier = 0; lessonIdentifier < TOTAL_FAKE_LESSONS_PER_COURSE; lessonIdentifier++) {
        long beginDate = course.getStartDate().toEpochSecond(LocalTime.of(0,0), ZoneOffset.ofHours(2));
        long endDate = LocalDateTime.now().plusYears(1L).toEpochSecond(ZoneOffset.ofHours(2));
        LocalDateTime generatedDate = LocalDateTime.ofEpochSecond(
          random.nextLong(beginDate, endDate), 0, ZoneOffset.ofHours(2)
        );

        LessonItemDomain lesson = new LessonItemDomain();
        lesson.setName("Lesson %d for %s".formatted(lessonIdentifier, course.getLabel()));
        lesson.setCourse(course);
        lesson.setStartsAt(generatedDate);
        lesson.setLecturer(userRepository.findById("lecturer", LockModeType.OPTIMISTIC));
        lessonItemRepository.persist(lesson);
      }
    }
  }
}
