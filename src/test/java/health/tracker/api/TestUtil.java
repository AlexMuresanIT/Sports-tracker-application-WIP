package health.tracker.api;

import static health.tracker.api.domain.Status.SUCCESS;

import health.tracker.api.domain.DTO.UserDTO;
import health.tracker.api.domain.Entity.OutdoorRunning;
import health.tracker.api.domain.Entity.User;
import health.tracker.api.domain.Gender;

public class TestUtil {
  public static final String ID = "12345";
  public static final String MAIL = "email@yahoo.com";
  public static final String PHONE_NUMBER = "077734343";

  public static User getUser() {
    return new User("id", "name", MAIL, "lastName", "adada", Gender.MALE, 20, PHONE_NUMBER);
  }

  public static User userWithWrongPw() {
    return new User("id", "name", MAIL, "lastName", "adadada", Gender.MALE, 24, PHONE_NUMBER);
  }

  public static User getCorrectUser() {
    return new User(
        "id", "firstName", MAIL, "lastName", "lastName007!", Gender.MALE, 24, PHONE_NUMBER);
  }

  public static UserDTO getUserDTO() {
    return new UserDTO("id", "firstName", MAIL + "da", Gender.MALE, 24, "asdadaA4!", PHONE_NUMBER);
  }

  public static User getUser2() {
    return new User(
        "id", "firstName", MAIL, "lastName", "lastName007!", Gender.FEMALE, 27, PHONE_NUMBER);
  }

  public static OutdoorRunning getOutdoorRunningRecord(
      final Double distance, final Integer burnedCalories) {
    return new OutdoorRunning(ID, MAIL, distance, "12.23.12", burnedCalories, 6.5, SUCCESS);
  }
}
