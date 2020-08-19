/******************************************************************************

Project     : CMP-4008Y - Programming 1, Assignment 1:
              Off-line Movie Database in Java (OMDB).

File        : OMDB.java

Date        : Friday 02nd November 2018

Author      : Martin Siddons

Description : This contains the main() method for the project which processes
              the requested database queries and outputs them to the screen.

History     : 16/11/2018 - v1.0 - Testbed code for ArrayLists.
              03/12/2018 - v1.1 - Began writing code to complete operations,
                                  moved ArrayLists into MovieDatabase object.
              04/12/2018 - v1.2 - Found need for further Classes to handle more
                                  forms of search. Rewriting code to use these.
              05/12/2018 - v1.3 - Finalised all required operations.

******************************************************************************/
package omdb;

public class OMDB 
{
    public static void main(String[] args)
    {   
        String sortBy,    // Valid values are "Date", "Duration" and "Rating"*.
               field,     // Valid values are "Title", "Year", "Cert", "Genre"
                          // "Duration" and "Rating".
               value,     // Value to search the given Database field for.
               thisMovie; // Name of the variable from the currently selected
                          // Movie object.
        
        
        // Operation 1.
        MovieDatabase db = new MovieDatabase(); // Create a new database.
        String file = "films.txt"; // Take this file                           
        db.load(file);             // then load & process it into the database.
        
        
        // Operation 2.
        sortBy = "Date";
        db.sort(sortBy); // Sort the database by Year first.
        System.out.println("--Sorting " + file + " into date order--\n");
        for (int i = 0; i < db.getSize(); i++)  // Loop through the sorted array
        {
            System.out.println(db.getEntry(i)); // and print every member.
        }
        
        
        // Operation 3.
        field = "Genre";     // Search the Genre variable on all movie objects
        value = "Film-Noir"; // for the value "Film-Noir"
        
        MovieDatabase noirDB = new MovieDatabase();
        // Search the Database and copy the found movies into a new database.
        noirDB.buildQuery(field, value, db); 
        
        // Sort ArrayList by movie length.
        sortBy = "Duration";
        noirDB.sort(sortBy);
        
        // Find and print the movie name third from the end.
        thisMovie = noirDB
                    .getEntry(noirDB
                    .getSize() - 3)
                    .getTitle();
        System.out.println("\nThe third longest Film-Noir is: " + thisMovie);
        
        // We won't need this database anymore, mark it for garbage collection.
        noirDB = null;
        
        
        // Operation 4.
        field = "Cert";    // Search for certificate
        value = "UNRATED"; // of UNRATED.
        MovieDatabase unratedDB = new MovieDatabase(); // Build the DB
        unratedDB.buildQuery(field, value, db); // and fill it with the query.
        
        sortBy = "Date";
        unratedDB.sort(sortBy); // Sort it.
        
        // Find and print the movie eighth from the end.
        thisMovie = unratedDB
                    .getEntry(unratedDB
                    .getSize() - 8)
                    .getTitle();
        System.out.println("\nThe eighth most recent \"UNRATED\" movie is: " 
                            + thisMovie);
        
        unratedDB = null; // Garbage day.
        
        
        // Operation 5.
        field = "Title";
        MovieDatabase longTitleDB = new MovieDatabase();
        longTitleDB.LongestString(db, field); // Look for the longest title.
        // This isn't the cleanest or most efficient implementation, but it
        // does make use of existing (Static) methods built for MovieDatabase 
        // objects, and can return other values from the Movie object if needed.
        
        // Grab the last value from the above database, which is the longest.
        thisMovie = longTitleDB
                    .getEntry(longTitleDB
                    .getSize() - 1)
                    .getTitle();
        System.out.println("\nThe film with the longest name is: " 
                            + thisMovie);
        
        longTitleDB = null; // Might as well garbage this too. Good practice.
    }
}
