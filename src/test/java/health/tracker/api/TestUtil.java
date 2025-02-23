package health.tracker.api;

import health.tracker.api.domain.Gender;
import health.tracker.api.domain.Entity.OutdoorRunning;
import health.tracker.api.domain.Entity.User;
import health.tracker.api.domain.DTO.UserDTO;
import health.tracker.api.domain.Status;

import static health.tracker.api.domain.Status.SUCCESS;

public class TestUtil {
    private static final String ID = "12345";
    private static final String MAIL = "email@yahoo.com";

    public static User getUser() {
        return new User(
                "id",
                "name",
                MAIL,
                "lastName",
                "adada",
                Gender.MALE,
                20);
    }

    public static User userWithWrongPw() {
        return new User(
                "id",
                "name",
                MAIL,
                "lastName",
                "adadada",
                Gender.MALE,
                24);
    }

    public static User getCorrectUser() {
        return new User(
                "id",
                "firstName",
                MAIL,
                "lastName",
                "lastName007!",
                Gender.MALE,
                24);
    }

    public static UserDTO getUserDTO() {
        return new UserDTO(
                "id",
                "firstName",
                MAIL+"da",
                Gender.MALE,
                24,
                "asdadaA4!");
    }

    public static User getUser2() {
        return new User(
                "id",
                "firstName",
                MAIL,
                "lastName",
                "lastName007!",
                Gender.FEMALE,
                27);
    }

    public static OutdoorRunning getOutdoorRunningRecord(final Double distance, final Integer burnedCalories) {
        return new OutdoorRunning(ID, MAIL, distance, "12.23.12", burnedCalories, 6.5, SUCCESS);
    }
}
