package health.tracker.api.producer;

import health.tracker.api.domain.Entity.OutdoorRunning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static health.tracker.api.config.kafka.KafkaTopicConfiguration.NEW_OUTDOOR_RUNNING_RECORD;

@Service
public class OutdoorRunningProducer {
    private static final Logger logger = LoggerFactory.getLogger(OutdoorRunningProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OutdoorRunningProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNewOutdoorRunningRecord(final OutdoorRunning outdoorRunning) {
        try {
            logger.info("Send new outdoor running record for user with email {}", outdoorRunning.getUserEmail());
            kafkaTemplate.send(
                    NEW_OUTDOOR_RUNNING_RECORD,
                    UUID.randomUUID().toString(),
                    outdoorRunning);
        } catch (Exception e) {
            logger.error("Could not send new outdoor running record", e);
        }
    }
}
