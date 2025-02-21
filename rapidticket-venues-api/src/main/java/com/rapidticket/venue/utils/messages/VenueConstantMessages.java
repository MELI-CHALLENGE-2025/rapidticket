package com.rapidticket.venue.utils.messages;

public class VenueConstantMessages {
    private VenueConstantMessages(){

    }

    // Success messages (User & Developer)
    public static final String UIM001 = "Venue successfully created.";
    public static final String DIM001 = "Venue record successfully inserted into the database.";

    public static final String UIM002 = "Venue list retrieved successfully.";
    public static final String DIM002 = "Successfully fetched the list of venues from the database.";

    public static final String UIM003 = "Venue details retrieved successfully.";
    public static final String DIM003 = "Venue details successfully fetched by code.";

    public static final String UIM004 = "Venue updated successfully.";
    public static final String DIM004 = "Venue record successfully updated in the database.";

    public static final String UIM005 = "Venue deleted successfully.";
    public static final String DIM005 = "Venue record successfully removed from the database.";

    // Error messages (User & Developer)
    public static final String UEM001 = "Venue not found.";
    public static final String DEM001 = "Database error: Venue is null or missing.";

    public static final String UEM002 = "Venue already exists.";
    public static final String DEM002 = "Database constraint: Venue with the given code already exists.";

    public static final String UEM003 = "Venue not found.";
    public static final String DEM003 = "No venue record found for the provided code.";

    public static final String UEM004 = "Failed to create the venue.";
    public static final String DEM004 = "Database error: Unable to create the venue record.";

    public static final String UEM005 = "Failed to update the venue.";
    public static final String DEM005 = "Database error: Unable to update the venue record.";

    public static final String UEM006 = "Failed to delete the venue.";
    public static final String DEM006 = "Database error: Unable to delete the venue record.";

}
