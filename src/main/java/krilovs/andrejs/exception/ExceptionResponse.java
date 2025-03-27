package krilovs.andrejs.exception;

import java.util.List;
import java.util.Map;

public record ExceptionResponse (
  String title,
  Integer status,
  List<Map<String, String>> violations
) {
}
