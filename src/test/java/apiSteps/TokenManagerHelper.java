package apiSteps;

import io.cucumber.java.en.Given;
import utils.APIConstants;
import utils.TokenManager;

public class TokenManagerHelper {

        @Given("a valid token is available for email {string} and password {string}")
        public void ensureValidToken(String email, String password) {
            // Retrieve or generate a valid token
            String token = TokenManager.getToken(email, password);

            // Store the token in a globally accessible variable
            APIConstants.token = token;

            // Log the token for debugging purposes
            System.out.println("Valid token retrieved and stored: " + token);
        }
    }


