package krilovs.andrejs.exception;

public class UserException extends BusinessException {

  public UserException(String failedMethodName, String message) {
    super("User exception", failedMethodName, message);
  }
}
