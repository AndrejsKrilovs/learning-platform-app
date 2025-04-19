package krilovs.andrejs.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import krilovs.andrejs.domain.UserDomain;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserDomain, String> {
  public List<UserDomain> getUserList() {
    return findAll().list();
  }

  public Optional<UserDomain> findByLoginAndPassword(String login, String password) {
    return find("login = ?1 and password = ?2", login, password).firstResultOptional();
  }
}