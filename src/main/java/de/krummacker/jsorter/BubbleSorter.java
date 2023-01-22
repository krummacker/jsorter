package de.krummacker.jsorter;

import java.util.List;

/**
 * Sorts Lists using the bubble sort algorithm. This implementation sorts in the specified list and does not create
 * any new objects, thus increasing performance.
 *
 * @param <T> the type of objects to be sorted
 */
public class BubbleSorter<T extends Comparable<T>> implements Sorter<T> {

    /**
     * Creates a new BubbleSorter.
     */
    public BubbleSorter() {
        // Intentionally left empty
    }

    @Override
    public List<T> sort(List<T> input) {
        for (int i = 0; i < input.size() - 1; ++i) {
            for (int j = 0; j < input.size() - 1 - i; ++j) {
                if (input.get(j).compareTo(input.get(j + 1)) > 0) {
                    T temp = input.get(j);
                    input.set(j, input.get(j + 1));
                    input.set(j + 1, temp);
                }
            }
        }
        return input;
    }
}
