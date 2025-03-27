package krilovs.andrejs.exception;

public abstract class BusinessException extends RuntimeException {
  private final String failedMethodName;
  private final String title;

  protected BusinessException(String title, String failedMethodName, String message) {
    super(message);
    this.failedMethodName = failedMethodName;
    this.title = title;
  }

  public String getFailedMethodName() {
    return failedMethodName;
  }

  public String getTitle() {
    return title;
  }
}
