package health.tracker.api.exception;

public class NoUserFoundException extends RuntimeException {

  public NoUserFoundException(final String message) {
    super(message);
  }
}
