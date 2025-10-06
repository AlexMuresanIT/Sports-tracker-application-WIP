package health.tracker.api.producer;

import static health.tracker.api.config.kafka.KafkaTopicConfiguration.REGISTER_USER;
import static health.tracker.api.config.kafka.KafkaTopicConfiguration.UPDATE_USER;

import health.tracker.api.config.HealthTrackerConfig;
import health.tracker.api.domain.Entity.User;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {
  private static final Logger logger = LoggerFactory.getLogger(UserProducer.class);

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private final HealthTrackerConfig healthTrackerConfig;

  public UserProducer(
      final KafkaTemplate<String, Object> kafkaTemplate,
      final HealthTrackerConfig healthTrackerConfig) {
    this.kafkaTemplate = kafkaTemplate;
    this.healthTrackerConfig = healthTrackerConfig;
  }

  public void sendNewUserMessage(final User user) {
    if (user == null) {
      logger.warn("User is null");
      return;
    }
    try {
      if (healthTrackerConfig.getKafka().enabled()) {
        logger.info(
            "New user with name {} and email {} registered", user.getFirstName(), user.getEmail());
        kafkaTemplate.send(REGISTER_USER, UUID.randomUUID().toString(), user);
      }
    } catch (final Exception e) {
      logger.error("Error while sending new user message", e);
    }
  }

  public void sendUpdatedUser(final User user) {
    if (user == null) {
      logger.warn("User is null");
      return;
    }
    try {
      if (healthTrackerConfig.getKafka().enabled()) {
        logger.info(
            "Send user updated with name {} and email {}", user.getFirstName(), user.getEmail());
        kafkaTemplate.send(UPDATE_USER, UUID.randomUUID().toString(), user);
      }
    } catch (final Exception e) {
      logger.error("Error while sending updated user message", e);
    }
  }
}
