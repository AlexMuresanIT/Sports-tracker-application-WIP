package health.tracker.api.schedulers;

import static health.tracker.api.service.utils.MessageUtil.*;

import health.tracker.api.config.HealthTrackerConfig;
import health.tracker.api.service.UserService;
import health.tracker.api.service.WhatsappApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UserDailyRemindersScheduler {
  private static final Logger logger = LoggerFactory.getLogger(UserDailyRemindersScheduler.class);

  private final UserService userService;

  private final WhatsappApiService whatsappApiService;

  private final HealthTrackerConfig healthTrackerConfig;

  public UserDailyRemindersScheduler(
      final UserService userService,
      final WhatsappApiService whatsappApiService,
      final HealthTrackerConfig healthTrackerConfig) {
    this.userService = userService;
    this.whatsappApiService = whatsappApiService;
    this.healthTrackerConfig = healthTrackerConfig;
  }

  @Scheduled(
      fixedDelayString = "${health-tracker.daily-reminders.drink-water.interval}",
      initialDelayString = "${health-tracker.daily-reminders.drink-water.initial-delay}")
  public void sendDrinkWaterReminder() {
    logger.info("Sending drink water reminders to all users");
    sendReminder(DRINK_WATER_DAILY_REMINDER_MESSAGE);
  }

  @Scheduled(
      fixedDelayString = "${health-tracker.daily-reminders.stand-up.interval}",
      initialDelayString = "${health-tracker.daily-reminders.stand-up.initial-delay}")
  public void sendStandUpReminder() {
    logger.info("Sending stand up reminders to all users");
    sendReminder(STAND_UP_DAILY_REMINDER_MESSAGE);
  }

  @Scheduled(
      fixedDelayString = "${health-tracker.daily-reminders.walk-reminder.interval}",
      initialDelayString = "${health-tracker.daily-reminders.walk-reminder.initial-delay}")
  public void sendWalkReminder() {
    logger.info("Sending walk reminders to all users");
    sendReminder(TAKE_A_WALK_DAILY_REMINDER_MESSAGE);
  }

  @Scheduled(
      fixedDelayString = "${health-tracker.daily-reminders.medication-reminder.interval}",
      initialDelayString = "${health-tracker.daily-reminders.medication-reminder.initial-delay}")
  public void sendMedicationReminder() {
    logger.info("Sending medication reminders to all users");
    sendReminder(MEDICATION_REMINDER_MESSAGE);
  }

  private void sendReminder(final String message) {
    if (healthTrackerConfig.getDailyReminders().enabled()) {
      final var users = userService.getAllUsers();
      users.forEach(user -> whatsappApiService.sendWhatsappMessage(user.getPhoneNumber(), message));
    }
  }
}
