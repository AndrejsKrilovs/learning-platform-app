package krilovs.andrejs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LessonItemDomain {
  private Long id;
  private String name;
  @JsonIgnore
  private Long courseId;

  public LessonItemDomain(Long id, String name, Long courseId) {
    this.id = id;
    this.name = name;
    this.courseId = courseId;
  }

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

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }
}
