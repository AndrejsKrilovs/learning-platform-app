package krilovs.andrejs.domain;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "lesson_table")
@EqualsAndHashCode(of = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonItemDomain {
  private final static String LESSON_ITEM_SEQUENCE = "lesson_item_sequence";

  @Id
  @Column(name = "lesson_id")
  @SequenceGenerator(name = LESSON_ITEM_SEQUENCE, sequenceName = LESSON_ITEM_SEQUENCE, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = LESSON_ITEM_SEQUENCE)
  Long id;

  @Column(name = "lesson_name", nullable = false, length = 50)
  @NotBlank(message = "Lesson name should be defined")
  @Size(max = 50, message = "Lesson name cannot be more than 50 characters")
  String name;

  @NotNull(message = "Lesson start time should be defined")
  @Column(name = "lesson_start_time", nullable = false)
  LocalDateTime startsAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_lecturer_id", referencedColumnName = "user_id")
  UserDomain lecturer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_course_id", referencedColumnName = "course_id")
  CourseItemDomain course;
}
