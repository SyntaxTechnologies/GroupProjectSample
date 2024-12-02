package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIConstants {

    // Authentication Token
    public static String token;

    public static final String TOKEN_FILE = "token.txt";

    // Base URL
    public static final String BASE_URL = "http://hrm.syntaxtechs.net/syntaxapi/api";

    // Request and Response Objects
    public static Response response;
    public static RequestSpecification request;

    // API Endpoints
    public static final String CREATE_EMPLOYEE = BASE_URL + "/createEmployee.php";
    public static final String GENERATE_TOKEN = BASE_URL + "/generateToken.php";
    public static final String GET_ONE_EMPLOYEE = BASE_URL + "/getOneEmployee.php";
    public static final String UPDATE_EMPLOYEE = BASE_URL + "/updateEmployee.php";
    public static final String DELETE_EMPLOYEE = BASE_URL + "/deleteEmployee.php";
    public static final String CREATE_USER = BASE_URL + "/createUser.php";

    // Header Keys and Values
    public static final String HEADER_CONTENT_TYPE_KEY = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_VALUE = "application/json";
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";

    // Configuration File Path
    public static final String CONFIG_FILE_PATH = "src/resources/config/config.properties";

    // Static Block Explanation:
// -------------------------
// The static block is a special block in Java that is executed when the class is first loaded into memory.
// It runs only once, even if the class is referenced multiple times during the program's execution.
// In this static block, we initialize the RestAssured.baseURI to the base URL of the API.
// - Compared to assigning RestAssured.baseURI directly in a static variable declaration, the static block
//   makes the initialization process more explicit and easier to enhance in the future.
// - It separates initialization logic from variable declarations, improving code maintainability and clarity.


    // Static Block for Base URI Initialization
    static {
        RestAssured.baseURI = BASE_URL;
    }




}
