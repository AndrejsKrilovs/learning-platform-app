package krilovs.andrejs.repo;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.domain.LessonItemDomain;

import java.util.stream.Stream;

@ApplicationScoped
public class LessonItemRepository implements PanacheRepository<LessonItemDomain> {
  private final static int LESSON_ITEMS_COUNT_PER_PAGE = 10;
  private int totalPageNumber;
  private int currentPage;
  private long totalElementsCount;

  public Stream<LessonItemDomain> getCourseLessons(Long courseId, Integer pageNumber) {
    currentPage = pageNumber;
    PanacheQuery<LessonItemDomain> queryResult =
      find("course.id = ?1", Sort.ascending("startsAt") ,courseId).page(pageNumber, LESSON_ITEMS_COUNT_PER_PAGE);

    totalPageNumber = queryResult.pageCount();
    totalElementsCount = queryResult.count();
    return queryResult.stream();
  }

  public int getTotalPageNumber() {
    return totalPageNumber;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public long getTotalElementsCount() {
    return totalElementsCount;
  }
}
