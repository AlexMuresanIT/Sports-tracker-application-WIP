package health.tracker.api;

import health.tracker.api.domain.Gender;
import health.tracker.api.domain.User;

public class TestUtil {

    public static User getUser() {
        return new User(
                "id",
                "name",
                "email",
                "lastName",
                "adada",
                Gender.MALE,
                20);
    }

    public static User userWithWrongPw() {
        return new User(
                "id",
                "name",
                "email",
                "lastName",
                "adadada",
                Gender.MALE,
                24);
    }

    public static User getCorrectUser() {
        return new User(
                "id",
                "firstName",
                "email@yahoo.com",
                "lastName",
                "lastName007!",
                Gender.MALE,
                24);
    }

    public static User getUser2() {
        return new User(
                "id",
                "firstName",
                "email@yahoo.com",
                "lastName",
                "lastName007!",
                Gender.FEMALE,
                27);
    }
}
