package com.rapidticket.show.utils.messages;

public class ShowConstantMessages {

    private ShowConstantMessages(){

    }

    // Success messages (User & Developer)
    public static final String UIM001 = "Show successfully created.";
    public static final String DIM001 = "Show record successfully inserted into the database.";

    public static final String UIM002 = "Show list retrieved successfully.";
    public static final String DIM002 = "Successfully fetched the list of shows from the database.";

    public static final String UIM003 = "Show details retrieved successfully.";
    public static final String DIM003 = "Show details successfully fetched by code.";

    public static final String UIM004 = "Show updated successfully.";
    public static final String DIM004 = "Show record successfully updated in the database.";

    public static final String UIM005 = "Show deleted successfully.";
    public static final String DIM005 = "Show record successfully removed from the database.";

    // Error messages (User & Developer)
    public static final String UEM001 = "Show not found.";
    public static final String DEM001 = "Database error: Show is null or missing.";

    public static final String UEM002 = "Show already exists.";
    public static final String DEM002 = "Database constraint: Show with the given code already exists.";

    public static final String UEM003 = "Show not found.";
    public static final String DEM003 = "No Show record found for the provided code.";

    public static final String UEM004 = "Failed to create the show.";
    public static final String DEM004 = "Database error: Unable to create the show record.";

    public static final String UEM005 = "Failed to update the show.";
    public static final String DEM005 = "Database error: Unable to update the show record.";

    public static final String UEM006 = "Failed to delete the show.";
    public static final String DEM006 = "Database error: Unable to delete the show record.";

}
