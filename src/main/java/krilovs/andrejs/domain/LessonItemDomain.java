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

import java.time.LocalDateTime;

@Entity
@Table(name = "lesson_table")
public class LessonItemDomain {
  private final static String LESSON_ITEM_SEQUENCE = "lesson_item_sequence";

  @Id
  @Column(name = "lesson_id")
  @SequenceGenerator(name = LESSON_ITEM_SEQUENCE, sequenceName = LESSON_ITEM_SEQUENCE, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = LESSON_ITEM_SEQUENCE)
  private Long id;

  @Column(name = "lesson_name", nullable = false, length = 50)
  @NotBlank(message = "Lesson name should be defined")
  @Size(max = 50, message = "Lesson name cannot be more than 50 characters")
  private String name;

  @NotNull(message = "Lesson start time should be defined")
  @Column(name = "lesson_start_time", nullable = false)
  private LocalDateTime startsAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_lecturer_id", referencedColumnName = "user_id")
  private UserDomain lecturer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lesson_course_id", referencedColumnName = "course_id")
  private CourseItemDomain course;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getStartsAt() {
    return startsAt;
  }

  public void setStartsAt(LocalDateTime startsAt) {
    this.startsAt = startsAt;
  }

  public UserDomain getLecturer() {
    return lecturer;
  }

  public void setLecturer(UserDomain lecturer) {
    this.lecturer = lecturer;
  }

  public CourseItemDomain getCourse() {
    return course;
  }

  public void setCourse(CourseItemDomain course) {
    this.course = course;
  }
}
