package de.krummacker.jsorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sorts Lists using the quick sort algorithm. This implementation does not create any new objects. However the add()
 * and remove() methods are very slow and cause bad performance.
 *
 * @param <T> the type of objects to be sorted
 */
public class RemoveAddQuickSorter<T extends Comparable<T>> implements Sorter<T> {

    /**
     * Creates a new QuickSorter.
     */
    public RemoveAddQuickSorter() {
        // Intentionally left empty
    }

    /**
     * If we want to reduce object creation then we have to work on sub lists of the original list.
     *
     * @param list  the list that contains the sublist
     * @param start the index of the first element of the sublist
     * @param end   the index of the last element of the sublist
     */
    private void sort(List<T> list, int start, int end) {

        if (start >= end) {
            // list is empty or has only 1 element = already sorted
            return;
        }

        int pivotIndex = start;
        T pivot = list.get(pivotIndex);
        for (int i = start + 1; i <= end; ++i) {
            T current = list.get(i);
            if (current.compareTo(pivot) < 0) {

                // These are slow.
                //noinspection SuspiciousListRemoveInLoop
                list.remove(i);
                list.add(start, current);

                ++pivotIndex;
            }
        }

        sort(list, start, pivotIndex - 1);
        sort(list, pivotIndex + 1, end);
    }

    @Override
    public List<T> sort(List<T> input) {

        // The provided list may be immutable and not support add nor remove, hence creating a copy.
        List<T> copy = new ArrayList<>(input);
        sort(copy, 0, input.size() - 1);
        return copy;
    }
}
