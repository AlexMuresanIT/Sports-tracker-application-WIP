package health.tracker.api.exception;

public class NoUserFoundException extends RuntimeException {

  public NoUserFoundException(String message) {
    super(message);
  }
}
