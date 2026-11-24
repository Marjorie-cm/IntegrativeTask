package ec.edu.uce.algorithms;

/**
 * Activity 2 (Week 4 - Iterative Strategy):
 * Implements iterative solutions for factorial of a non-negative integer,
 * sum of array elements, and maximum value identification in an array.
 * Time complexity: O(n) | Auxiliary space complexity: O(1).
 */
public class Iteration {

    public static long operations = 0;

    /**
     * Method 1: Computes the factorial of n iteratively.
     * Validates n >= 0 and n <= 20 to prevent 64-bit long integer overflow.
     */
    public static long factorialIterative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Invalid input: factorial is undefined for negative numbers (" + n + ").");
        }
        if (n > 20) {
            throw new IllegalArgumentException("Long type overflow: maximum supported input is n = 20 (" + n + ").");
        }
        operations = 0;
        long result = 1L;
        for (int i = 2; i <= n; i++) {
            result *= i;
            operations++;
        }
        return result;
    }

    /**
     * Method 2: Computes the sum of all elements in an array iteratively.
     */
    public static long arraySumIterative(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty.");
        }
        operations = 0;
        long sum = 0L;
        for (int value : array) {
            sum += value;
            operations++;
        }
        return sum;
    }

    /**
     * Method 3: Finds the maximum value in an array iteratively.
     */
    public static int arrayMaxIterative(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty.");
        }
        operations = 0;
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            operations++;
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
}