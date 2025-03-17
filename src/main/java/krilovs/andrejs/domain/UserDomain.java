package krilovs.andrejs.domain;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public record UserDomain(
  @NotBlank(message = "Username should be defined")
  String login,
  @NotBlank(message = "Password should be defined")
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
