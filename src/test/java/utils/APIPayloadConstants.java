package utils;

import org.json.JSONObject;

public class APIPayloadConstants {

    /**
     * Creates a JSON payload for user creation.
     *
     * @param name     User's name
     * @param email    User's email
     * @param password User's password
     * @return JSON string for the request payload
     */
    public static String createUser(String name, String email, String password) {
        return createJsonPayload(new String[]{"name", "email", "password"},
                new Object[]{name, email, password});
    }

    /**
     * Creates a JSON payload for token generation.
     *
     * @param email    User's email
     * @param password User's password
     * @return JSON string for the request payload
     */
    public static String generateTokenPayload(String email, String password) {
        return createJsonPayload(new String[]{"email", "password"},
                new Object[]{email, password});
    }

    /**
     * Creates a JSON payload for employee creation with dynamic fields.
     *
     * @param emp_firstname  Employee's first name
     * @param emp_lastname   Employee's last name
     * @param emp_middle_name Employee's middle name
     * @param emp_gender     Employee's gender
     * @param emp_birthday   Employee's birthday
     * @param emp_status     Employee's status
     * @param emp_job_title  Employee's job title
     * @return JSON string for the request payload
     */
    public static String createEmployeeJsonPayloadDynamic(String emp_firstname,
                                                          String emp_lastname,
                                                          String emp_middle_name,
                                                          String emp_gender,
                                                          String emp_birthday,
                                                          String emp_status,
                                                          String emp_job_title) {
        return createJsonPayload(new String[]{"emp_firstname", "emp_lastname", "emp_middle_name", "emp_gender",
                        "emp_birthday", "emp_status", "emp_job_title"},
                new Object[]{emp_firstname, emp_lastname, emp_middle_name, emp_gender,
                        emp_birthday, emp_status, emp_job_title});
    }

    /**
     * Utility method to create JSON payload from arrays of keys and values.
     *
     * @param keys   Array of JSON keys
     * @param values Array of corresponding JSON values
     * @return JSON string
     */
    private static String createJsonPayload(String[] keys, Object[] values) {
        if (keys.length != values.length) {
            throw new IllegalArgumentException("Keys and values arrays must have the same length");
        }
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keys.length; i++) {
            jsonObject.put(keys[i], values[i]);
        }
        return jsonObject.toString();
    }
}
