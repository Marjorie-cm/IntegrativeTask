package ec.edu.uce.algorithms;

import java.util.Scanner;

/**
 * Main entry point for the console application of the Integrative Task project.
 * Integrates and executes Activities 1, 2, 3, and 4 modularly.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--all")) {
            runAllActivities();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            printMenu();
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
            } else {
                scanner.next();
                System.out.println("Invalid input. Please enter a number between 0 and 5.");
                continue;
            }

            switch (option) {
                case 1 -> Complexity.runTests();
                case 2 -> Recursion.runComparativeTests();
                case 3 -> Sorting.runValidations();
                case 4 -> Benchmark.runAndDisplayResults();
                case 5 -> runAllActivities();
                case 0 -> System.out.println("Exiting application. Good luck with your submission!");
                default -> System.out.println("Option out of range. Please choose between 0 and 5.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n*****************************************************************");
        System.out.println("*  UNIVERSIDAD CENTRAL DEL ECUADOR - ALGORITHMS (WEEKS 3, 4 & 5)*");
        System.out.println("*  Project: Integrative Task (Java Implementation)              *");
        System.out.println("*****************************************************************");
        System.out.println("  1. Activity 1: Practical Complexity Analysis (O(n), O(n^2), O(log n))");
        System.out.println("  2. Activity 2: Iterative and Recursive Algorithms (6 methods + bounds)");
        System.out.println("  3. Activity 3: From-Scratch Sorting Algorithms (4 algorithms)");
        System.out.println("  4. Activity 4: Experimental Benchmark and Comparison (n=10..10,000)");
        System.out.println("  5. Run ALL Activities (Complete console report)");
        System.out.println("  0. Exit");
        System.out.print("Select an option: ");
    }

    public static void runAllActivities() {
        Complexity.runTests();
        Recursion.runComparativeTests();
        Sorting.runValidations();
        Benchmark.runAndDisplayResults();
    }
}