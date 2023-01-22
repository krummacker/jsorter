package de.krummacker.jsorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sorts Lists using the quick sort algorithm. It picks a pivot based on the median of the first, the last and the
 * middle element.
 *
 * @param <T> the type of objects to be sorted
 */
public class MedianQuickSorter<T extends Comparable<T>> implements Sorter<T> {

    /**
     * Creates a new MedianQuickSorter.
     */
    public MedianQuickSorter() {
        // Intentionally left empty
    }

    @Override
    public List<T> sort(List<T> input) {

        if (input.size() < 2) {
            // already sorted
            return input;
        }

        PivotSplitter pivotSplitter = new PivotSplitter(input).invoke();

        List<T> first = sort(pivotSplitter.getSmaller());
        List<T> last = sort(pivotSplitter.getBigger());

        List<T> result = new ArrayList<>(first);
        result.add(pivotSplitter.getPivot());
        result.addAll(last);
        return result;
    }

    /**
     * A method object that, after having been invoked, can provide a pivot value, a list of smaller and a list of
     * larger items. This class is only to be used by subclasses of this class that are in the same package.
     */
    class PivotSplitter {

        private final List<T> input;
        private T pivot;
        private List<T> smaller;
        private List<T> bigger;

        /**
         * Builds a method object using the provided list as an argument.
         */
        PivotSplitter(List<T> input) {
            this.input = input;
        }

        T getPivot() {
            return pivot;
        }

        List<T> getSmaller() {
            return smaller;
        }

        List<T> getBigger() {
            return bigger;
        }

        /**
         * Invokes this method object.
         *
         * @return the method object itself to allow chaining.
         */
        PivotSplitter invoke() {

            int pivotIndex = determinePivotIndex(input);
            pivot = input.get(pivotIndex);

            // This implementation creates new lists in every step but this has turned out to be quicker than using the
            // add() and remove() methods in ArrayList. Let's make each sub list as big as the input list, just in case.
            // This way we avoid rebuilding the internal array.
            smaller = new ArrayList<>(input.size());
            bigger = new ArrayList<>(input.size());

            // This is a very old-fashioned way to iterate a list but this allows us to skip the pivot element.
            for (int i = 0; i < input.size(); ++i) {
                if (i == pivotIndex) {
                    continue;
                }
                T element = input.get(i);
                if (element.compareTo(pivot) < 0) {
                    smaller.add(element);
                } else {
                    bigger.add(element);
                }
            }

            return this;
        }

        /**
         * Determines the index of the best pivot for the specified list. It is the median of the first, middle and last
         * element of the list.
         *
         * @param input the list of which the best pivot index should be computed
         * @return the index of the pivot
         */
        private int determinePivotIndex(List<T> input) {

            int startIndex = 0;
            int middleIndex = input.size() / 2;
            int endIndex = input.size() - 1;

            T a = input.get(startIndex);
            T b = input.get(middleIndex);
            T c = input.get(endIndex);

            if (a.compareTo(b) > 0) {
                if (b.compareTo(c) > 0) {
                    return middleIndex;
                } else {
                    return a.compareTo(c) > 0 ? endIndex : startIndex;
                }
            } else {
                if (b.compareTo(c) < 0) {
                    return middleIndex;
                } else {
                    return a.compareTo(c) < 0 ? endIndex : startIndex;
                }
            }
        }
    }
}
