package de.krummacker.jsorter;

public class MedianQuickSorterTest extends SorterBase {

    @Override
    protected Sorter<Integer> getSorter() {
        return new MedianQuickSorter<>();
    }
}