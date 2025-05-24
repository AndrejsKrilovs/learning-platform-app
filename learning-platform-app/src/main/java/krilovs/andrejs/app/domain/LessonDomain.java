package krilovs.andrejs.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_table")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonDomain extends BaseDomain {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_seq")
  @SequenceGenerator(name = "lesson_seq", sequenceName = "lesson_item_sequence", allocationSize = 1)
  @Column(name = "lesson_id")
  Long id;

  @Column(name = "lesson_start_time", nullable = false)
  LocalDateTime startTime;

  @Column(name = "lesson_name", length = 50, nullable = false)
  String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", referencedColumnName = "course_id")
  CourseDomain course;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lecturer_id", referencedColumnName = "user_id")
  UserDomain lecturer;
}