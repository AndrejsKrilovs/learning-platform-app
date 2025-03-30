package krilovs.andrejs.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LessonDTO (
  @NotBlank(message = "Lesson name should be defined")
  String name,
  @NotNull(message = "Lesson date should be defined")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm")
  LocalDateTime startsAt,
  String lecturerName,
  Long courseId
){
}
