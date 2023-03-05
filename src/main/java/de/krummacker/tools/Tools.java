package de.krummacker.tools;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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
        SecureRandom random = new SecureRandom();
        List<Integer> result = new ArrayList<>(length);
        for (int i = 0; i < length; ++i) {
            int r = random.nextInt();
            result.add(r);
        }
        return result;
    }
}
