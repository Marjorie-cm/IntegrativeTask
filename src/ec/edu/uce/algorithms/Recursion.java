package ec.edu.uce.algorithms;

import java.util.Arrays;

/**
 * Activity 2 (Week 4 - Recursive Strategy & Comparison):
 * Implements recursive solutions for factorial, array sum, and array maximum,
 * including explicit base cases, defensive input validation, and comparative tests
 * against the Iteration class.
 * Time complexity: O(n) | Auxiliary space complexity: O(n) on the JVM call stack.
 */
public class Recursion {

    public static long operations = 0;

    /**
     * Method 4: Computes the factorial of n recursively.
     * Base case: n == 0 || n == 1 -> returns 1.
     */
    public static long factorialRecursive(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Invalid input: factorial is undefined for negative numbers (" + n + ").");
        }
        if (n > 20) {
            throw new IllegalArgumentException("Long type overflow: maximum supported input is n = 20 (" + n + ").");
        }
        operations = 0;
        return factorialHelper(n);
    }

    private static long factorialHelper(int n) {
        operations++;
        if (n == 0 || n == 1) {
            return 1L;
        }
        return n * factorialHelper(n - 1);
    }

    /**
     * Method 5: Computes the sum of array elements recursively.
     * Base case: index == array.length - 1.
     */
    public static long arraySumRecursive(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty.");
        }
        operations = 0;
        return sumHelper(array, 0);
    }

    private static long sumHelper(int[] array, int index) {
        operations++;
        if (index == array.length - 1) {
            return array[index];
        }
        return array[index] + sumHelper(array, index + 1);
    }

    /**
     * Method 6: Finds the maximum value in an array recursively.
     * Base case: index == array.length - 1.
     */
    public static int arrayMaxRecursive(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty.");
        }
        operations = 0;
        return maxHelper(array, 0);
    }

    private static int maxHelper(int[] array, int index) {
        operations++;
        if (index == array.length - 1) {
            return array[index];
        }
        int maxRest = maxHelper(array, index + 1);
        return (array[index] > maxRest) ? array[index] : maxRest;
    }

    /**
     * Runs minimum test cases, boundary cases, and performance comparison
     * across all six methods (3 iterative and 3 recursive).
     */
    public static void runComparativeTests() {
        System.out.println("\n=========================================================================================");
        System.out.println("ACTIVITY 2: ITERATIVE AND RECURSIVE ALGORITHMS IMPLEMENTATION (WEEK 4)");
        System.out.println("=========================================================================================");

        int[] values = {8, 3, 10, 2, 7};
        int number = 5;

        // 1. Factorial(5)
        long t0 = System.nanoTime();
        long factIt = Iteration.factorialIterative(number);
        long t1 = System.nanoTime();
        long opsFactIt = Iteration.operations;

        long t2 = System.nanoTime();
        long factRec = factorialRecursive(number);
        long t3 = System.nanoTime();
        long opsFactRec = operations;

        // 2. Sum({8, 3, 10, 2, 7})
        long t4 = System.nanoTime();
        long sumIt = Iteration.arraySumIterative(values);
        long t5 = System.nanoTime();
        long opsSumIt = Iteration.operations;

        long t6 = System.nanoTime();
        long sumRec = arraySumRecursive(values);
        long t7 = System.nanoTime();
        long opsSumRec = operations;

        // 3. Max({8, 3, 10, 2, 7})
        long t8 = System.nanoTime();
        int maxIt = Iteration.arrayMaxIterative(values);
        long t9 = System.nanoTime();
        long opsMaxIt = Iteration.operations;

        long t10 = System.nanoTime();
        int maxRec = arrayMaxRecursive(values);
        long t11 = System.nanoTime();
        long opsMaxRec = operations;

        System.out.println("Minimum test cases (number = 5, values = " + Arrays.toString(values) + "):");
        System.out.printf("  * Factorial(5) -> Iterative: %d (ops=%d, %.5f ms) | Recursive: %d (ops=%d, %.5f ms)%n",
                factIt, opsFactIt, (t1 - t0) / 1e6, factRec, opsFactRec, (t3 - t2) / 1e6);
        System.out.printf("  * Array Sum    -> Iterative: %d (ops=%d, %.5f ms) | Recursive: %d (ops=%d, %.5f ms)%n",
                sumIt, opsSumIt, (t5 - t4) / 1e6, sumRec, opsSumRec, (t7 - t6) / 1e6);
        System.out.printf("  * Array Max    -> Iterative: %d (ops=%d, %.5f ms) | Recursive: %d (ops=%d, %.5f ms)%n",
                maxIt, opsMaxIt, (t9 - t8) / 1e6, maxRec, opsMaxRec, (t11 - t10) / 1e6);

        System.out.println("\nBoundary and validation test cases:");
        System.out.println("  * Lower bound 0!      -> Iterative = " + Iteration.factorialIterative(0)
                + " | Recursive = " + factorialRecursive(0));
        System.out.println("  * Upper bound 20!     -> Iterative = " + Iteration.factorialIterative(20)
                + " | Recursive = " + factorialRecursive(20));
        System.out.println("  * Single elem {42}    -> SumIt = " + Iteration.arraySumIterative(new int[]{42})
                + " | MaxRec = " + arrayMaxRecursive(new int[]{42}));

        try {
            Iteration.factorialIterative(-3);
        } catch (IllegalArgumentException e) {
            System.out.println("  * [OK] Negative input exception caught : " + e.getMessage());
        }
        try {
            factorialRecursive(21);
        } catch (IllegalArgumentException e) {
            System.out.println("  * [OK] Overflow exception caught       : " + e.getMessage());
        }
        try {
            arraySumRecursive(new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("  * [OK] Empty array exception caught    : " + e.getMessage());
        }
    }
}