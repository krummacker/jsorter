package de.krummacker.jsorter;

import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Tests the SorterPerformanceTester class.
 */
public class SorterPerformanceTesterTest {

    /**
     * Make sure that the executeApplication() method can be called (happy case).
     */
    @Test
    public void testExecuteApplication() {
        String[] args = new String[]{"-m", "5", "-s", "1"};
        SorterPerformanceTester.executeApplication(args, new PrintStream(new ByteArrayOutputStream()));
    }

    /**
     * Make sure that the executeApplication() method does not crash on wrong arguments.
     */
    @Test
    public void testExecuteApplicationWrongArgs() {
        String[] args = new String[]{"-m", "5", "-x", "1"};
        SorterPerformanceTester.executeApplication(args, new PrintStream(new ByteArrayOutputStream()));
    }
}