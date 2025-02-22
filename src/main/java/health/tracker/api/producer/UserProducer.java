package health.tracker.api.producer;

import health.tracker.api.domain.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static health.tracker.api.config.kafka.KafkaTopicConfiguration.REGISTER_USER;
import static health.tracker.api.config.kafka.KafkaTopicConfiguration.UPDATE_USER;

@Service
public class UserProducer {
    private static final Logger logger = LoggerFactory.getLogger(UserProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNewUserMessage(final User user) {
        try{
            logger.info("New user with name {} and email {} registered", user.getFirstName(), user.getEmail());
            kafkaTemplate.send(
                    REGISTER_USER,
                    UUID.randomUUID().toString(),
                    user);
        } catch (Exception e){
            logger.error("Error while sending new user message", e);
        }
    }

    public void sendUpdatedUser(final User user) {
        try {
            logger.info("Send user updated with name {} and email {}", user.getFirstName(), user.getEmail());
            kafkaTemplate.send(
                    UPDATE_USER,
                    UUID.randomUUID().toString(),
                    user);
        } catch (Exception e) {
            logger.error("Error while sending updated user message", e);
        }
    }
}
