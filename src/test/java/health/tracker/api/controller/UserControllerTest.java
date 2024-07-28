package health.tracker.api.controller;

import health.tracker.api.TestUtil;
import health.tracker.api.domain.Gender;
import health.tracker.api.domain.UserDTO;
import health.tracker.api.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private static final String ADD_USER = "/register";
    public static final String GET_USER_BY_ID = "/user/id/{id}";
    public static final String GET_USER_BY_EMAIL = "/user/email/{email}";
    public static final String UPDATE_USER_AGE = "/user/updateAge/{email}/{age}";
    public static final String UPDATE_USER_PASSWORD = "/user/updatePassword/{email}/{password}";
    public static final String UPDATE_USER_EMAIL = "/user/updateEmail/{email}/{newEmail}";
    public static final String DELETE_USER_BY_ID = "/user/delete/{id}";
    public static final String DELETE_USER_BY_EMAIL = "/user/deleteE/{email}";
    public static final String GET_ALL_USERS = "/all";

    @Test
    void testThatUserIsRegistered() throws Exception {
        final var responseAsString = this.mockMvc.perform(post(ADD_USER)
                    .header("Authorization","Basic YWRtaW46YWRtaW5ib3Nz")
                        .content(asJsonString(TestUtil.getCorrectUser()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseAsString).isEqualTo("User registered successfully.");
    }

    @Test
    void testGetUserById() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(get(GET_USER_BY_ID, "id")
                        .header("Authorization","Basic YWRtaW46YWRtaW5ib3Nz")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var response = new ObjectMapper().readValue(responseAsString, UserDTO.class);

        assertThat(response).isNotNull().satisfies(user -> {
            assertThat(user.getEmail()).isEqualTo("email@yahoo.com");
            assertThat(user.getFirstName()).isEqualTo("firstName");
            assertThat(user.getLastName()).isEqualTo("lastName");
        });
    }

    @Test
    void testGetUserByEmail() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(get(GET_USER_BY_EMAIL,"email@yahoo.com")
                        .header("Authorization","Basic YWRtaW46YWRtaW5ib3Nz")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var response = new ObjectMapper().readValue(responseAsString, UserDTO.class);

        assertThat(response).isNotNull().satisfies(user -> {
            assertThat(user.getEmail()).isEqualTo("email@yahoo.com");
            assertThat(user.getFirstName()).isEqualTo("firstName");
            assertThat(user.getLastName()).isEqualTo("lastName");
        });
    }

    @Test
    void testUpdateUserAgeSuccessfully() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(put(UPDATE_USER_AGE,"email@yahoo.com", 26)
                        .header("Authorization","Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseAsString).isEqualTo("Age updated.");
    }

    @Test
    void testUpdateUserAgeFailed() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        this.mockMvc
                .perform(put(UPDATE_USER_AGE,"incorrectEmail", 29)
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUserPassword() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(put(UPDATE_USER_PASSWORD, "email@yahoo.com", "Razvanel007!")
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseAsString).isEqualTo("Password updated.");
    }

    @Test
    void testUpdateUserPasswordFailed() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var response = this.mockMvc
                .perform(put(UPDATE_USER_PASSWORD, "email@yahoo.com", "Raz")
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo("Invalid password. Try again");
    }

    @Test
    void testUpdateUserEmailSuccessfully() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(put(UPDATE_USER_EMAIL,"email@yahoo.com", "alex@yahoo.com")
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseAsString).isEqualTo("Email updated.");
    }

    @Test
    void testUpdateUserEmailFailed() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var response = this.mockMvc
                .perform(put(UPDATE_USER_EMAIL, "email@yahoo.com", "email")
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo("Invalid email. Try again");
    }

    @Test
    void testDeleteUserByIdSuccessfully() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(delete(DELETE_USER_BY_ID, "id")
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseAsString).isEqualTo("User deleted.");
    }

    @Test
    void testDeleteUserByEmail() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(delete(DELETE_USER_BY_EMAIL, "email@yahoo.com")
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseAsString).isEqualTo("User deleted.");
    }

    @Test
    void getAllUsers() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());
        userRepository.save(TestUtil.getUser2());

        final var responseAsString = this.mockMvc
                .perform(get(GET_ALL_USERS)
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var response = new ObjectMapper().readValue(responseAsString, UserDTO[].class);

        assertThat(response).isNotNull().satisfies(users -> {
            assertThat(users[0].getAge()).isEqualTo(27);
            assertThat(users[0].getGender()).isEqualTo(Gender.FEMALE);
        });
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    private String asJsonString(final Object object) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
