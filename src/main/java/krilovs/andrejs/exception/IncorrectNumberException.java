package krilovs.andrejs.exception;

public class IncorrectNumberException extends BusinessException {

  public IncorrectNumberException(String title, String failedMethodName, String message) {
    super(title, failedMethodName, message);
  }
}
