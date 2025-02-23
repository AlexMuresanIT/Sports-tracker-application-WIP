package health.tracker.api.producer;

import health.tracker.api.TestUtil;
import health.tracker.api.domain.Entity.OutdoorRunning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static health.tracker.api.TestUtil.*;
import static health.tracker.api.config.kafka.KafkaTopicConfiguration.NEW_OUTDOOR_RUNNING_RECORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OutdoorRunningProducerTest {

    private OutdoorRunningProducer outdoorRunningProducer;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @BeforeEach
    void setUp() {
        outdoorRunningProducer = new OutdoorRunningProducer(kafkaTemplate);
    }

    @Test
    void shouldSendNewOutdoorRunningRecord() {
        outdoorRunningProducer.sendNewOutdoorRunningRecord(getOutdoorRunningRecord(6.6, 456));

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<OutdoorRunning> valueCaptor = ArgumentCaptor.forClass(OutdoorRunning.class);

        verify(kafkaTemplate, times(1)).send(topicCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());

        final var topic = topicCaptor.getValue();
        assertThat(topic).isEqualTo(NEW_OUTDOOR_RUNNING_RECORD);

        final var outdoorRecord = valueCaptor.getValue();
        assertThat(outdoorRecord).isNotNull();
        assertThat(outdoorRecord.getDistance()).isEqualTo(6.6);
        assertThat(outdoorRecord.getBurnedCalories()).isEqualTo(456);

        final var key = keyCaptor.getValue();
        assertThat(key).isEqualTo(ID + "_" + MAIL);
    }
}
