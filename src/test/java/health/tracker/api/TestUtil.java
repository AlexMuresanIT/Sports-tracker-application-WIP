package health.tracker.api;

import health.tracker.api.domain.Gender;
import health.tracker.api.domain.OutdoorRunning;
import health.tracker.api.domain.User;

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
        return new OutdoorRunning(ID, MAIL, distance, "12.23.12", burnedCalories, 6.5);
    }
}
