package health.tracker.api;

import health.tracker.api.model.Gender;
import health.tracker.api.model.User;

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

    public static User updatedUserAge() {
        return new User(
                "id",
                "name",
                "email",
                "lastName",
                "adada",
                Gender.MALE,
                24);
    }
}
