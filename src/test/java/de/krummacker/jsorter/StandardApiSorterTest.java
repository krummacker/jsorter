package de.krummacker.jsorter;

public class StandardApiSorterTest extends SorterBase {

    @Override
    protected Sorter<Integer> getSorter() {
        return new StandardApiSorter<>();
    }
}