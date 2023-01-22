package de.krummacker.jsorter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sorts Lists using the quick sort algorithm.
 *
 * @param <T> the type of objects to be sorted
 */
public class QuickSorter<T extends Comparable<T>> implements Sorter<T> {

    /**
     * Creates a new QuickSorter.
     */
    public QuickSorter() {
        // Intentionally left empty
    }

    @Override
    public List<T> sort(List<T> input) {

        if (input.size() < 2) {
            // already sorted
            return input;
        }

        Iterator<T> iterator = input.iterator();
        T pivot = iterator.next();

        // This implementation creates new lists in every step but this has turned out to be quicker than using the
        // add() and remove() methods in ArrayList.
        List<T> smaller = new ArrayList<>();
        List<T> bigger = new ArrayList<>();

        while (iterator.hasNext()) {
            T c = iterator.next();
            if (c.compareTo(pivot) < 0) {
                smaller.add(c);
            } else {
                bigger.add(c);
            }
        }

        List<T> first = sort(smaller);
        List<T> last = sort(bigger);

        List<T> result = new ArrayList<>(first);
        result.add(pivot);
        result.addAll(last);
        return result;
    }
}
