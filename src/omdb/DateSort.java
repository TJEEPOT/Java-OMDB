/******************************************************************************

Project     : CMP-4008Y - Programming 1, Assignment 1:
              Off-line Movie Database in Java (OMDB).

File        : DateSort.java

Date        : Tuesday 4th November 2018

Author      : Martin Siddons

Description : This contains a Comparitor interface to sort ArrayLists by Date.
              Assistance taken from https://howtodoinjava.com/sort/
              sort-arraylist-objects-comparable-comparator/

History     : 04/11/2018 - v1.0 - Implemented Date Sort.

******************************************************************************/
package omdb;

import java.util.Comparator;

public class DateSort implements Comparator<Movie>
{
    @Override
    public int compare(Movie m1, Movie m2) // Define objects to be sorted.
    {
        return m1.getYear() - m2.getYear(); // comparison.
    }
}
