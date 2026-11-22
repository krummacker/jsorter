package de.krummacker.jsorter;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class RandomPivotQuickSorterTest extends SorterBase {

    @Override
    protected Sorter<Integer> getSorter() {
        return new RandomPivotQuickSorter<>();
    }

    @Test
    public void testSortPreservesDuplicateValues() {

        List<Integer> input = Arrays.asList(2, 1, 2, 1, 2);
        List<Integer> expected = Arrays.asList(1, 1, 2, 2, 2);

        Assert.assertEquals(getSorter().sort(input), expected);
    }
}
