package es.wacoco.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import es.wacoco.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    private AWSCognitoIdentityProvider cognitoIdentityProvider;
    public User loginUser(String username, String password) {
        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withAuthFlow("USER_PASSWORD_AUTH")
                .withClientId("YourClientId")
                .withAuthParameters(
                        Map.of(
                                "USERNAME", username,
                                "PASSWORD", password
                        )
                );

        try {
            InitiateAuthResult authResult = cognitoIdentityProvider.initiateAuth(authRequest);
            AuthenticationResultType authResponse = authResult.getAuthenticationResult();


            String accessToken = authResponse.getAccessToken();
            String idToken = authResponse.getIdToken();
            String refreshToken = authResponse.getRefreshToken();

            // Construct a User object with the logged-in user's details and return it
            User loggedInUser = new User();
            loggedInUser.setUsername(username);


            return loggedInUser;

        } catch (Exception e) {
            // Handle login failure
            throw new RuntimeException("User login failed: " + e.getMessage(), e);
        }
    }

    public void confirmRegistration(String username, String confirmationCode) {
        ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest()
                .withClientId("YourClientId")
                .withUsername(username)
                .withConfirmationCode(confirmationCode);

        try {
            ConfirmSignUpResult confirmSignUpResult = cognitoIdentityProvider.confirmSignUp(confirmSignUpRequest);
            // Handle successful confirmation if needed
        } catch (Exception e) {
            throw new RuntimeException("User registration confirmation failed: " + e.getMessage(), e);
        }
    }
}
