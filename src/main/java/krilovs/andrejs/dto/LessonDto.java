package krilovs.andrejs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record LessonDto(Long id,
                        String name,

                        @JsonFormat(pattern="yyyy-MM-dd HH:mm")
                        LocalDateTime startsAt,
                        String lecturer){
}
