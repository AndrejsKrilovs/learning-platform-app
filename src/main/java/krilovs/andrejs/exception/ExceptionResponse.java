package krilovs.andrejs.exception;

public record ExceptionResponse (
  String methodName,
  String errorMessage
) {
}
