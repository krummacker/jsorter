package de.krummacker.jsorter;

import de.krummacker.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Main class for performance testing various sorters.
 */
public class SorterPerformanceTester {

    /**
     * Set to private in order to prevent instantiation.
     */
    private SorterPerformanceTester() {
        // Intentionally left empty
    }

    /**
     * Called by the VM on program start.
     *
     * @param args the arguments specified on the command line
     */
    public static void main(String[] args) {

        // Sorters are ordered by resilience against stack overflow errors and then performance.
        List<Sorter<Integer>> sorters = Arrays.asList(
                new RemoveAddQuickSorter<>(),
                new QuickSorter<>(),
                new RandomPivotQuickSorter<>(),
                new BubbleSorter<>(),
                new MedianQuickSorter<>(),
                new MultithreadedQuickSorter<>(),
                new StandardApiSorter<>());

        List<Integer> counts = Arrays.asList(10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000);

        System.out.println("  Count      Remove/Add       QuickSort     RandomPivot      BubbleSort     MedianQuick   Multithreaded     StandardAPI");
        System.out.println("         random  sorted  random  sorted  random  sorted  random  sorted  random  sorted  random  sorted  random  sorted");
        for (int count : counts) {
            List<Integer> randomInput = Tools.createRandomList(count);
            List<Integer> sortedInput = new ArrayList<>(randomInput);
            Collections.sort(sortedInput);

            System.out.printf("%7d", count);
            for (Sorter<Integer> sorter : sorters) {
                boolean skipRandom = (count > 10000 && sorter instanceof RandomPivotQuickSorter) ||
                        (count > 5000 && sorter instanceof BubbleSorter) ||
                        (count > 10000 && sorter instanceof RemoveAddQuickSorter);
                if (skipRandom) {
                    System.out.print("       -");
                } else {
                    List<Integer> input = new ArrayList<>(randomInput);
                    long before = System.nanoTime();
                    sorter.sort(input);
                    long after = System.nanoTime();
                    System.out.printf("%8d", (after - before) / 1000);
                }

                boolean skipSorted = (count > 5000 && sorter instanceof QuickSorter) ||
                        (count > 10000 && sorter instanceof RemoveAddQuickSorter) ||
                        (count > 10000 && sorter instanceof RandomPivotQuickSorter) ||
                        (count > 5000 && sorter instanceof BubbleSorter);
                if (skipSorted) {
                    System.out.print("       -");
                } else {
                    List<Integer> input = new ArrayList<>(sortedInput);
                    long before = System.nanoTime();
                    sorter.sort(input);
                    long after = System.nanoTime();
                    System.out.printf("%8d", (after - before) / 1000);
                }
            }
            System.out.println();
        }
    }
}
