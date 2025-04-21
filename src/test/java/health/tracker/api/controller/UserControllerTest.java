package health.tracker.api.controller;

import static health.tracker.api.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import health.tracker.api.domain.DTO.UserDTO;
import health.tracker.api.repository.UserRepository;
import java.util.List;

import health.tracker.api.service.WhatsappApiService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
public class UserControllerTest {
  public static final String EMAIL = "email@yahoo.com";

  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @Autowired protected com.fasterxml.jackson.databind.ObjectMapper objectMapper;

  @Mock
  private WhatsappApiService whatsappApiService;

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
  public void testThatUserIsRegistered() throws Exception {
    final var user = getUserDTO();
    final var responseAsString =
        this.mockMvc
            .perform(
                post(ADD_USER)
                    .content(objectMapper.writeValueAsString(user))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(responseAsString).isEqualTo("User registered successfully.");

    verify(whatsappApiService, times(1)).sendWhatsappMessage(user.getPhoneNumber(), );
  }

  @Test
  public void testGetUserById() throws Exception {
    userRepository.save(getCorrectUser());

    final var responseAsString =
        this.mockMvc
            .perform(get(GET_USER_BY_ID, "id").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    final var response = objectMapper.readValue(responseAsString, UserDTO.class);

    assertThat(response)
        .isNotNull()
        .satisfies(
            user -> {
              assertThat(user.getEmail()).isEqualTo(EMAIL);
              assertThat(user.getAge()).isEqualTo(24);
            });
  }

  @Test
  public void testGetUserByEmail() throws Exception {
    userRepository.save(getCorrectUser());

    final var responseAsString =
        this.mockMvc
            .perform(get(GET_USER_BY_EMAIL, EMAIL).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    final var response = objectMapper.readValue(responseAsString, UserDTO.class);

    assertThat(response)
        .isNotNull()
        .satisfies(
            user -> {
              assertThat(user.getEmail()).isEqualTo(EMAIL);
              assertThat(user.getAge()).isEqualTo(24);
            });
  }

  @Test
  public void testUpdateUserAgeSuccessfully() throws Exception {
    userRepository.save(getCorrectUser());

    final var responseAsString =
        this.mockMvc
            .perform(put(UPDATE_USER_AGE, EMAIL, 26))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(responseAsString).isEqualTo("User updated successfully.");
  }

  @Test
  public void testUpdateUserPassword() throws Exception {
    userRepository.save(getCorrectUser());

    final var responseAsString =
        this.mockMvc
            .perform(put(UPDATE_USER_PASSWORD, EMAIL, "Razvanel007!"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(responseAsString).isEqualTo("Password updated.");
  }

  @Test
  public void testUpdateUserPasswordFailed() throws Exception {
    userRepository.save(getCorrectUser());

    final var response =
        this.mockMvc
            .perform(put(UPDATE_USER_PASSWORD, EMAIL, "Raz"))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(response).isEqualTo("Invalid password. Try again");
  }

  @Test
  public void testUpdateUserEmailSuccessfully() throws Exception {
    userRepository.save(getCorrectUser());

    final var responseAsString =
        this.mockMvc
            .perform(put(UPDATE_USER_EMAIL, EMAIL, "alex@yahoo.com"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(responseAsString).isEqualTo("Email updated.");
  }

  @Test
  public void testUpdateUserEmailFailed() throws Exception {
    userRepository.save(getCorrectUser());

    final var response =
        this.mockMvc
            .perform(put(UPDATE_USER_EMAIL, EMAIL, "email"))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(response).isEqualTo("Invalid email. Try again");
  }

  @Test
  public void testDeleteUserByIdSuccessfully() throws Exception {
    userRepository.save(getCorrectUser());

    final var responseAsString =
        this.mockMvc
            .perform(delete(DELETE_USER_BY_ID, "id"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(responseAsString).isEqualTo("User deleted.");
  }

  @Test
  public void testDeleteUserByEmail() throws Exception {
    userRepository.save(getCorrectUser());

    final var responseAsString =
        this.mockMvc
            .perform(delete(DELETE_USER_BY_EMAIL, EMAIL))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    assertThat(responseAsString).isEqualTo("User deleted.");
  }

  @Test
  public void getAllUsers() throws Exception {
    userRepository.save(getCorrectUser());
    userRepository.save(getUser2());

    final var responseAsString =
        this.mockMvc
            .perform(get(GET_ALL_USERS))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    final var response =
        objectMapper.readValue(responseAsString, new TypeReference<List<UserDTO>>() {});

    assertThat(response)
        .isNotNull()
        .satisfies(
            users -> {
              final var user = users.get(0);
              assertThat(user).isNotNull();
              assertThat(user.getEmail()).isEqualTo(EMAIL);
            });
  }

  @AfterEach
  void cleanUp() {
    userRepository.deleteAll();
  }
}
