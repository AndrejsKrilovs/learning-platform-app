package krilovs.andrejs.exception;

public class LoggingException extends BusinessException {

  public LoggingException(String failedMethodName, String message) {
    super("Logging exception", failedMethodName, message);
  }
}
