package es.wacoco.Controller;

import es.wacoco.Model.User;
import es.wacoco.Model.UserConfirmationRequest;
import es.wacoco.Model.UserLoginRequest;
import es.wacoco.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @ModelAttribute UserLoginRequest userLoginRequest) {
        try {
            logger.info("Logging in user: {}", userLoginRequest.getUsername());
            User loggedInUser = userService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());
            // Antag att vi har en URL till en säker sida för inloggade användare
            return ResponseEntity.ok("redirect:/some-secured-page");
        } catch (Exception e) {
            logger.error("Login failed for user: {}", userLoginRequest.getUsername(), e);
            // Antag att vi har en sida som visar felmeddelanden
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public String showConfirmationForm() {
        return "confirm";
    }

    @PostMapping("/confirm")
    public String confirmUser(@ModelAttribute UserConfirmationRequest confirmationRequest, Model model) {
        try {
            boolean isConfirmed = userService.confirmUserRegistration(confirmationRequest.getUsername(), confirmationRequest.getConfirmationCode());
            if (isConfirmed) {
                return "redirect:/confirmation-success";
            } else {
                model.addAttribute("error", "Confirmation code is incorrect or user does not exist.");
                return "confirm";
            }
        } catch (Exception e) {
            logger.error("Confirmation failed for user: {}", confirmationRequest.getUsername(), e);
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "confirm";
        }
    }
}
