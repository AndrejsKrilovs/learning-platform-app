package krilovs.andrejs.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import krilovs.andrejs.domain.CourseItemDomain;
import krilovs.andrejs.domain.LessonItemDomain;
import krilovs.andrejs.domain.UserDomain;
import krilovs.andrejs.exception.CourseException;
import krilovs.andrejs.repo.LessonItemRepository;
import krilovs.andrejs.request.LessonResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

@QuarkusTest
class LessonItemServiceTest {

  @InjectMock
  LessonItemRepository lessonItemRepository;

  @Inject
  LessonItemService lessonItemService;

  @Test
  void showLessonsForSelectedCourse() {
    Mockito.when(lessonItemRepository.getCourseLessons(Mockito.anyLong(), Mockito.anyInt()))
      .thenReturn(Stream.of(generateValidLesson()));

    List<LessonResponse> resultList = lessonItemService.showLessonsForSelectedCourse(Long.MAX_VALUE, Integer.MAX_VALUE);
    Assertions.assertFalse(resultList.isEmpty());
    Assertions.assertEquals(1L, resultList.getFirst().id());
    Assertions.assertEquals("Lesson name", resultList.getFirst().name());
    Assertions.assertEquals("Andrejs Krilovs", resultList.getFirst().lecturer());
  }

  @Test
  void showLessonsForIncorrectSelectedCourse() {
    Mockito.when(lessonItemRepository.getCourseLessons(Mockito.anyLong(), Mockito.anyInt()))
      .thenReturn(Stream.of(generateValidLesson()));

    CourseException incorrectCourse = Assertions.assertThrows(
      CourseException.class, () -> lessonItemService.showLessonsForSelectedCourse(Long.MIN_VALUE, Integer.MAX_VALUE)
    );
    Assertions.assertEquals("Cannot take lessons for course item with negative identifier", incorrectCourse.getMessage());
  }

  private LessonItemDomain generateValidLesson() {
    UserDomain lecturer = new UserDomain();
    lecturer.setName("Andrejs");
    lecturer.setSurname("Krilovs");

    CourseItemDomain course = new CourseItemDomain();
    course.setId(Long.MAX_VALUE);

    LessonItemDomain lesson = new LessonItemDomain();
    lesson.setId(1L);
    lesson.setName("Lesson name");
    lesson.setLecturer(lecturer);
    lesson.setCourse(course);
    return lesson;
  }
}