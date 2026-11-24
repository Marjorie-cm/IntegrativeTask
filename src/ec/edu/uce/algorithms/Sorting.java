package ec.edu.uce.algorithms;

import java.util.Arrays;

/**
 * Activity 3 (Week 5): From-scratch implementation of sorting algorithms in Java:
 * Bubble Sort, Selection Sort, Insertion Sort, and Shell Sort.
 * Does NOT use Arrays.sort(), Collections.sort(), or stream().sorted().
 *
 * Documented Counting Criteria:
 * - Comparisons: +1 for each relational evaluation between two array values.
 * - Movements: +1 for each write/assignment into an array position (+1 per shift
 *   or key insertion; +2 per full swap of two elements using a temporary variable).
 */
public class Sorting {

    public record SortingResult(
            String algorithm,
            long comparisons,
            long movements,
            long timeNano,
            int[] sortedArray
    ) {
        public double timeMs() {
            return timeNano / 1_000_000.0;
        }
    }

    /**
     * 1. Bubble Sort implemented from scratch (with early-exit optimization flag).
     */
    public static SortingResult bubbleSort(int[] input) {
        if (input == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        int[] arr = input.clone();
        int n = arr.length;
        long comparisons = 0;
        long movements = 0;

        long start = System.nanoTime();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                comparisons++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    movements += 2;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        long end = System.nanoTime();

        return new SortingResult("Bubble Sort", comparisons, movements, end - start, arr);
    }

    /**
     * 2. Selection Sort implemented from scratch.
     */
    public static SortingResult selectionSort(int[] input) {
        if (input == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        int[] arr = input.clone();
        int n = arr.length;
        long comparisons = 0;
        long movements = 0;

        long start = System.nanoTime();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                comparisons++;
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                movements += 2;
            }
        }
        long end = System.nanoTime();

        return new SortingResult("Selection Sort", comparisons, movements, end - start, arr);
    }

    /**
     * 3. Insertion Sort implemented from scratch.
     */
    public static SortingResult insertionSort(int[] input) {
        if (input == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        int[] arr = input.clone();
        int n = arr.length;
        long comparisons = 0;
        long movements = 0;

        long start = System.nanoTime();
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                comparisons++;
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    movements++;
                    j--;
                } else {
                    break;
                }
            }
            if (j + 1 != i) {
                arr[j + 1] = key;
                movements++;
            }
        }
        long end = System.nanoTime();

        return new SortingResult("Insertion Sort", comparisons, movements, end - start, arr);
    }

    /**
     * 4. Shell Sort implemented from scratch (gap sequence n/2, n/4, ..., 1).
     */
    public static SortingResult shellSort(int[] input) {
        if (input == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        int[] arr = input.clone();
        int n = arr.length;
        long comparisons = 0;
        long movements = 0;

        long start = System.nanoTime();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;
                while (j >= gap) {
                    comparisons++;
                    if (arr[j - gap] > temp) {
                        arr[j] = arr[j - gap];
                        movements++;
                        j -= gap;
                    } else {
                        break;
                    }
                }
                if (j != i) {
                    arr[j] = temp;
                    movements++;
                }
            }
        }
        long end = System.nanoTime();

        return new SortingResult("Shell Sort", comparisons, movements, end - start, arr);
    }

    /**
     * Validates all four sorting algorithms against the initial test dataset
     * and required edge cases (empty, single element, duplicates, pre-sorted).
     */
    public static void runValidations() {
        System.out.println("\n=========================================================================================");
        System.out.println("ACTIVITY 3: FROM-SCRATCH SORTING ALGORITHMS IMPLEMENTATION (WEEK 5)");
        System.out.println("=========================================================================================");

        int[] data = {45, 12, 90, 12, 3, 70, 45, 18};
        System.out.println("Initial dataset : " + Arrays.toString(data));
        System.out.println("Expected output : [3, 12, 12, 18, 45, 45, 70, 90]\n");

        SortingResult[] results = {
                bubbleSort(data),
                selectionSort(data),
                insertionSort(data),
                shellSort(data)
        };

        for (SortingResult r : results) {
            System.out.printf("%-15s -> %-32s | Comp=%-3d | Mov=%-3d | Time=%.5f ms%n",
                    r.algorithm(), Arrays.toString(r.sortedArray()),
                    r.comparisons(), r.movements(), r.timeMs());
        }

        System.out.println("\nSpecial edge cases validation (empty, 1 element, duplicates, pre-sorted):");
        int[][] edgeCases = {
                {},
                {42},
                {5, 2, 5, 1, 2, 5},
                {10, 20, 30, 40, 50},
                {50, 40, 30, 20, 10}
        };
        String[] labels = {
                "Empty array",
                "Single element",
                "Duplicate values",
                "Pre-sorted asc.",
                "Pre-sorted desc."
        };

        for (int i = 0; i < edgeCases.length; i++) {
            SortingResult b = bubbleSort(edgeCases[i]);
            SortingResult s = selectionSort(edgeCases[i]);
            SortingResult ins = insertionSort(edgeCases[i]);
            SortingResult sh = shellSort(edgeCases[i]);

            boolean allMatch = Arrays.equals(b.sortedArray(), s.sortedArray())
                    && Arrays.equals(s.sortedArray(), ins.sortedArray())
                    && Arrays.equals(ins.sortedArray(), sh.sortedArray());

            System.out.printf("  * %-18s -> Output: %-20s | Match=%b | Comp(B/S/I/Sh)=%d/%d/%d/%d%n",
                    labels[i], Arrays.toString(sh.sortedArray()), allMatch,
                    b.comparisons(), s.comparisons(), ins.comparisons(), sh.comparisons());
        }
    }
}