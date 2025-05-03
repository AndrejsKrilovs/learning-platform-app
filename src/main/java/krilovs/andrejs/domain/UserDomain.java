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
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_table")
@EqualsAndHashCode(of = {"login"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDomain {
  @Id
  @Column(name = "user_id", length = 20)
  @NotBlank(message = "Username should be defined")
  @Size(max = 20, message = "Maximal length for username is 20 characters")
  String login;

  /**
   * Hide this property when crypto functionality will be implemented in frontend
   */
  @Column(name = "user_pwd")
  @NotBlank(message = "Password should be defined")
  String password;

  @Column(name = "user_name", length = 20)
  @NotBlank(message = "Name should be defined")
  @Size(max = 20, message = "Maximal length for name is 20 characters")
  String name;

  @Column(name = "user_surname", length = 20)
  @NotBlank(message = "Surname should be defined")
  @Size(max = 20, message = "Maximal length for surname is 20 characters")
  String surname;

  @Column(name = "user_role", length = 20)
  @NotBlank(message = "Role should be defined")
  String role;

  @Version
  @JsonIgnore
  int version;

  @Column(name = "registered_at", nullable = false)
  final LocalDateTime registeredDate = LocalDateTime.now();

  @Column(name = "last_visit_at")
  LocalDateTime lastVisitedDate;

  @JsonIgnore
  @ElementCollection
  @JoinTable(
    name = "user_course_table",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}),
    joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id")
  )
  List<CourseItemDomain> userCourses;
}
