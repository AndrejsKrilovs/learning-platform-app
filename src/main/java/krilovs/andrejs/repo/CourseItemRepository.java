package krilovs.andrejs.repo;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.domain.CourseItemDomain;

import java.util.List;

@ApplicationScoped
public class CourseItemRepository implements PanacheRepository<CourseItemDomain> {
  private final static int COURSE_ITEM_COUNT_PER_PAGE = 10;
  private int totalPageNumber;
  private int currentPage;

  public List<CourseItemDomain> getAvailableCourses(int pageNumber) {
    currentPage = pageNumber;
    PanacheQuery<CourseItemDomain> queryForResult =
      findAll(Sort.ascending("startDate")).page(pageNumber, COURSE_ITEM_COUNT_PER_PAGE);

    totalPageNumber = queryForResult.pageCount();
    return queryForResult.list();
  }

  public int getTotalPageNumber() {
    return totalPageNumber;
  }

  public int getCurrentPage() {
    return currentPage;
  }
}