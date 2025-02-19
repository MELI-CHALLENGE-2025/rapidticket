package com.rapidticket.show.utils;

public class FunctionConstantMessages {
    private FunctionConstantMessages(){

    }

    // Success messages (User & Developer)
    public static final String UIM001 = "Function successfully created.";
    public static final String DIM001 = "Function record successfully inserted into the database.";

    public static final String UIM002 = "Function list retrieved successfully.";
    public static final String DIM002 = "Successfully fetched the list of venues from the database.";

    public static final String UIM003 = "Function details retrieved successfully.";
    public static final String DIM003 = "Function details successfully fetched by code.";

    public static final String UIM004 = "Function updated successfully.";
    public static final String DIM004 = "Function record successfully updated in the database.";

    public static final String UIM005 = "Function deleted successfully.";
    public static final String DIM005 = "Function record successfully removed from the database.";

    // Error messages (User & Developer)
    public static final String UEM001 = "Function not found.";
    public static final String DEM001 = "Database error: Function is null or missing.";

    public static final String UEM002 = "Function already exists.";
    public static final String DEM002 = "Database constraint: Function with the given code already exists.";

    public static final String UEM003 = "Function not found.";
    public static final String DEM003 = "No function record found for the provided code.";

    public static final String UEM004 = "Failed to create the function.";
    public static final String DEM004 = "Database error: Unable to create the function record.";

    public static final String UEM005 = "Failed to update the function.";
    public static final String DEM005 = "Database error: Unable to update the function record.";

    public static final String UEM006 = "Failed to delete the function.";
    public static final String DEM006 = "Database error: Unable to delete the function record.";

}
