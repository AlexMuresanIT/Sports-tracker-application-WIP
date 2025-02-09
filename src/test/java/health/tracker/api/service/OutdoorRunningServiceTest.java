package health.tracker.api.service;

import health.tracker.api.repository.OutdoorRunningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static health.tracker.api.TestUtil.getOutdoorRunningRecord;
import static health.tracker.api.TestUtil.getUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest
public class OutdoorRunningServiceTest {

    private static final String ID = "12345";
    private static final String ID1 = "1";
    private static final String MAIL = "email@yahoo.com";

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0").withExposedPorts(27017);

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    private OutdoorRunningService outdoorRunningService;

    @Mock
    private UserService userService;

    @Autowired
    private OutdoorRunningRepository outdoorRunningRepository;

    @BeforeEach
    void setUp() {
        outdoorRunningService = new OutdoorRunningService(outdoorRunningRepository, userService);
        when(userService.getByEmail(MAIL)).thenReturn(getUser());
    }

    @Test
    void shouldAddOutdoorRunningRecord() {
        final var outdoorRecord = getOutdoorRunningRecord(11.1, 456);

        outdoorRunningService.addOutdoorRunningRecord(outdoorRecord);

        final var savedRecord = outdoorRunningRepository.findById(outdoorRecord.getId());

        assertThat(savedRecord).isPresent();
        assertThat(savedRecord.get().getDistance()).isEqualTo(outdoorRecord.getDistance());
    }

    @Test
    void shouldRetrieveAllRecordsOfUser() {
        final var record1 = getOutdoorRunningRecord(11.1, 456);
        final var record2 = getOutdoorRunningRecord(7.7, 350);

        outdoorRunningRepository.saveAll(List.of(record1, record2));

        assertThat(outdoorRunningService.getAllOutdoorRunningRecordsOfAnUser(MAIL)).hasSize(2);
    }
}
