package health.tracker.api.schedulers;

import health.tracker.api.service.OutdoorRunningService;
import health.tracker.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(value = "scheduling.enable", havingValue = "true")
public class RecordsSchedulers {

    private static final Logger logger = LoggerFactory.getLogger(RecordsSchedulers.class);

    private final OutdoorRunningService outdoorRunningService;
    private final UserService userService;

    public RecordsSchedulers(final OutdoorRunningService outdoorRunningService, final UserService userService) {
        this.outdoorRunningService = outdoorRunningService;
        this.userService = userService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void outputBestRecordOfAUser() {
        final var userEmail = getRandomEmail();
        final var record = outdoorRunningService.bestRecordForTheUser(userEmail);
        if(record != null) {
            logger.info("Best record for user {} is {}", userEmail, record);
        } else {
            logger.info("No record for the user {}", userEmail);
        }
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void recordsCounterForAnUser() {
        final var userEmail = getRandomEmail();
        final var recordsCounter = outdoorRunningService.getAllOutdoorRunningRecordsOfAnUser(userEmail).size();
        if(recordsCounter > 0) {
            logger.info("{} records for the user {}", recordsCounter, userEmail);
        } else {
            logger.info("No records for the user {}", userEmail);
        }
    }

    private String getRandomEmail() {
        final var users = userService.getAllUsers();
        final var randomUser = new Random().nextInt(users.size());
        return users.get(randomUser).getEmail();
    }
}
