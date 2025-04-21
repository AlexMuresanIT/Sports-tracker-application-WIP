package health.tracker.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WhatsappApiService {
  private static final Logger logger = LoggerFactory.getLogger(WhatsappApiService.class);

  private final WebClient webClient;

  @Value("${whatsapp.api}")
  private String WHATSAPP_URL;

  @Value("${whatsapp.api-key}")
  private String API_KEY;

  public WhatsappApiService(final WebClient.Builder builder) {
    this.webClient = builder.build();
  }

  public void sendWhatsappMessage(final String phoneNumber, final String whatsappMessage) {
    if (phoneNumber.isBlank() || whatsappMessage.isBlank()) {
      return;
    }

    try {
      final var uri =
          UriComponentsBuilder.fromUriString(WHATSAPP_URL)
              .queryParam("phone", phoneNumber)
              .queryParam("text", whatsappMessage)
              .queryParam("apikey", API_KEY)
              .build()
              .toUri();

      logger.info("Sending Whatsapp message to {}", phoneNumber);
      webClient.post().uri(uri).retrieve().bodyToMono(String.class).block();
    } catch (final Exception e) {
      logger.error("Whatsapp message could not be sent", e);
    }
  }
}
