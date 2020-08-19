/******************************************************************************

Project     : CMP-4008Y - Programming 1, Assignment 1:
              Off-line Movie Database in Java (OMDB).

File        : DateSort.java

Date        : Tuesday 4th November 2018

Author      : Martin Siddons

Description : This contains a Comparitor interface to sort ArrayLists by 
              Rating. Assistance taken from https://howtodoinjava.com/sort/
              sort-arraylist-objects-comparable-comparator/
                
              Although this isn't used in the program, it is a feature that I
              can see being very useful in the future so I've included it now.

History     : 05/11/2018 - v1.0 - Implemented Rating Sort.

******************************************************************************/
package omdb;

import java.util.Comparator;

public class RatingSort implements Comparator<Movie>
{
    @Override
    public int compare(Movie m1, Movie m2) // Define objects to be sorted.
    {
        // Additional assistance from https://stackoverflow.com/questions/
        // 4242023/comparator-with-double-type
        if (m1.getRating() < m2.getRating()) 
        {
            return -1;
        }
        else if (m1.getRating() > m2.getRating())
        {
            return 1;
        }
        return 0;
    }
}