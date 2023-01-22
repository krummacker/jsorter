package de.krummacker.jsorter;

public class RandomPivotQuickSorterTest extends SorterBase {

    @Override
    protected Sorter<Integer> getSorter() {
        return new RandomPivotQuickSorter<>();
    }
}