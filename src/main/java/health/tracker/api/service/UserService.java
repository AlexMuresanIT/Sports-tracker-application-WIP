package health.tracker.api.service;

import health.tracker.api.exception.InvalidData;
import health.tracker.api.exception.NoUserFoundException;
import health.tracker.api.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import health.tracker.api.repository.UserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        if(checkPw(user.getPassword()) && checkEmail(user.getEmail())){
            userRepository.save(user);
            return;
        }
        throw new InvalidData("Invalid password or email. Try again");
    }

    public User getById(String id) {
        final var user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        throw new NoUserFoundException("No user found with id " + id);
    }

    public User getByEmail(String email) {
        final var maybeUser = userRepository.findByEmail(email);
        if(maybeUser.isPresent()) {
            return maybeUser.get();
        }
        throw new NoUserFoundException("No user found with email " + email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(sortByIdAsc());
    }

    public void updateUserAge(String email, Integer age) {
        final var maybeUser = userRepository.findByEmail(email);
        if(maybeUser.isPresent()) {
            if(age >= maybeUser.get().getAge()) {
                final var user = maybeUser.get();
                user.setAge(age);
                userRepository.save(user);
                return;
            }
            throw new InvalidData("You cannot enter an age smaller than existing one.");
        }
        throw new NoUserFoundException("No user found with email " + email);
    }

    public void updateUserPassword(String email, String password) {
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

    public void updateUserEmail(String email, String newEmail) {
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

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    private Sort sortByIdAsc() {
        return Sort.by(Sort.Direction.ASC, "id");
    }

    private boolean checkPw(String password) {
        if(password.length() < 8) {
            return false;
        }

        String specialCharacters = "[!@#$%^&?]";
        String capitalLetter = "[A-Z]";
        String numbers = "[0-9]";

        Pattern pattern1 = Pattern.compile(specialCharacters);
        Pattern pattern2 = Pattern.compile(capitalLetter);
        Pattern pattern3 = Pattern.compile(numbers);

        Matcher matcher = pattern1.matcher(password);
        Matcher matcher1 = pattern2.matcher(password);
        Matcher matcher2 = pattern3.matcher(password);

        if(matcher.find() && matcher1.find() && matcher2.find()) {
            return true;
        }
        return false;
    }

    private boolean checkEmail(String email) {
        String emailSpecial = "@yahoo.com";
        Pattern pattern = Pattern.compile(emailSpecial);
        Matcher matcher = pattern.matcher(email);
        if(matcher.find()) {
            return true;
        }
        return false;
    }
}
