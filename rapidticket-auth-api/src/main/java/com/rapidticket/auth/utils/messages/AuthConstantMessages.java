package com.rapidticket.auth.utils.messages;

public class AuthConstantMessages {
    private AuthConstantMessages(){

    }

    // Success messages (User & Developer)
    public static final String UIM001 = "User successfully created.";
    public static final String DIM001 = "User record successfully inserted into the database.";

    public static final String UIM002 = "User login successfully.";
    public static final String DIM002 = "Successfully user login from the database.";

    // Error messages (User & Developer)
    public static final String UEM001 = "User not found.";
    public static final String DEM001 = "Database error: User is null or missing.";

    public static final String UEM002 = "Email already exists.";
    public static final String DEM002 = "Database constraint: Email with the given code already exists.";

    public static final String UEM004 = "Failed to create the user.";
    public static final String DEM004 = "Database error: Unable to create the user record.";

    public static final String UEM005 = "Failed to verify password.";
    public static final String DEM005 = "Database error: Unable to verify password.";

}
