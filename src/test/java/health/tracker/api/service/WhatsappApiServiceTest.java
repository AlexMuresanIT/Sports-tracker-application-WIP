package health.tracker.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
public class WhatsappApiServiceTest {

  private WhatsappApiService whatsappApiService;

  @Mock private WebClient.Builder webClientBuilder;

  @Mock private WebClient webClient;

  @Mock private WebClient.RequestBodyUriSpec requestBodyUriSpec;

  @Mock private WebClient.ResponseSpec responseSpec;

  @BeforeEach
  void setUp() {
    when(webClientBuilder.build()).thenReturn(webClient);
    whatsappApiService = new WhatsappApiService(webClientBuilder);

    ReflectionTestUtils.setField(
        whatsappApiService, "WHATSAPP_URL", "https://api.example.com/whatsapp");
    ReflectionTestUtils.setField(whatsappApiService, "API_KEY", "test-api-key");

    // Mock the WebClient chain
    when(webClient.post()).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.uri((URI) any())).thenReturn(requestBodyUriSpec);
    when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("success"));
  }

  @Test
  void shouldSendWhatsappMessage() {
    final var phoneNumber = "1234567890";
    final var message = "Test message";

    whatsappApiService.sendWhatsappMessage(phoneNumber, message);

    verify(webClient, times(1)).post();
  }
}
