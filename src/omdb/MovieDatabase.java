/******************************************************************************

Project     : CMP-4008Y - Programming 1, Assignment 1:
              Off-line Movie Database in Java (OMDB).

File        : MovieDatabase.java

Date        : Friday 02nd November 2018

Author      : Martin Siddons

Description : Class which provides methods for reading data from file and 
              answering database queries. 

History     : 02/11/2018 - v1.0 - Groundwork set, no content.
              21/11/2018 - v1.1 - Early work on code to load file and set AL.
              28/11/2018 - v1.2 - Successful loading of string into AL
                                  Rewrote file loading code to load entire file.
              02/12/2018 - v1.3 - Total rewrite splitting code into new  
                                  methods and optimising for reusability. Added
                                  accessor methods for OMDB.java.
              04/12/2018 - v1.4 - Changed sort() method to use Comparitor.
              05/12/2018 - v1.5 - Created findAccessor Static method, as code is
                                  used a couple times. Swapped throws for 
                                  try-catch block. Tidied up code.

******************************************************************************/
package omdb;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class MovieDatabase 
{
    private final ArrayList<Movie> movieAL;
    
    
    // Default constructor for the Arraylist.
    public MovieDatabase()
    {
        // Initialise the movie database.
        movieAL = new ArrayList<>();
    }
    
    
    // Return the size of the database.
    public int getSize()
    {
        return movieAL.size();
    }
    
    
    // Return the Movie object at the specified location in the database.
    // Utilises Movie.toString.
    public Movie getEntry(int a)
    {
        try
        {
            return movieAL.get(a);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("\nNo Movie Object found at that position in "
                             + "that database, Aborting.");
        }
        return movieAL.get(a);
    }
    
    
    // Allow external access to sort the Database.
    public void sort(String sortBy)
    {
        switch (sortBy) 
        {
            case "Date":
                Collections.sort(movieAL, new DateSort());
                break;
            case "Duration":
                Collections.sort(movieAL, new DurationSort());
                break;
            default:
                System.out.println("\nError: Unable to sort by " + sortBy);
                break;
        }
    }
       
    
    // Load method to bring data into the created ArrayList object.
    public ArrayList<Movie> load(String load) 
    {
        try // Looking for FileNotFoundException or IOException.
        {
            // Set up a stream to read in the data from specified file.
            BufferedReader in = new BufferedReader(
                new FileReader(load)); // Start a StreamReader for the file.

            // Read in the file line-by-line, creating objects in the database 
            // for each line. Loop concept taken from https://howtodoinjava.com/
            // java/io/how-to-read-file-in-java-bufferedreader-example/
            String thisLine;
            while ((thisLine = in.readLine()) != null)
            {
                // Build an array. This will hold the current line from the file
                // as well as that line split into different elements. This 
                // allows us to feed in an array into the Static Method rather 
                // than having an array here and another seperate one within the
                // static method. This means if you add further items to the 
                // film file in the future, you will only need to change the 
                // size of the below array.

                // Form the array big enough for the line plus 6 elements.
                String[] movieArray = new String[7];
                movieArray[0] = thisLine; // Read in the current line from file.

                // Feed in the line (within the array) into the Static method 
                // which will return the line plus elements of each entry.
                movieElements(movieArray);

                // Assign all elements from array into a new ArrayList object.
                String title = movieArray[1];
                int year = Integer.parseInt(movieArray[2]);
                String cert = movieArray[3];
                String genre = movieArray[4];
                int duration = Integer.parseInt(movieArray[5]);
                double rating = Double.parseDouble(movieArray[6]);
                movieAL.add(new Movie (title, year, cert, genre, 
                                       duration, rating));
            }
            in.close(); // Close the BufferedReader stream.
        }
        catch(FileNotFoundException e)
        {
            System.out.println("\nFile \"" + load + "\" Not Found, Aborting.");
        }
        catch (IOException e)
        {
            System.out.println("\nAn IO exception has occured, Aborting.");
        }
        return movieAL; // return the ArrayList (database) we created.
    }
    
    
    // Build an array of movie elements from the current read line, which 
    // can be used to form the variables in a database object.
    static String[] movieElements(String[] a)
    {
        // Set the position for where the first array element will be entered.
        int arrayPos = 1; 
        char thisChar; 
        
        // Initialise a StringBuilder to form strings to add to the array. 
        StringBuilder builder = new StringBuilder();
        
        // set up a flag to check if we're within quote marks.
        boolean inString  = false; 
        
        for (int i = 0; i < a[0].length(); i++)
        {
            thisChar = a[0].charAt(i); // load the current character.
            
            switch (thisChar) 
            {
                case '\"':
                    inString = !inString; // if quote detected, flip the flag.
                    break;
                    
                case ',':
                    if (inString)
                    {
                        // If we're in a quote, just add the comma to the string
                        builder.append(thisChar); 
                    }
                    
                    else
                    {
                        a[arrayPos] = builder.toString();
                        builder.setLength(0); // Clear the StringBuilder buffer.
                        arrayPos++; // Point the builder to the next element.
                    }   
                    break;
                    
                default:
                    builder.append(thisChar);
                    break;
            }
        }
        // Store whatever is left in the StringBuilder as a final array element.
        a[arrayPos] = builder.toString();
        return (a); // send the whole array back to the main program.
    }
    
    
    // Search for a list of films matching the given values and return them.
    public ArrayList<Movie> buildQuery(String field, String value, 
                                MovieDatabase searchDB)
    {
        try
        {
            // Iterate through the main database, looking for the given value.
            for (int i = 0; i < searchDB.getSize(); i++)
            {
                String fieldValue; // Field comparison variable.
                Movie m = searchDB.getEntry(i); // Current Movie object.

                // Find the required Movie accessor method from given field 
                // name then store the value in the variable above.
                fieldValue = findAccessor(m, field);

                // If the found value matches the one we're looking for, add 
                // that Movie object to the current database.
                Boolean match = false;
                match = fieldValue.contains(value); // Set "match" to true here. 
                if (match)
                {
                    movieAL.add(searchDB.getEntry(i));
                }            
            }
        }
        catch (NullPointerException e)
        {
            System.out.println("\nField \"" + field 
                             + "\" Not Found, Aborting.");
        }
        catch (Exception e)
        {
            System.out.println("\n Error in Input, Aborting");
        }
        return movieAL; // Return the newly complete database.
    }
    
    
    // Iterate through an ArrayList of objects to find the longest entry of a 
    // given field.
    public ArrayList<Movie> LongestString(MovieDatabase searchDB, String field)
    {
        String longest = null,     // The longest string of given field.
               fieldValue;  // The current value we're analysing.
        int maxLength = 0;  // Ronseal.
        
        // Iterate through the main database, finding the field given.
        for (int i = 0; i < searchDB.getSize(); i++)
        {
            // Check which accessor method we need and load the value it
            // represents into fieldValue.
            Movie m = searchDB.getEntry(i);
            fieldValue = findAccessor(m, field);
            
            if (fieldValue.length() > maxLength)
            {
                longest = fieldValue;
                maxLength = fieldValue.length();
                movieAL.add(searchDB.getEntry(i)); // add to the list anyway.
            }
        }
        return movieAL;
    }
        
    // Specialised method that will return the requested Movie object variable.
    static String findAccessor(Movie m, String field)
    {
        String s = null; // Field comparison variable.
        switch (field) 
        {
            case "Title":
                s = m.getTitle();
                break;
            case "Year":
                s = Integer.toString(m.getYear());
                break;
            case "Cert":
                s = m.getCert();
                break;
            case "Genre":
                s = m.getGenre();
                break;
            case "Length":
                s = Integer.toString(m.getDuration());
                break;
            case "Rating":
                s = Double.toString(m.getRating());
                break;
            default:
                System.out.println("\nError: Field Not Found in DB");
                break;
        }
        return s;
    }
            
    
    public static void main(String[] args)
    {
        // Test Harness
        MovieDatabase db = new MovieDatabase(); // Initialise the Database.
        db.load("films.txt");
        
        // Iterate and print all Movies in database.
        for (int i = 0; i < db.getSize(); i++)
        {
            System.out.println(db.getEntry(i));
        }
        
        // Sort database by year and print.
        System.out.println("\n--Sorting by Year--\n");
        String sortBy = "Date";
        db.sort(sortBy);
        for (int i = 0; i < db.getSize(); i++)
        {
            System.out.println(db.getEntry(i));
        }
    }
}