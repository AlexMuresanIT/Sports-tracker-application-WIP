package health.tracker.api.controller.rest;

import health.tracker.api.domain.DTO.UserDTO;
import health.tracker.api.exception.InvalidData;
import health.tracker.api.exception.NoUserFoundException;
import health.tracker.api.mappers.UserMapper;
import health.tracker.api.producer.UserProducer;
import health.tracker.api.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;
  private final UserMapper userMapper;
  private final UserProducer userProducer;

  public UserController(
      final UserService userService, final UserMapper userMapper, final UserProducer userProducer) {
    this.userService = userService;
    this.userMapper = userMapper;
    this.userProducer = userProducer;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody UserDTO user) {
    try {
      final var userDomain = userMapper.toDomain(user);
      userService.addUser(userDomain);
      userProducer.sendNewUserMessage(userDomain);
      return ResponseEntity.ok("User registered successfully.");
    } catch (InvalidData e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/user/id/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
    log.info("Getting user by id: {}", id);
    try {
      final var user = userService.getById(id);
      return ResponseEntity.ok(userMapper.toDTO(user));
    } catch (NoUserFoundException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping(value = "/user/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
    log.info("Getting user by email: {}", email);
    try {
      final var user = userService.getByEmail(email);
      return ResponseEntity.ok(userMapper.toDTO(user));
    } catch (NoUserFoundException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/user/updateAge/{email}/{age}")
  public ResponseEntity<String> updateUserAge(
      @PathVariable String email, @PathVariable Integer age) {
    final var updatedUser = userService.updateUserAge(email, age);
    userProducer.sendUpdatedUser(updatedUser);
    return ResponseEntity.ok("User updated successfully.");
  }

  @PutMapping("/user/updatePassword/{email}/{password}")
  public ResponseEntity<String> updateUserPassword(
      @PathVariable String email, @PathVariable String password) {
    try {
      final var updatedUser = userService.updateUserPassword(email, password);
      userProducer.sendUpdatedUser(updatedUser);
      return new ResponseEntity<>("Password updated.", HttpStatus.OK);
    } catch (NoUserFoundException | InvalidData e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/user/updateEmail/{email}/{newEmail}")
  public ResponseEntity<String> updateUserEmail(
      @PathVariable String email, @PathVariable String newEmail) {
    try {
      final var updatedUser = userService.updateUserEmail(email, newEmail);
      userProducer.sendUpdatedUser(updatedUser);
      return new ResponseEntity<>("Email updated.", HttpStatus.OK);
    } catch (NoUserFoundException | InvalidData e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/user/delete/{id}")
  public ResponseEntity<String> deleteUserById(@PathVariable String id) {
    log.info("Deleting user by id: {}", id);
    userService.deleteUserById(id);
    return ResponseEntity.ok("User deleted.");
  }

  @DeleteMapping("/user/deleteE/{email}")
  public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
    log.info("Deleting user by email: {}", email);
    userService.deleteUserByEmail(email);
    return ResponseEntity.ok("User deleted.");
  }

  @GetMapping("/all/paged")
  public ResponseEntity<Page<UserDTO>> getAllUsersPaginated() {
    log.info("Getting all users");
    final var users = userService.getAllUsersPaginated();
    return ResponseEntity.ok(userService.convertUserToDTO(users));
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    log.info("Getting all users");
    final var users = userService.getAllUsers();
    return ResponseEntity.ok(userMapper.toDTO(users));
  }

  @GetMapping("/logged")
  public ResponseEntity<String> currentUser(@AuthenticationPrincipal UserDetails user) {
    return ResponseEntity.ok("Current user " + user.getUsername());
  }
}
