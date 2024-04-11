package es.wacoco.Controller;

import es.wacoco.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if ("expectedUsername".equals(loginRequest.getUsername()) &&
                "expectedPassword".equals(loginRequest.getPassword())) {
            // Generate and return token (note: this is not a real token generation)
            String token = "simulated-token";
            return ResponseEntity.ok(new AuthToken(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {

        if ("expected-confirm-token".equals(token)) {
            return ResponseEntity.ok("Account confirmed.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired confirmation token.");
        }
    }

    // Helper classes for login request and auth token response

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername(){
            return username;
        }

        public void setUsername (String username){
            this.username = username;
        }

        public String getPassword() {

            return password;
        }

        // Constructors, getters, and setters...
    }

    public static class AuthToken {
        private String token;

        public AuthToken(String token){
            this.token = token;

            // Constructors, getters, and setters...
        }
        public String getToken(){
            return token;


        }
        public void setToken(String token){
            this.token = token;
        }}}
