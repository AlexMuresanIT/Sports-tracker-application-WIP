package health.tracker.api.service.initializer;

import health.tracker.api.domain.AwardType;
import health.tracker.api.domain.Entity.Award;
import health.tracker.api.service.AwardService;
import health.tracker.api.service.UserService;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AwardInitializer implements CommandLineRunner {
  private final Logger log = LoggerFactory.getLogger(AwardInitializer.class);

  private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;
  private static final List<Award> ALL_AWARDS = new ArrayList<>();

  private final AwardService awardService;
  private final UserService userService;

  static {
    ALL_AWARDS.add(
        new Award(
            null,
            "February Challenge",
            "You earned this award by reaching your daily move Goal 11 times in a month",
            "icon",
            AwardType.MONTHLY_CHALLENGES,
            OffsetDateTime.of(2025, 2, 25, 12, 44, 33, 0, ZONE_OFFSET).toString()));
    ALL_AWARDS.add(
        new Award(
            null,
            "Yoga Day Challenge",
            "Do a yoga workout of at least 10 minutes",
            "icon",
            AwardType.WORKOUTS,
            OffsetDateTime.of(2025, 1, 21, 12, 44, 33, 0, ZONE_OFFSET).toString()));
    ALL_AWARDS.add(
        new Award(
            null,
            "Move Goal 200%",
            "Double your daily Move goal",
            "icon",
            AwardType.CLOSE_YOUR_RING,
            OffsetDateTime.of(2024, 1, 16, 12, 44, 33, 0, ZONE_OFFSET).toString()));
    ALL_AWARDS.add(
        new Award(
            null,
            "Move Goal 300%",
            "Triple your daily Move goal",
            "icon",
            AwardType.CLOSE_YOUR_RING,
            OffsetDateTime.of(2024, 7, 12, 12, 44, 33, 0, ZONE_OFFSET).toString()));
    ALL_AWARDS.add(
        new Award(
            null,
            "Move Goal 300%",
            "Triple your daily Move goal",
            "icon",
            AwardType.CLOSE_YOUR_RING,
            OffsetDateTime.of(2024, 7, 12, 12, 44, 33, 0, ZONE_OFFSET).toString()));
    ALL_AWARDS.add(
        new Award(
            null,
            "Move Goal 400%",
            "Quadruple your daily Move goal",
            "icon",
            AwardType.CLOSE_YOUR_RING,
            OffsetDateTime.of(2024, 6, 18, 12, 44, 33, 0, ZONE_OFFSET).toString()));
    ALL_AWARDS.add(
        new Award(
            null,
            "New Move Record",
            "Set a new personal best of most calories burned in a day",
            "icon",
            AwardType.CLOSE_YOUR_RING,
            OffsetDateTime.of(2024, 6, 20, 12, 44, 33, 0, ZONE_OFFSET).toString()));
    ALL_AWARDS.add(
        new Award(
            null,
            "Fastest 5K",
            "You earned this award with a new fastest 5k time of 00:31:12 on 31/11/2024",
            "icon",
            AwardType.WORKOUTS,
            OffsetDateTime.of(2024, 11, 29, 12, 44, 33, 0, ZONE_OFFSET).toString()));
    ALL_AWARDS.add(
        new Award(
            null,
            "Walking Workout Record",
            "You earned this award for the most kilocalories burned on a walk. 282 kilocalories burned on 24/11/2024",
            "icon",
            AwardType.WORKOUTS,
            OffsetDateTime.of(2024, 11, 29, 12, 44, 33, 0, ZONE_OFFSET).toString()));
  }

  public AwardInitializer(final AwardService awardService, final UserService userService) {
    this.awardService = awardService;
    this.userService = userService;
  }

  @Override
  public void run(final String... args) {
    try {
      log.info("Application started and saving awards.");
      final var awards = awardService.findAllAwards();
      if (awards.isEmpty()) {
        awardService.saveAwards(ALL_AWARDS);
        final var allUsers = userService.getAllUsers();
        final var updatedUsers = userService.updateUsersAwardsList(allUsers, ALL_AWARDS);
        userService.saveUsers(updatedUsers);
      }
    } catch (final Exception e) {
      log.error("There was an error saving award {}", e.getMessage());
    }
  }
}
