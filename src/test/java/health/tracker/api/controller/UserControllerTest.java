package health.tracker.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import health.tracker.api.TestUtil;
import health.tracker.api.domain.DTO.UserDTO;
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

import java.util.List;

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

    @Autowired
    protected com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Test
    public void testThatUserIsRegistered() throws Exception {
        final var responseAsString = this.mockMvc.perform(post(ADD_USER)
                    .header("Authorization","Basic YWRtaW46YWRtaW5ib3Nz")
                        .content(objectMapper.writeValueAsString(TestUtil.getUserDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseAsString).isEqualTo("User registered successfully.");
    }

    @Test
    public void testGetUserById() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(get(GET_USER_BY_ID, "id")
                        .header("Authorization","Basic YWRtaW46YWRtaW5ib3Nz")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var response = objectMapper.readValue(responseAsString, UserDTO.class);

        assertThat(response).isNotNull().satisfies(user -> {
            assertThat(user.getEmail()).isEqualTo("email@yahoo.com");
            assertThat(user.getAge()).isEqualTo(24);
        });
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(get(GET_USER_BY_EMAIL,"email@yahoo.com")
                        .header("Authorization","Basic YWRtaW46YWRtaW5ib3Nz")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var response = objectMapper.readValue(responseAsString, UserDTO.class);

        assertThat(response).isNotNull().satisfies(user -> {
            assertThat(user.getEmail()).isEqualTo("email@yahoo.com");
            assertThat(user.getAge()).isEqualTo(24);
        });
    }

    @Test
    public void testUpdateUserAgeSuccessfully() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());

        final var responseAsString = this.mockMvc
                .perform(put(UPDATE_USER_AGE,"email@yahoo.com", 26)
                        .header("Authorization","Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseAsString).isEqualTo("User updated successfully.");
    }

    @Test
    public void testUpdateUserPassword() throws Exception {
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
    public void testUpdateUserPasswordFailed() throws Exception {
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
    public void testUpdateUserEmailSuccessfully() throws Exception {
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
    public void testUpdateUserEmailFailed() throws Exception {
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
    public void testDeleteUserByIdSuccessfully() throws Exception {
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
    public void testDeleteUserByEmail() throws Exception {
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
    public void getAllUsers() throws Exception {
        userRepository.save(TestUtil.getCorrectUser());
        userRepository.save(TestUtil.getUser2());

        final var responseAsString = this.mockMvc
                .perform(get(GET_ALL_USERS)
                        .header("Authorization", "Basic YWRtaW46YWRtaW5ib3Nz"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var response = objectMapper.readValue(responseAsString, new TypeReference<List<UserDTO>>() {});

        assertThat(response).isNotNull().satisfies(users -> {
            final var user = users.get(0);
            assertThat(user).isNotNull();
            assertThat(user.getEmail()).isEqualTo("email@yahoo.com");
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
