package es.wacoco.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import es.wacoco.Config.CognitoConfig;
import es.wacoco.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AWSCognitoIdentityProvider cognitoIdentityProvider;

    @Autowired
    private CognitoConfig cognitoConfig;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-secret}")
    private String clientSecret;

    public User loginUser(String username, String password) {
        // Beräkna SECRET_HASH
        String secretHash = cognitoConfig.calculateSecretHash(username, clientId, clientSecret);

        // Förbered anrop till Cognito
        HashMap<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", username);
        authParams.put("PASSWORD", password);
        authParams.put("SECRET_HASH", secretHash);

        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withAuthFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .withClientId(clientId)
                .withAuthParameters(authParams);

        try {
            InitiateAuthResult authResult = cognitoIdentityProvider.initiateAuth(authRequest);
            AuthenticationResultType authResponse = authResult.getAuthenticationResult();

            // Construct a User object with the logged-in user's details
            User loggedInUser = new User();
            loggedInUser.setUsername(username);
            // Optionally store tokens or other necessary information in the User object

            return loggedInUser; // Return the User object
        } catch (Exception e) {
            logger.error("User login failed: {}", e.getMessage());
            throw new RuntimeException("User login failed: " + e.getMessage(), e);
        }}public boolean confirmUserRegistration(String username, String confirmationCode) {
        ConfirmSignUpRequest confirmRequest = new ConfirmSignUpRequest()
                .withClientId(clientId) // Använd ditt Cognito App Client ID
                .withUsername(username)
                .withConfirmationCode(confirmationCode);
        try {
            ConfirmSignUpResult confirmSignUpResult = cognitoIdentityProvider.confirmSignUp(confirmRequest);
            return true; // Antag att registreringen är bekräftad om inga undantag kastas
        } catch (Exception e) {
            logger.error("An error occurred while confirming user registration: {}", e.getMessage());
            return false; // Något gick fel, antag att bekräftelsen misslyckades
        }
    }


}
