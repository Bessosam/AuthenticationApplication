package es.wacoco.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import es.wacoco.Model.User;
import es.wacoco.Model.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service

public class UserService {

    @Autowired
    private AWSCognitoIdentityProvider cognitoIdentityProvider;


    public User registerUser(UserRegistrationRequest userRegistrationRequest) {
        SignUpRequest signUpRequest = new SignUpRequest()
                .withClientId("YourClientId") // Replace "YourClientId" with your actual Cognito client ID
                .withUsername(userRegistrationRequest.getUsername())
                .withPassword(userRegistrationRequest.getPassword())
                .withUserAttributes(
                        new AttributeType().withName("email").withValue(userRegistrationRequest.getEmail())
                );

        try {
            SignUpResult signUpResponse = cognitoIdentityProvider.signUp(signUpRequest);

            // Construct a User object with the registered user's details and return it
            User registeredUser = new User();
            registeredUser.setUsername(userRegistrationRequest.getUsername());
            registeredUser.setEmail(userRegistrationRequest.getEmail());


            return registeredUser;

        } catch (Exception e) {

            throw new RuntimeException("User registration failed: " + e.getMessage(), e);
        }
    }

    public User loginUser(String username, String password) {
        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withAuthFlow("USER_PASSWORD_AUTH")
                // Replace "YourClientId" with your actual Cognito client ID
                .withClientId("YourClientId")
                .withAuthParameters(
                        Map.of(
                                // Use email as the username
                                "USERNAME", username,
                                "PASSWORD", password
                        )
                );

        try {
            InitiateAuthResult authResult = cognitoIdentityProvider.initiateAuth(authRequest);
            AuthenticationResultType authResponse = authResult.getAuthenticationResult();

            // At this point, the user is successfully authenticated, and you can access JWT tokens:
            String accessToken = authResponse.getAccessToken();
            String idToken = authResponse.getIdToken();
            String refreshToken = authResponse.getRefreshToken();

            // Construct a User object with the logged-in user's details and return it
            User loggedInUser = new User();
            loggedInUser.setUsername(username);
            // Store tokens or other necessary information in the User object

            return loggedInUser;

        } catch (Exception e) {
            // Handle login failure
            throw new RuntimeException("User login failed: " + e.getMessage(), e);
        }
    }
}
