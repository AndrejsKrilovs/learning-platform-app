package krilovs.andrejs.app.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.app.domain.CourseDomain;

import java.util.List;

@ApplicationScoped
public class CourseRepository implements PanacheRepository<CourseDomain> {

  private final static Integer COURSE_RECORDS_PER_PAGE = 10;

  public List<CourseDomain> findCoursesByStudentLogin(String login, int pageNumber) {
    return find("SELECT c FROM CourseDomain c JOIN c.students s WHERE s.login = ?1 ORDER BY c.startDate ASC", login)
      .page(pageNumber, COURSE_RECORDS_PER_PAGE)
      .list();
  }
}
