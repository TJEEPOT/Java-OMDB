/******************************************************************************

Project     : CMP-4008Y - Programming 1, Assignment 1:
              Off-line Movie Database in Java (OMDB).

File        : Movie.java

Date        : Friday 02nd November 2018

Author      : Martin Siddons

Description : Class which provides constructors for building objects which 
              hold information for a particular film which are specified in 
              MovieDatabase.java.

History     : 02/11/2018 - v1.0 - Initial setup, added constructors and ToString
              09/11/2018 - v1.1 - Added Comparable interface
              13/11/2018 - v1.1.1 Added DecimalFormat, adjusted output to match.
              04/12/2018 - v1.2 - Removed Comparable interface and implemented
                                  Comparitor interface instead, which is easier
                                  to expand on in the future (See DateSort.java,
                                  DurationSort.java and RatingSort.java.)

******************************************************************************/
package omdb;

import java.text.DecimalFormat;

public class Movie // implements Comparable<Movie>
{
    private final String title;
    private final int year;
    private final String cert;
    private final String genre;
    private final int duration;
    private final double rating;
    
    DecimalFormat fmt = new DecimalFormat("0.###");
    // Up to 3DP in precision for the rating, more would be excessive.
    
    // Default constructor
    public Movie()
    {
        title = "null";
        year = 0;
        cert = "null";
        genre = "null";
        duration = 0;
        rating = 0.0;
    }
    
    
    // Main constructor
    public Movie(String title, int year, String cert, String genre, 
            int duration, double rating)
    {
        this.title = title;
        this.year = year;
        this.cert = cert;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
    }
    
    
    // Accessor methods
    public String getTitle()
    {
        return title;
    }
    
    public int getYear()
    {
        return year;
    }
    
    public String getCert()
    {
        return cert;
    }
    
    public String getGenre()
    {
        return genre;
    }
    
    public int getDuration()
    {
        return duration;
    }
        
    public double getRating()
    { // As DecimalFormat outputs as String, this must cast to Double.
        double rate = Double.parseDouble(fmt.format(rating));
        return rate;
    }
    

    // Output string
    @Override
    public String toString()
    {   // returns the data in exactly same format as the reference file.
        return "\"" + title + 
               "\"," + year + 
               ",\"" + cert + 
               "\",\"" + genre + 
               "\"," + duration + 
               "," + fmt.format(rating); 
    }
    
    
//    // Comparable interface
//    @Override
//    public int compareTo(Movie m)
//    {
//        return Integer.compare(this.year, m.year);
//    }

    // Test harness
    public static void main(String[] args) 
    {
        Movie m1 = new Movie();
        Movie m2 = new Movie("test",2018,"18","Action",120,1.1);
        Movie m3 = new Movie("Indiana Jones and the Last Crusade",1989,"PG-13",
                             "Action/Adventure/Fantasy",127,0);
        
        System.out.println("Default: " + m1);
        System.out.println("Basic: " + m2);
        System.out.println("Actual entry: " + m3);
        
        // Accessor methods test
        System.out.println("\nTitle: " + m3.getTitle());
        System.out.println("Year: " + m3.getYear());
        System.out.println("Certificate: " + m3.getCert());
        System.out.println("Genre: " + m3.getGenre());
        System.out.println("Duration: " + m3.getDuration());
        System.out.println("Rating: " + m3.getRating());        
    }
}
