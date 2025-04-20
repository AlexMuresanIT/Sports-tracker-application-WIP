package health.tracker.api.service.utils;

public final class MessageUtil {

  private MessageUtil() {
    // do not instantiate me
  }

  public static final String WELCOME_MESSAGE =
      "Hello %s ! Welcome to the Sports Tracker application";
  public static final String UPDATE_AGE_MESSAGE =
      "Hello %s ! You successfully updated your age to %s. You are getting older !";
  public static final String UPDATE_PASSWORD_MESSAGE = "Your password has been updated!";
  public static final String UPDATE_EMAIL_MESSAGE = "Your email has been updated!";
}
