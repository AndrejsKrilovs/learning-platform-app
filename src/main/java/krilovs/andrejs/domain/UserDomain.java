package krilovs.andrejs.domain;

import java.util.Objects;

public record UserDomain(
  String login,
  String password
) {
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof UserDomain that)) return false;
    return Objects.equals(login, that.login);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(login);
  }
}
