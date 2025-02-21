package com.rapidticket.auth.utils.messages;

public class AuthConstantMessages {
    private AuthConstantMessages(){

    }

    // Success messages (User & Developer)
    public static final String UIM002 = "User login successfully.";
    public static final String DIM002 = "Successfully user login from the database.";

    // Error messages (User & Developer)
    public static final String UEM001 = "User not found.";
    public static final String DEM001 = "Database error: User is null or missing.";

    public static final String UEM005 = "Failed to verify password.";
    public static final String DEM005 = "Database error: Unable to verify password.";

}
