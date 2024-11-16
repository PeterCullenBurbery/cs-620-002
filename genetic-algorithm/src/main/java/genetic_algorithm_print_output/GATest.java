//package genetic_algorithm_print_output;
//
//public class GATest {
//    public static void main(String[] args) {
//        String goal = "Hello World";
//        Population population = new Population(goal, 100, 1000, 0.01);
//        population.iterate();
//    }
//}
//
package genetic_algorithm_print_output;

import java.util.Scanner;

public class GATest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt for the string to guess
        System.out.print("Enter the string to guess: ");
        String goal = scanner.nextLine();

        // Prompt for the number of organisms per generation
        System.out.print("Enter the number of organisms per generation: ");
        int popSize = Integer.parseInt(scanner.nextLine());

        // Prompt for the number of generations
        System.out.print("Enter the number of generations: ");
        int numGenerations = Integer.parseInt(scanner.nextLine());

        // Prompt for the mutation probability
        System.out.print("Enter the mutation probability (e.g., 0.01): ");
        double mutateProb = Double.parseDouble(scanner.nextLine());

        // Create the population and start the genetic algorithm
        Population population = new Population(goal, popSize, numGenerations, mutateProb);
        population.iterate();

        scanner.close();
    }
}
