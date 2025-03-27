package krilovs.andrejs.exception;

public class CourseException extends BusinessException {

  public CourseException(String title, String failedMethodName, String message) {
    super(title, failedMethodName, message);
  }
}
