package de.krummacker.jsorter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sorts Lists using the quick sort algorithm. It picks a random pivot and thus should perform better on already sorted
 * lists.
 *
 * @param <T> the type of objects to be sorted
 */
public class RandomPivotQuickSorter<T extends Comparable<T>> implements Sorter<T> {

    private static Random random;

    /**
     * Creates a new RandomPivotQuickSorter.
     */
    public RandomPivotQuickSorter() {
        random = new Random();
    }

    @Override
    public List<T> sort(List<T> input) {

        if (input.size() < 2) {
            // already sorted
            return input;
        }

        int index = random.nextInt(input.size());
        T pivot = input.get(index);

        // This implementation creates new lists in every step but this has turned out to be quicker than using the
        // add() and remove() methods in ArrayList.
        List<T> smaller = new ArrayList<>();
        List<T> bigger = new ArrayList<>();

        for (T element : input) {
            if (element.compareTo(pivot) < 0) {
                smaller.add(element);
            } else {
                bigger.add(element);
            }
        }

        List<T> first = sort(smaller);
        List<T> last = sort(bigger);

        List<T> result = new ArrayList<>();
        result.addAll(first);
        result.addAll(last);
        return result;
    }
}
