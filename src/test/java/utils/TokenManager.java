package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.restassured.response.Response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class TokenManager {




    /**
     * Retrieves a valid token. If the token is missing, expired, or invalid, generates a new one.
     *
     * @param email    The email to use for token generation.
     * @param password The password to use for token generation.
     * @return A valid JWT Bearer token.
     */
    public static String getToken(String email, String password) {
        String token = readTokenFromFile();

        if (token == null || isTokenExpired(token)) {
            token = generateNewToken(email, password);
            saveTokenToFile(token);
        }

        return token;
    }

    /**
     * Reads the token from the file.
     *
     * @return The token if found and readable, otherwise null.
     */
    private static String readTokenFromFile() {
        try {
            if (Files.exists(Paths.get("token.txt"))) {
                String token = new String(Files.readAllBytes(Paths.get(APIConstants.TOKEN_FILE))).trim();
                System.out.println("Token read from file.");
                return token.isEmpty() ? null : token;
            } else {
                System.out.println("Token file does not exist. A new token will be generated.");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error reading token file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves the token to a file.
     *
     * @param token The token to save.
     */
    private static void saveTokenToFile(String token) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APIConstants.TOKEN_FILE))) {
            writer.write(token);
            System.out.println("Token saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving token to file: " + e.getMessage());
        }
    }

    /**
     * Checks if the given token is expired.
     *
     * @param token The JWT token.
     * @return True if the token is expired, false otherwise.
     */
    private static boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token.replace("Bearer ", ""));
            Date expiration = decodedJWT.getExpiresAt();
            boolean isExpired = expiration == null || expiration.before(new Date());
            if (isExpired) {
                System.out.println("Token is expired.");
            }
            return isExpired;
        } catch (Exception e) {
            System.out.println("Error decoding token: " + e.getMessage());
            return true; // Treat as expired if decoding fails
        }
    }

    /**
     * Generates a new token by making an API call.
     *
     * @param email    The email to use for authentication.
     * @param password The password to use for authentication.
     * @return The newly generated token.
     */
    private static String generateNewToken(String email, String password) {
        try {
            Response response = given()
                    .header("Content-Type", "application/json")
                    .body(String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password))
                    .when()
                    .post(APIConstants.GENERATE_TOKEN);

            if (response.getStatusCode() == 200) {
                String token = "Bearer " + response.jsonPath().getString("token");
                System.out.println("New token generated.");
                return token;
            } else {
                System.err.println("Failed to generate token. Response: " + response.getBody().asString());
                throw new RuntimeException("Failed to generate token. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating token: " + e.getMessage(), e);
        }
    }
}
