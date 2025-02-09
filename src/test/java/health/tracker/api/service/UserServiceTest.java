package health.tracker.api.service;

import health.tracker.api.TestUtil;
import health.tracker.api.exception.InvalidData;
import health.tracker.api.exception.NoUserFoundException;
import health.tracker.api.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Test that user is saved to db")
    void testThatUserIsSaved() {
        when(userRepository.findById("id")).thenReturn(Optional.of(TestUtil.getUser()));
        final var savedUser = userService.getById("id");
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo("id");
        assertThat(savedUser.getEmail()).isEqualTo("email@yahoo.com");
    }

    @Test
    @DisplayName("User with incorrect password is not saved")
    void testUserWithIncorrectPasswordNotSaved() {
        try{
            userService.addUser(TestUtil.userWithWrongPw());
        }catch (InvalidData e){
            assertThat(e).isInstanceOf(InvalidData.class);
            assertThat(e.getMessage()).isEqualTo("Invalid password or email. Try again");
        }
    }

    @Test
    @DisplayName("Test that the user with email is retrieved")
    void testThatCorrectUserIsReturned() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(TestUtil.getUser()));
        final var savedUser = userService.getByEmail("email");
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo("id");
        assertThat(savedUser.getEmail()).isEqualTo("email@yahoo.com");
    }

    @Test
    @DisplayName("Test that a user is not found")
    void testThatUserWithSpecificEmailIsDeleted() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.empty());
        assertThrows(NoUserFoundException.class, () -> userService.getByEmail("email"));
    }

    @Test
    @DisplayName("Updated password is incorrect")
    void testUserUpdatedPasswordIsIncorrect() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(TestUtil.getUser()));
        try{
            userService.updateUserPassword("email","dada");
        }catch (InvalidData e){
            assertThat(e).isInstanceOf(InvalidData.class);
            assertThat(e.getMessage()).isEqualTo("Invalid password. Try again");
        }
    }

    @Test
    @DisplayName("Updated email is incorrect")
    void testUserUpdatesEmailIsIncorrect() {
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(TestUtil.getUser()));
        try{
            userService.updateUserEmail("email","dada");
        }catch (InvalidData e){
            assertThat(e).isInstanceOf(InvalidData.class);
            assertThat(e.getMessage()).isEqualTo("Invalid email. Try again");
        }
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
}
