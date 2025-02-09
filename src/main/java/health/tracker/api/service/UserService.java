package health.tracker.api.service;

import health.tracker.api.domain.UserDTO;
import health.tracker.api.exception.InvalidData;
import health.tracker.api.exception.NoUserFoundException;
import health.tracker.api.domain.User;
import health.tracker.api.mappers.UserMapper;
import health.tracker.api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void addUser(final User user) {
        if(checkPw(user.getPassword()) && checkEmail(user.getEmail())){
            userRepository.save(user);
            return;
        }
        throw new InvalidData("Invalid password or email. Try again");
    }

    public User getById(final String id) {
        final var user = userRepository.findById(id);
        return user.orElseThrow(() -> new NoUserFoundException("No user found with id " + id));
    }

    public User getByEmail(final String email) {
        final var maybeUser = userRepository.findByEmail(email);
        return maybeUser.orElseThrow(() -> new NoUserFoundException("No user found with email " + email));
    }

    public Page<User> getAllUsersPaginated() {
        final var pageable = PageRequest.of(0, 10, Sort.by("firstName"));
        return userRepository.findAll(pageable);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserAge(final String email, final Integer age) {
        final var maybeUser = userRepository.findByEmail(email);
        maybeUser.map(foundUser -> {
            foundUser.setAge(age);
            userRepository.save(foundUser);
            return null;
        });
    }

    public void updateUserPassword(final String email, final String password) {
        final var maybeUser = userRepository.findByEmail(email);
        if(maybeUser.isPresent()) {
            if(checkPw(password)) {
                final var user = maybeUser.get();
                user.setPassword(password);
                userRepository.save(user);
                return;
            }
            throw new InvalidData("Invalid password. Try again");
        }
        throw new NoUserFoundException("No user found with email " + email);
    }

    public void updateUserEmail(final String email, final String newEmail) {
        final var maybeUser = userRepository.findByEmail(email);
        if(maybeUser.isPresent()) {
            if(checkEmail(newEmail) && !email.equals(newEmail)) {
                final var user = maybeUser.get();
                user.setEmail(newEmail);
                userRepository.save(user);
                return;
            }
            throw new InvalidData("Invalid email. Try again");
        }
        throw new NoUserFoundException("No user found with email " + email);
    }

    public void deleteUserById(final String id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByEmail(final String email) {
        userRepository.deleteByEmail(email);
    }

    public Page<UserDTO> convertUserToDTO(final Page<User> users) {
        return users.map(userMapper::toDTO);
    }

    private boolean checkPw(final String password) {
        if(password.length() < 8) {
            return false;
        }

        Pattern pattern1 = Pattern.compile("[!@#$%^&?]");
        Pattern pattern2 = Pattern.compile("[A-Z]");
        Pattern pattern3 = Pattern.compile("[0-9]");

        Matcher matcher = pattern1.matcher(password);
        Matcher matcher1 = pattern2.matcher(password);
        Matcher matcher2 = pattern3.matcher(password);

        return matcher.find() && matcher1.find() && matcher2.find();
    }

    private boolean checkEmail(final String email) {
        Pattern pattern = Pattern.compile("@yahoo.com");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
