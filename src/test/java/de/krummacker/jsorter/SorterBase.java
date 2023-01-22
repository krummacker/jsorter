package de.krummacker.jsorter;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Make this class abstract so that the unit test runner does not start it unintentionally.
 */
public abstract class SorterBase {

    private Sorter<Integer> sorter;

    @BeforeMethod
    public void setUp() throws Exception {
        sorter = getSorter();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        sorter = null;
    }

    /**
     * Subclasses that test concrete Sorter implementations only need to implement this method and return an instance
     * of the Sorter.
     *
     * @return the Sorter to be tested
     */
    protected abstract Sorter<Integer> getSorter();

    @Test
    public void testSortHappyCase() throws Exception {

        List<Integer> before = Arrays.asList(2, 4, 7, 9, 3, 5, 1, 8, 6);
        List<Integer> after = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> result = sorter.sort(before);
        Assert.assertEquals(result, after);
    }

    @Test
    public void testSortAlreadySorted() throws Exception {

        List<Integer> before = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> after = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> result = sorter.sort(before);
        Assert.assertEquals(result, after);
    }

    @Test
    public void testSortEmptyList() throws Exception {

        List<Integer> before = Collections.emptyList();
        List<Integer> after = Collections.emptyList();

        List<Integer> result = sorter.sort(before);
        Assert.assertEquals(result, after);
    }

    @Test
    public void testSortListNull() throws Exception {

        try {
            sorter.sort(null);
        } catch (NullPointerException e) {
            // expected
            return;
        } catch (Exception e) {
            Assert.fail("wrong exception thrown: " + e);
        }
        Assert.fail("no exception thrown");
    }

    @Test
    public void testSortElementNull() throws Exception {

        List<Integer> before = Arrays.asList(2, null, 7);

        try {
            sorter.sort(before);
        } catch (NullPointerException e) {
            // expected
            return;
        } catch (Exception e) {
            Assert.fail("wrong exception thrown: " + e);
        }
        Assert.fail("no exception thrown");
    }
}
