package krilovs.andrejs.app.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDomain extends BaseDomain {

  @Id
  @Column(name = "user_id", length = 20)
  String login;

  @Column(name = "user_email", length = 20)
  String email;

  @Column(name = "user_pwd")
  String password;

  @Column(name = "user_name", length = 20)
  String name;

  @Column(name = "user_surname", length = 20)
  String surname;

  @Column(name = "is_active")
  Boolean active;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Column(length = 10, name = "user_role")
  @CollectionTable(
    name = "user_roles_table",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  )
  Set<UserRole> role;

  @ManyToMany(mappedBy = "students")
  List<CourseDomain> courses;
}
