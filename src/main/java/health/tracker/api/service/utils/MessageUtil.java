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

  public static final String DRINK_WATER_DAILY_REMINDER_MESSAGE =
      "Remember to drink water regularly for your health!";
  public static final String STAND_UP_DAILY_REMINDER_MESSAGE =
      "It's time to stand up and stretch your legs!";
  public static final String TAKE_A_WALK_DAILY_REMINDER_MESSAGE =
      "Take a short walk to refresh your mind and body!";
  public static final String MEDICATION_REMINDER_MESSAGE = "It's time to take your medication.";
}
