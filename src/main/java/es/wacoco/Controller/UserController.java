package es.wacoco.Controller;

import es.wacoco.Model.User;
import es.wacoco.Model.UserLoginRequest;
import es.wacoco.Model.UserRegistrationRequest;
import es.wacoco.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@Component
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        User registeredUser = userService.registerUser(userRegistrationRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        User loggedInUser = userService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        return ResponseEntity.ok(loggedInUser);
    }
}
