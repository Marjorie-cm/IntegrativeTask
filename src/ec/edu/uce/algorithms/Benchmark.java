package ec.edu.uce.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Activity 4: Experimental Benchmark and Comparison Module.
 * Evaluates Bubble Sort, Selection Sort, Insertion Sort, and Shell Sort across
 * input sizes n = {10, 100, 1,000, 10,000} and 5 data distributions, averaging
 * multiple runs timed via System.nanoTime() after JVM warm-up.
 */
public class Benchmark {

    public enum DatasetType {
        ASCENDING("Ascending"),
        DESCENDING("Descending"),
        RANDOM("Random"),
        DUPLICATES("Duplicates"),
        NEARLY_SORTED("Nearly sorted");

        private final String label;

        DatasetType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public record BenchmarkMetric(
            String algorithm,
            int n,
            DatasetType type,
            long comparisons,
            long movements,
            double averageTimeMs
    ) {}

    /**
     * Generates reproducible datasets by size and distribution type.
     */
    public static int[] generateDataset(int n, DatasetType type, long seed) {
        Random random = new Random(seed);
        int[] arr = new int[n];

        switch (type) {
            case ASCENDING -> {
                for (int i = 0; i < n; i++) arr[i] = i + 1;
            }
            case DESCENDING -> {
                for (int i = 0; i < n; i++) arr[i] = n - i;
            }
            case RANDOM -> {
                for (int i = 0; i < n; i++) arr[i] = random.nextInt(n * 10) + 1;
            }
            case DUPLICATES -> {
                int distinctValues = Math.max(2, Math.min(10, n / 2));
                for (int i = 0; i < n; i++) arr[i] = (random.nextInt(distinctValues) + 1) * 10;
            }
            case NEARLY_SORTED -> {
                for (int i = 0; i < n; i++) arr[i] = i + 1;
                int localSwaps = Math.max(1, (int) (n * 0.05));
                for (int k = 0; k < localSwaps; k++) {
                    int idx1 = random.nextInt(n);
                    int idx2 = Math.min(n - 1, Math.max(0, idx1 + random.nextInt(5) - 2));
                    int temp = arr[idx1];
                    arr[idx1] = arr[idx2];
                    arr[idx2] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * Warms up the JVM so the HotSpot JIT compiler stabilizes before timing.
     */
    public static void warmUpJVM() {
        int[] sample = generateDataset(1_000, DatasetType.RANDOM, 99L);
        for (int i = 0; i < 5; i++) {
            Sorting.bubbleSort(sample);
            Sorting.selectionSort(sample);
            Sorting.insertionSort(sample);
            Sorting.shellSort(sample);
        }
    }

    /**
     * Runs the full benchmark suite (minimum 3 runs per combination).
     */
    public static List<BenchmarkMetric> runBenchmark(int runs) {
        if (runs < 3) {
            throw new IllegalArgumentException("At least 3 runs per combination are required.");
        }

        warmUpJVM();

        int[] sizes = {10, 100, 1_000, 10_000};
        String[] algorithms = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Shell Sort"};
        List<BenchmarkMetric> results = new ArrayList<>();

        for (int n : sizes) {
            for (DatasetType type : DatasetType.values()) {
                // Dataset generation is strictly excluded from timing measurements
                int[] baseDataset = generateDataset(n, type, 42L + n);

                for (String alg : algorithms) {
                    long totalNano = 0;
                    long comparisons = 0;
                    long movements = 0;

                    for (int r = 0; r < runs; r++) {
                        Sorting.SortingResult res = switch (alg) {
                            case "Bubble Sort" -> Sorting.bubbleSort(baseDataset);
                            case "Selection Sort" -> Sorting.selectionSort(baseDataset);
                            case "Insertion Sort" -> Sorting.insertionSort(baseDataset);
                            case "Shell Sort" -> Sorting.shellSort(baseDataset);
                            default -> throw new IllegalStateException("Unsupported algorithm: " + alg);
                        };
                        totalNano += res.timeNano();
                        comparisons = res.comparisons();
                        movements = res.movements();
                    }

                    double averageMs = (totalNano / (double) runs) / 1_000_000.0;
                    results.add(new BenchmarkMetric(alg, n, type, comparisons, movements, averageMs));
                }
            }
        }
        return results;
    }

    /**
     * Prints basic hardware and JVM specifications, the required summary table
     * (n = 100, Random), and the full experimental results matrix.
     */
    public static void runAndDisplayResults() {
        System.out.println("\n=========================================================================================");
        System.out.println("ACTIVITY 4: EXPERIMENTAL BENCHMARK AND SORTING COMPARISON");
        System.out.println("=========================================================================================");
        System.out.println("System and JVM Specifications:");
        System.out.println("  * Java Version : " + System.getProperty("java.version") + " (" + System.getProperty("java.vm.name") + ")");
        System.out.println("  * OS           : " + System.getProperty("os.name") + " " + System.getProperty("os.arch"));
        System.out.println("  * CPU Cores    : " + Runtime.getRuntime().availableProcessors());
        System.out.printf("  * Max JVM Heap : %.2f MiB%n", Runtime.getRuntime().maxMemory() / (1024.0 * 1024.0));

        List<BenchmarkMetric> list = runBenchmark(5);

        System.out.println("\n--- Required Results Table (n = 100 | Dataset Type = Random) ---");
        System.out.printf("%-16s | %-6s | %-12s | %-14s | %-12s | %-18s%n",
                "Algorithm", "n", "Data Type", "Comparisons", "Movements", "Average Time (ms)");
        System.out.println("-".repeat(90));
        for (BenchmarkMetric m : list) {
            if (m.n() == 100 && m.type() == DatasetType.RANDOM) {
                System.out.printf("%-16s | %-6d | %-12s | %-14d | %-12d | %-18.5f%n",
                        m.algorithm(), m.n(), m.type().getLabel(),
                        m.comparisons(), m.movements(), m.averageTimeMs());
            }
        }

        System.out.println("\n--- Full Experimental Matrix (n = 10, 100, 1,000, 10,000 | 5 Distributions) ---");
        System.out.printf("%-16s | %-6s | %-14s | %-14s | %-14s | %-18s%n",
                "Algorithm", "n", "Data Type", "Comparisons", "Movements", "Average Time (ms)");
        System.out.println("-".repeat(94));
        for (BenchmarkMetric m : list) {
            System.out.printf("%-16s | %-6d | %-14s | %-14d | %-14d | %-18.5f%n",
                    m.algorithm(), m.n(), m.type().getLabel(),
                    m.comparisons(), m.movements(), m.averageTimeMs());
        }
    }
}