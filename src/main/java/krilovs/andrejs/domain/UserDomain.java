package krilovs.andrejs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_table")
public class UserDomain {
  @Id
  @Column(name = "user_id", length = 20)
  @NotBlank(message = "Username should be defined")
  @Size(max = 20, message = "Maximal length for username is 20 characters")
  private String login;

  @JsonIgnore
  @Column(name = "user_pwd")
  @NotBlank(message = "Password should be defined")
  private String password;

  @Column(name = "user_name", length = 20)
  @NotBlank(message = "Name should be defined")
  @Size(max = 20, message = "Maximal length for name is 20 characters")
  private String name;

  @Column(name = "user_surname", length = 20)
  @NotBlank(message = "Surname should be defined")
  @Size(max = 20, message = "Maximal length for surname is 20 characters")
  private String surname;

  @Column(name = "user_role", length = 20)
  @NotBlank(message = "Role should be defined")
  private String role;

  @Version
  @JsonIgnore
  private int version;

  @Column(name = "registered_at", nullable = false)
  private final LocalDateTime registeredDate = LocalDateTime.now();

  @Column(name = "last_visit_at")
  private LocalDateTime lastVisitedDate;

  @JsonIgnore
  @ElementCollection
  @JoinTable(
    name = "user_course_table",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}),
    joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id")
  )
  private List<CourseItemDomain> userCourses;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public LocalDateTime getRegisteredDate() {
    return registeredDate;
  }

  public LocalDateTime getLastVisitedDate() {
    return lastVisitedDate;
  }

  public void setLastVisitedDate(LocalDateTime lastVisitedDate) {
    this.lastVisitedDate = lastVisitedDate;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public List<CourseItemDomain> getUserCourses() {
    return userCourses;
  }

  public void setUserCourses(List<CourseItemDomain> userCourses) {
    this.userCourses = userCourses;
  }

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
