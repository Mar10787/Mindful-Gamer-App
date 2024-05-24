package com.example.mindfulgamer.util;

import java.util.Comparator;

public class CustomComparator implements Comparator<String> {
    /**
     * Algorithm used to create the custom comparator, uses subtraction to get a value and from there orders
     * with the given numerical result
     * @param s1: String of the first object to be compared.
     * @param s2: String the second object to be compared.
     * @return number: A int of the result of the subtractions used for sorting
     */
    @Override
    public int compare(String s1, String s2) {
        return getPriority(s1) - getPriority(s2);
    }

    /**
     * Method is an algorithm that is used to create a custom sort for priority list, grabs numerical values
     * for priority type
     * @param value: A string of the priority type
     * @return number: A int representation of the priority type
     */
    private int getPriority(String value) {
        return switch (value) {
            case "High" -> 1;
            case "Medium" -> 2;
            case "Low" -> 3;
            default -> Integer.MAX_VALUE;
        };
    }
}
