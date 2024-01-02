package de.krummacker.jsorter;

import de.krummacker.tools.Tools;

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
            System.out.printf("%7d", count);
            for (Sorter<Integer> sorter : sorters) {
                List<Integer> input = Tools.createRandomList(count);

                long before = System.nanoTime();
                List<Integer> sorted = Collections.emptyList();
                boolean skipped;  // 'false' by default
                if ((count > 10000 && sorter instanceof RandomPivotQuickSorter) ||
                        (count > 5000 && sorter instanceof BubbleSorter) ||
                        (count > 10000 && sorter instanceof RemoveAddQuickSorter)) {
                    skipped = true;
                } else {
                    sorted = sorter.sort(input);
                    skipped = false;
                }
                long after = System.nanoTime();
                if (skipped) {
                    System.out.print("       -");
                } else {
                    System.out.printf("%8d", (after - before) / 1000);
                }

                before = System.nanoTime();
                if ((count > 5000 && sorter instanceof QuickSorter) ||
                        (count > 10000 && sorter instanceof RemoveAddQuickSorter) ||
                        (count > 10000 && sorter instanceof RandomPivotQuickSorter) ||
                        (count > 5000 && sorter instanceof BubbleSorter)) {
                    skipped = true;
                } else {
                    sorter.sort(sorted);
                    // keep skipped = false
                }
                after = System.nanoTime();
                if (skipped) {
                    System.out.print("       -");
                } else {
                    System.out.printf("%8d", (after - before) / 1000);
                }
            }
            System.out.println();
        }
    }
}
