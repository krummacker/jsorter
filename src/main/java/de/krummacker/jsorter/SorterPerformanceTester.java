package de.krummacker.jsorter;

import de.krummacker.tools.Tools;
import org.apache.commons.cli.*;

import java.io.PrintStream;
import java.util.Arrays;
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
        executeApplication(args, System.out);
    }

    /**
     * Executes this application and writes any output into the specified PrintStream.
     *
     * @param args   the command line arguments
     * @param output where to write the output to
     */
    static void executeApplication(String[] args, PrintStream output) {
        Options options = defineOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            int max = cmd.hasOption('m') ? Integer.parseInt(cmd.getOptionValue('m')) : 7000;
            int step = cmd.hasOption('s') ? Integer.parseInt(cmd.getOptionValue('s')) : 1000;
            runPerformanceTests(max, step, output);
        } catch (ParseException e) {
            output.println("Invalid command line arguments: " + e.getMessage());
        }
    }

    /**
     * Returns an Options instance that describes which command line options this tool has.
     *
     * @return the Options instance
     */
    private static Options defineOptions() {
        Options options = new Options();
        options.addOption("m", "max", true, "specifies the maximum number of elements");
        options.addOption("s", "step", true, "specifies the step width for increasing the number of elements");
        return options;
    }

    /**
     * Runs the actual performance tests.
     *
     * @param max    the maximum number of elements to sort
     * @param step   the step size for increasing the number of elements
     * @param output where to write the output to
     */
    private static void runPerformanceTests(int max, int step, PrintStream output) {

        // Sorters are ordered by resilience against stack overflow errors and then performance.
        List<Sorter<Integer>> sorters = Arrays.asList(
                new RemoveAddQuickSorter<Integer>(),
                new QuickSorter<Integer>(),
                new RandomPivotQuickSorter<Integer>(),
                new BubbleSorter<Integer>(),
                new MedianQuickSorter<Integer>(),
                new MultithreadedQuickSorter<Integer>(),
                new StandardApiSorter<Integer>());

        for (int i = step; i <= max; i += step) {
            output.print("Number of elements: " + i + "; ");
            for (Sorter<Integer> sorter : sorters) {
                List<Integer> input = Tools.createRandomList(i);

                long before = System.currentTimeMillis();
                List<Integer> sorted = sorter.sort(input);
                long after = System.currentTimeMillis();
                output.print("Random: " + sorter.getClass().getSimpleName() + " in ms: " + (after - before) + "; ");

                before = System.currentTimeMillis();
                sorter.sort(sorted);
                after = System.currentTimeMillis();
                output.print("Sorted: " + sorter.getClass().getSimpleName() + " in ms: " + (after - before) + "; ");
            }
            output.println();
        }
    }
}
