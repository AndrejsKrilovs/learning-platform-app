package krilovs.andrejs.exception;

public class LoginException extends RuntimeException {
  private final String failedMethodName;

  public LoginException(String failedMethodName, String message) {
    super(message);
    this.failedMethodName = failedMethodName;
  }

  public String getFailedMethodName() {
    return failedMethodName;
  }
}
