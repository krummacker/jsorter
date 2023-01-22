package de.krummacker.jsorter;

import java.util.List;

/**
 * A Sorter can sort Lists with objects. Classes that implement this interface usually use different sort algorithms
 * such as bubble sort, quick sort, merge sort and others.
 *
 * @param <T> The type of the objects to be sorted. Must implement the Comparable interface
 */
@FunctionalInterface
public interface Sorter<T extends Comparable<T>> {

    /**
     * Sorts the specified list and returns the result. Does not change the parameter.
     *
     * @param input the list to be sorted
     * @return a sorted list
     * @throws NullPointerException if the input parameter or one of the list elements is null
     */
    List<T> sort(List<T> input);
}