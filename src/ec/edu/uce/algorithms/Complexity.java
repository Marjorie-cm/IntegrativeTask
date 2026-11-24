package ec.edu.uce.algorithms;

import java.util.Random;

/**
 * Activity 1 (Week 3): Practical Algorithmic Complexity Analysis.
 * Implements three algorithms representing O(n), O(n^2), and O(log n) growth behaviors,
 * counting dominant operations, formulating T(n), and measuring execution time.
 */
public class Complexity {

    /**
     * Immutable structure to store metrics for each execution.
     */
    public record ComplexityResult(
            String algorithm,
            int n,
            long output,
            long dominantOperations,
            String formulaTn,
            String bigO,
            long timeNano
    ) {
        public double timeMs() {
            return timeNano / 1_000_000.0;
        }
    }

    /**
     * Algorithm A: Linear traversal to compute the sum of elements in an array.
     * - Input size (n): array length (array.length).
     * - Dominant operation: accumulated addition (sum += array[i]), executed n times.
     * - Cost function: T(n) = 3n + 3 -> Asymptotic complexity: O(n).
     */
    public static ComplexityResult algorithmALinear(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        int n = array.length;
        long operations = 0;
        long sum = 0;

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            sum += array[i];
            operations++; // Dominant operation
        }
        long end = System.nanoTime();

        return new ComplexityResult(
                "Algorithm A (Linear Sum)",
                n,
                sum,
                operations,
                "T(n) = 3n + 3",
                "O(n)",
                end - start
        );
    }

    /**
     * Algorithm B: Pairwise comparison of each array element against all others
     * using nested loops.
     * - Input size (n): array length (array.length).
     * - Dominant operation: pairwise comparison (array[i] == array[j]), executed n^2 times.
     * - Cost function: T(n) = 3n^2 + 3n + 3 -> Asymptotic complexity: O(n^2).
     */
    public static ComplexityResult algorithmBQuadratic(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        int n = array.length;
        long operations = 0;
        long matches = 0;

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                operations++; // Dominant operation
                if (array[i] == array[j]) {
                    matches++;
                }
            }
        }
        long end = System.nanoTime();

        return new ComplexityResult(
                "Algorithm B (Nested Loops)",
                n,
                matches,
                operations,
                "T(n) = 3n^2 + 3n + 3",
                "O(n^2)",
                end - start
        );
    }

    /**
     * Algorithm C: Successively halves a positive integer until reaching 1.
     * - Input size (n): positive integer value provided as input.
     * - Dominant operation: integer division by 2 (current /= 2), executed floor(log2(n)) times.
     * - Cost function: T(n) = 3 * floor(log2(n)) + 4 -> Asymptotic complexity: O(log n).
     */
    public static ComplexityResult algorithmCLogarithmic(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Input n must be a positive integer (> 0).");
        }
        long operations = 0;
        int current = n;

        long start = System.nanoTime();
        while (current > 1) {
            current /= 2;
            operations++; // Dominant operation
        }
        long end = System.nanoTime();

        return new ComplexityResult(
                "Algorithm C (Successive Halving)",
                n,
                current,
                operations,
                "T(n) = 3*floor(log2(n)) + 4",
                "O(log n)",
                end - start
        );
    }

    /**
     * Runs experimental tests for Activity 1 across multiple input sizes
     * and prints the comparison table of operations and execution times.
     */
    public static void runTests() {
        System.out.println("\n=========================================================================================");
        System.out.println("ACTIVITY 1: PRACTICAL ALGORITHMIC COMPLEXITY ANALYSIS (WEEK 3)");
        System.out.println("=========================================================================================");

        // Brief JVM warm-up
        int[] warmup = generateRandomArray(500, 10L);
        for (int w = 0; w < 5; w++) {
            algorithmALinear(warmup);
            algorithmBQuadratic(warmup);
            algorithmCLogarithmic(1000);
        }

        int[] sizes = {10, 100, 1_000, 5_000, 10_000};
        System.out.printf("%-8s | %-10s | %-11s | %-12s | %-11s | %-12s | %-11s%n",
                "n", "Ops O(n)", "Time(ms)", "Ops O(n^2)", "Time(ms)", "Ops O(log n)", "Time(ms)");
        System.out.println("-".repeat(89));

        for (int n : sizes) {
            int[] data = generateRandomArray(n, 42L);
            ComplexityResult resA = algorithmALinear(data);
            ComplexityResult resB = algorithmBQuadratic(data);
            ComplexityResult resC = algorithmCLogarithmic(n);

            System.out.printf("%-8d | %-10d | %-11.5f | %-12d | %-11.5f | %-12d | %-11.5f%n",
                    n,
                    resA.dominantOperations(), resA.timeMs(),
                    resB.dominantOperations(), resB.timeMs(),
                    resC.dominantOperations(), resC.timeMs());
        }

        System.out.println("\nTheoretical Cost Summary:");
        System.out.println("  * Algorithm A: Dominant op = accumulated sum       | T(n) = 3n + 3               | Big O = O(n)");
        System.out.println("  * Algorithm B: Dominant op = pairwise comparison   | T(n) = 3n^2 + 3n + 3        | Big O = O(n^2)");
        System.out.println("  * Algorithm C: Dominant op = integer division by 2 | T(n) = 3*floor(log2(n)) + 4 | Big O = O(log n)");
    }

    private static int[] generateRandomArray(int n, long seed) {
        Random random = new Random(seed);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(100) + 1;
        }
        return arr;
    }
}