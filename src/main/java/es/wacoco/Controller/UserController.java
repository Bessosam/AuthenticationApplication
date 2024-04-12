package es.wacoco.Controller;

import es.wacoco.Model.User;
import es.wacoco.Model.UserLoginRequest;
import es.wacoco.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        User loggedInUser = userService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        return ResponseEntity.ok(loggedInUser);

    }
    @GetMapping("/confirm")
    public String showConfirmationForm() {
        return "confirm";
    }
    @GetMapping("/confirmRegistration")
    public ResponseEntity<String> confirmRegistration(@RequestParam("username") String username, @RequestParam("confirmationCode") String confirmationCode) {
        userService.confirmRegistration(username, confirmationCode);
        return ResponseEntity.ok("User registration confirmed successfully.");
    }
}



