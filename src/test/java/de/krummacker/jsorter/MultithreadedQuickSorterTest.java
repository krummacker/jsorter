package de.krummacker.jsorter;

public class MultithreadedQuickSorterTest extends SorterBase {

    @Override
    protected Sorter<Integer> getSorter() {
        return new MultithreadedQuickSorter<>();
    }
}