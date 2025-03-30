package krilovs.andrejs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class LessonItemDomain {
  private Long id;
  private String name;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm")
  private LocalDateTime startsAt;
  private String lecturer;
  @JsonIgnore
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

  public String getLecturer() {
    return lecturer;
  }

  public void setLecturer(String lecturer) {
    this.lecturer = lecturer;
  }

  public CourseItemDomain getCourse() {
    return course;
  }

  public void setCourse(CourseItemDomain course) {
    this.course = course;
  }
}
