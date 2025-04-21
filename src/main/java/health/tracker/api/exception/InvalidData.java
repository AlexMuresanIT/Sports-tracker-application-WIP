package health.tracker.api.exception;

public class InvalidData extends RuntimeException {
  public InvalidData(final String message) {
    super(message);
  }
}
