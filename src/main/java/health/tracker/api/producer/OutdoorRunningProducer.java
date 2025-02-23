package health.tracker.api.producer;

import health.tracker.api.domain.Entity.OutdoorRunning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static health.tracker.api.config.kafka.KafkaTopicConfiguration.NEW_OUTDOOR_RUNNING_RECORD;

@Service
public class OutdoorRunningProducer {
    private static final Logger logger = LoggerFactory.getLogger(OutdoorRunningProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OutdoorRunningProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNewOutdoorRunningRecord(final OutdoorRunning outdoorRunning) {
        if (!recordIsValid(outdoorRunning)) {
            logger.warn("Outdoor running record is not valid");
            return;
        }
        try {
            logger.info("Send new outdoor running record for user with email {}", outdoorRunning.getUserEmail());
            kafkaTemplate.send(
                    NEW_OUTDOOR_RUNNING_RECORD,
                    generateMessageKey(outdoorRunning),
                    outdoorRunning);
        } catch (Exception e) {
            logger.error("Could not send new outdoor running record", e);
        }
    }

    private String generateMessageKey(final OutdoorRunning outdoorRunning) {
        return outdoorRunning.getId() + "_" + outdoorRunning.getUserEmail();
    }

    private boolean recordIsValid(final OutdoorRunning outdoorRunning) {
        return outdoorRunning.getUserEmail() != null && outdoorRunning.getId() != null;
    }
}
