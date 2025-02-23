package health.tracker.api.controller;

import health.tracker.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserAPIController {

  private static final Logger log = LoggerFactory.getLogger(UserAPIController.class);

  @Value("${app.title}")
  private String appName;

  private final UserService userService;

  public UserAPIController(UserService userService) {
    this.userService = userService;
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping("/")
  public String showUsers(Model model) {
    log.info("Showing users");
    model.addAttribute("users", userService.getAllUsers());
    model.addAttribute("appName", appName);
    return "users";
  }
}
