package krilovs.andrejs.app.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

@Entity
@Table(name = "course_table")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDomain extends BaseDomain {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
  @SequenceGenerator(name = "course_seq", sequenceName = "course_item_sequence", allocationSize = 1)
  @Column(name = "course_id")
  Long id;

  @Column(name = "course_currency", length = 3, nullable = false)
  Currency currency;

  @Column(name = "course_price", precision = 5, scale = 2, nullable = false)
  BigDecimal price;

  @Column(name = "course_start_date", nullable = false)
  LocalDate startDate;

  @Column(name = "course_label", length = 50, nullable = false)
  String label;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  Set<LessonDomain> lessons;

  @ManyToMany
  @JoinTable(
    name = "user_course_table",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}),
    joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "user_id"))
  Set<UserDomain> students;
}