package health.tracker.api.exception;

public class InvalidData extends RuntimeException {
  public InvalidData(String message) {
    super(message);
  }
}
