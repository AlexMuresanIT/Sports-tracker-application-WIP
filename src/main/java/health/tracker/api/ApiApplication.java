package health.tracker.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan("health.tracker.api")
public class ApiApplication {

  public static void main(final String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }
}
