package krilovs.andrejs.app.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.app.domain.UserDomain;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserDomain> {

  private final static Integer USER_RECORDS_PER_PAGE = 10;

  public Optional<UserDomain> findByLogin(String login) {
    return find("login", login).firstResultOptional();
  }

  public Optional<UserDomain> findByLoginAndPassword(String login, String password) {
    return find("login = ?1 and password = ?2", login, password).firstResultOptional();
  }

  public List<UserDomain> findActiveUsers(int pageNumber) {
    return find("active", Sort.descending("updatedAt"), Boolean.TRUE)
      .page(pageNumber, USER_RECORDS_PER_PAGE)
      .list();
  }
}
