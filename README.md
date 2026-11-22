# jsorter

`jsorter` is a small Java project for exploring and comparing sorting algorithms. It includes several quicksort variants, a bubble sort, and Java's standard list sort.

## Requirements

- JDK 25
- Apache Maven 3.10 or later

The project targets Java 25 using Maven's `maven.compiler.release` setting.

## Build and test

Run the unit tests with:

```sh
mvn test
```

Compile the project with:

```sh
mvn package
```

## Algorithms

| Class | Description |
| --- | --- |
| `BubbleSorter` | Bubble sort; modifies the input list. |
| `QuickSorter` | Quicksort using the first element as the pivot. |
| `RandomPivotQuickSorter` | Quicksort using a randomly selected pivot and grouping equal values. |
| `MedianQuickSorter` | Quicksort using the median of the first, middle, and last elements as the pivot. |
| `MultithreadedQuickSorter` | A parallel variant of the median-pivot quicksort. |
| `RemoveAddQuickSorter` | Quicksort that partitions by removing and inserting list elements. |
| `StandardApiSorter` | Java's built-in list sort. |

Sorters implement the `Sorter<T>` interface and accept lists of comparable values. Implementations may modify the input list or return a sorted copy, so callers should use the returned list and should not assume the input remains unchanged.

## Run the performance tester

After building, run the exploratory performance comparison with:

```sh
java -cp target/classes de.krummacker.jsorter.SorterPerformanceTester
```

The tester compares random and sorted integer lists at several sizes and reports elapsed microseconds. It skips some slow algorithm/input combinations at larger sizes. Results are intended for exploration, not rigorous benchmarking: each case is timed once, without warmup or repeated samples. For reliable comparisons, use a benchmark harness such as JMH.

`de.krummacker.tools.Tools.createRandomList` creates random integer input and has a seed overload for repeatable data.
