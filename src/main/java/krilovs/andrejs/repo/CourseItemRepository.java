package krilovs.andrejs.repo;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.domain.CourseItemDomain;

import java.util.List;

@ApplicationScoped
public class CourseItemRepository implements PanacheRepository<CourseItemDomain> {
  private static final int COURSE_ITEM_COUNT_PER_PAGE = 10;
  private static final String USER_COURSE_QUERY = """
      select
        c
      from
        UserDomain u
      join
        u.userCourses c
      where
        u.login = ?1
      order by
        c.startDate asc
    """;
  private static final String USER_AVAILABLE_COURSES_SQL = """
      from
        CourseItemDomain c
      where
        c not in (
          select
            u.userCourses
          from
            UserDomain u
          where
            u.login = ?1
        )
    """;

  private int totalPageNumberForAvailableCourses;
  private int currentPageForAvailableCourses;
  private long totalElementsCountForAvailableCourses;
  private int totalPageNumberForUserCourses;
  private int currentPageForUserCourses;
  private long totalElementsCountForUserCourses;

  public List<CourseItemDomain> getAvailableCourses(String username, int pageNumber) {
    Sort sortDirection = Sort.ascending("startDate");
    currentPageForAvailableCourses = pageNumber;

    PanacheQuery<CourseItemDomain> queryForResult =
      find(USER_AVAILABLE_COURSES_SQL, sortDirection, username).page(pageNumber, COURSE_ITEM_COUNT_PER_PAGE);

    totalPageNumberForAvailableCourses = queryForResult.pageCount();
    totalElementsCountForAvailableCourses = queryForResult.count();
    return queryForResult.list();
  }

  public List<CourseItemDomain> getUserCourses(String username, int pageNumber) {
    currentPageForUserCourses = pageNumber;
    PanacheQuery<CourseItemDomain> queryResult =
      find(USER_COURSE_QUERY, username).page(pageNumber, COURSE_ITEM_COUNT_PER_PAGE);

    totalPageNumberForUserCourses = queryResult.pageCount();
    totalElementsCountForUserCourses = queryResult.count();
    return queryResult.list();
  }

  public int getTotalPageNumberForAvailableCourses() {
    return totalPageNumberForAvailableCourses;
  }

  public int getCurrentPageForAvailableCourses() {
    return currentPageForAvailableCourses;
  }

  public long getTotalElementsCountForAvailableCourses() {
    return totalElementsCountForAvailableCourses;
  }

  public int getTotalPageNumberForUserCourses() {
    return totalPageNumberForUserCourses;
  }

  public int getCurrentPageForUserCourses() {
    return currentPageForUserCourses;
  }

  public long getTotalElementsCountForUserCourses() {
    return totalElementsCountForUserCourses;
  }
}