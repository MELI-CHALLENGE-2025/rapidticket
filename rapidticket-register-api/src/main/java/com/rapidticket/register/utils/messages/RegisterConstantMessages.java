package com.rapidticket.register.utils.messages;

public class RegisterConstantMessages {
    private RegisterConstantMessages(){

    }

    // Success messages (User & Developer)
    public static final String UIM001 = "User successfully created.";
    public static final String DIM001 = "User record successfully inserted into the database.";

    // Error messages (User & Developer)
    public static final String UEM002 = "Email already exists.";
    public static final String DEM002 = "Database constraint: Email with the given code already exists.";

    public static final String UEM004 = "Failed to create the user.";
    public static final String DEM004 = "Database error: Unable to create the user record.";

}
