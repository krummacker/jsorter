package de.krummacker.jsorter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Sorts Lists using the quick sort algorithm. It uses multiple threads to get the work done faster. This speedup is
 * especially visible for lists from 50,000 to 8,000,000 elements. Smaller and interestingly also larger lists are
 * better handled by a single-threaded sorter.
 *
 * @param <T> the type of objects to be sorted
 */
public class MultithreadedQuickSorter<T extends Comparable<T>> extends MedianQuickSorter<T> {

    /**
     * Through testing this has turned out to be a reasonable number. A higher value would increase the number of
     * threads and the CPU usage but there is no measurable speed improvement.
     */
    private final int threadCreationThreshold = Runtime.getRuntime().availableProcessors() / 2;

    /**
     * Creates a new MultithreadedQuickSorter.
     */
    public MultithreadedQuickSorter() {
        // Intentionally left empty
    }

    @Override
    public List<T> sort(List<T> input) {
        ExecutorService executorService = Executors.newWorkStealingPool();
        try {
            return sort(input, 0, executorService);
        } finally {
            executorService.shutdownNow();
        }
    }

    /**
     * Internal sort method that keeps track of the nesting level ("deepness").
     *
     * @param input    the list to be sorted
     * @param deepness the nesting level that we are in
     * @return the sorted list
     */
    private List<T> sort(List<T> input, int deepness, ExecutorService executorService) {

        if (input.size() < 2) {
            // already sorted
            return input;
        }

        PivotSplitter pivotSplitter = new PivotSplitter(input).invoke();

        List<T> smaller = pivotSplitter.getSmaller();
        List<T> bigger = pivotSplitter.getBigger();

        List<T> first;
        List<T> last;

        if (deepness < threadCreationThreshold) {

            Future<List<T>> firstFuture = executorService.submit(() -> sort(smaller, deepness + 1, executorService));
            Future<List<T>> lastFuture = executorService.submit(() -> sort(bigger, deepness + 1, executorService));

            try {
                first = firstFuture.get();
                last = lastFuture.get();
            } catch (InterruptedException e) {
                firstFuture.cancel(true);
                lastFuture.cancel(true);
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Sorting was interrupted", e);
            } catch (ExecutionException e) {
                firstFuture.cancel(true);
                lastFuture.cancel(true);
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException runtimeException) {
                    throw runtimeException;
                }
                if (cause instanceof Error error) {
                    throw error;
                }
                throw new IllegalStateException("A sorting task failed", cause);
            }
        } else {
            first = sort(smaller, deepness + 1, executorService);
            last = sort(bigger, deepness + 1, executorService);
        }

        List<T> result = new ArrayList<>(input.size());
        result.addAll(first);
        result.add(pivotSplitter.getPivot());
        result.addAll(last);
        return result;
    }
}
