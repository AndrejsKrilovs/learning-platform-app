package krilovs.andrejs.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record LessonResponse (Long id,
                              String name,
                              @JsonFormat(pattern="yyyy-MM-dd HH:mm")
                              LocalDateTime startsAt,
                              String lecturer){
}
