package health.tracker.api.producer;

import static health.tracker.api.TestUtil.getUser;
import static health.tracker.api.config.kafka.KafkaTopicConfiguration.REGISTER_USER;
import static health.tracker.api.config.kafka.KafkaTopicConfiguration.UPDATE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import health.tracker.api.domain.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class UserProducerTest {

  @Mock private KafkaTemplate<String, Object> kafkaTemplate;

  private UserProducer userProducer;

  @BeforeEach
  void setUp() {
    userProducer = new UserProducer(kafkaTemplate);
  }

  @Test
  void testShouldSendMessageWithNewUserRegistered() {
    userProducer.sendNewUserMessage(getUser());

    final ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<User> valueCaptor = ArgumentCaptor.forClass(User.class);

    verify(kafkaTemplate, times(1))
        .send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());

    final var registeredUser = valueCaptor.getValue();
    assertThat(registeredUser).isNotNull();
    assertThat(registeredUser.getFirstName()).isEqualTo("name");

    final var topic = topicCaptor.getValue();
    assertThat(topic).isNotNull();
    assertThat(topic).isEqualTo(REGISTER_USER);
  }

  @Test
  void testShouldSendUpdatedUserMessage() {
    userProducer.sendUpdatedUser(getUser());

    final ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<User> valueCaptor = ArgumentCaptor.forClass(User.class);

    verify(kafkaTemplate, times(1))
        .send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());

    final var registeredUser = valueCaptor.getValue();
    assertThat(registeredUser).isNotNull();
    assertThat(registeredUser.getFirstName()).isEqualTo("name");

    final var topic = topicCaptor.getValue();
    assertThat(topic).isNotNull();
    assertThat(topic).isEqualTo(UPDATE_USER);
  }

  @Test
  void testShouldNotSendNullUser() {
    userProducer.sendNewUserMessage(null);

    final ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    final ArgumentCaptor<User> valueCaptor = ArgumentCaptor.forClass(User.class);

    verify(kafkaTemplate, times(0))
        .send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());
  }
}
