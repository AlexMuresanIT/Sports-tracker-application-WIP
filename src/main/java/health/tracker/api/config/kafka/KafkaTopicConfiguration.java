package health.tracker.api.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {
  public static final String REGISTER_USER = "registeredUser";
  public static final String UPDATE_USER = "updateUser";
  public static final String NEW_OUTDOOR_RUNNING_RECORD = "newOutdoorRunningRecord";

  @Bean
  public NewTopic registerUserTopic() {
    return new NewTopic(REGISTER_USER, 1, (short) 1);
  }

  @Bean
  public NewTopic updateUserTopic() {
    return new NewTopic(UPDATE_USER, 1, (short) 1);
  }

  @Bean
  public NewTopic newOutdoorRunningRecordTopic() {
    return new NewTopic(NEW_OUTDOOR_RUNNING_RECORD, 1, (short) 1);
  }
}
