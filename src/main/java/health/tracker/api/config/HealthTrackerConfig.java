package health.tracker.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "health-tracker")
public class HealthTrackerConfig {
  private DailyReminders dailyReminders;

  private Kafka kafka;

  public Kafka getKafka() {
    return kafka;
  }

  public void setKafka(final Kafka kafka) {
    this.kafka = kafka;
  }

  public DailyReminders getDailyReminders() {
    return dailyReminders;
  }

  public void setDailyReminders(final DailyReminders dailyReminders) {
    this.dailyReminders = dailyReminders;
  }

  public record Kafka(boolean enabled) {}

  public record DailyReminders(
      boolean enabled,
      DrinkWater drinkWater,
      StandUp standUp,
      WalkReminder walkReminder,
      MedicationReminder medicationReminder) {}

  public record DrinkWater(String initialDelay, String interval) {}

  public record StandUp(String initialDelay, String interval) {}

  public record WalkReminder(String initialDelay, String interval) {}

  public record MedicationReminder(String initialDelay, String interval) {}
}
