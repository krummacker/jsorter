package de.krummacker.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Contains miscellaneous tool functions.
 */
public class Tools {

    /**
     * Prevents accidental instantiation.
     */
    private Tools() {
    }

    /**
     * Creates a list of random int values. This method is meant to provide test data.
     *
     * @param length the size of the list to be created
     * @return the list with the random values
     */
    public static List<Integer> createRandomList(int length) {
        Random random = new Random();
        List<Integer> result = new ArrayList<>(length);
        for (int i = 0; i < length; ++i) {
            int r = random.nextInt();
            result.add(r);
        }
        return result;
    }

    /**
     * Determines the median of the 3 specified arguments, i.e. the element that is "in the middle".
     *
     * @param a the first element
     * @param b the second element
     * @param c the third element
     * @return the median, one of a, b, or c
     * @throws NullPointerException if any of the arguments is null
     */
    static <T extends Comparable<T>> T computeMedian(T a, T b, T c) {
        if (a.compareTo(b) > 0) {
            if (b.compareTo(c) > 0) {
                return b;
            } else {
                return a.compareTo(c) > 0 ? c : a;
            }
        } else {
            if (b.compareTo(c) < 0) {
                return b;
            } else {
                return a.compareTo(c) < 0 ? c : a;
            }
        }
    }
}
