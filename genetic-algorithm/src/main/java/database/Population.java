package database;

import java.util.Arrays;
import java.util.Random;

public class Population {
    private int popSize;
    private double mutateProb;
    private int numGenerations;
    private Organism[] thisGeneration;
    private Organism[] nextGeneration;
    private String goalString;

    public Population(String goalString, int popSize, int numGenerations, double mutateProb) {
        this.goalString = goalString;
        this.popSize = popSize;
        this.numGenerations = numGenerations;
        this.mutateProb = mutateProb;
        thisGeneration = new Organism[popSize];
        nextGeneration = new Organism[popSize];

        for (int i = 0; i < popSize; i++) {
            thisGeneration[i] = new Organism(goalString);
        }
    }

    // Parent selection method (roulette wheel selection)
    private Organism selectParent() {
        int totalFitness = Arrays.stream(thisGeneration).mapToInt(Organism::fitness).sum();
        Random rand = new Random();
        int randFitness = rand.nextInt(totalFitness);
        int runningSum = 0;
        for (Organism org : thisGeneration) {
            runningSum += org.fitness();
            if (runningSum >= randFitness) {
                return org;
            }
        }
        return thisGeneration[thisGeneration.length - 1]; // Fallback if no selection
    }

    public GenerationData iterateOneGeneration(int currentGeneration) {
        double adaptiveMutateProb = mutateProb * (1.0 - (currentGeneration / (double) numGenerations));

        // Sort the current generation by fitness
        Arrays.sort(thisGeneration);

        // Calculate average fitness
        double totalFitness = Arrays.stream(thisGeneration).mapToInt(Organism::fitness).sum();
        double avgFitness = totalFitness / popSize;

        // Elitism: Keep the best organism
        nextGeneration[0] = thisGeneration[0];

        for (int i = 1; i < popSize / 2; i++) {
            Organism parent1 = selectParent();
            Organism parent2 = selectParent();
            Organism[] children = parent1.mate(parent2);

            // Apply adaptive mutation
            children[0].mutate(adaptiveMutateProb);
            children[1].mutate(adaptiveMutateProb);

            nextGeneration[2 * i] = children[0];
            nextGeneration[2 * i + 1] = children[1];
        }

        // Ensure nextGeneration is fully populated
        for (int i = 0; i < popSize; i++) {
            if (nextGeneration[i] == null) {
                nextGeneration[i] = new Organism(goalString); // Fill any gaps
            }
        }

        // Copy nextGeneration to thisGeneration
        thisGeneration = Arrays.copyOf(nextGeneration, popSize);

        // Return generation data for GUI update
        return new GenerationData(
            currentGeneration,
            getBestFitness(),
            getAverageFitness(),
            getBestGuess(),
            adaptiveMutateProb // Pass mutation probability
        );
    }

    // Get the best fitness in the current generation
    public int getBestFitness() {
        return thisGeneration[0].fitness();
    }

    // Calculate and return the average fitness of the current generation
    public double getAverageFitness() {
        return Arrays.stream(thisGeneration).mapToInt(Organism::fitness).average().orElse(0.0);
    }

//    // Get the best guess (string) from the current generation
//    public String getBestGuess() {
//        return thisGeneration[0].toString();
//    }
    public String getBestGuess() {
        return thisGeneration[0].getValue(); // Assuming Organism has a getValue() method
    }

    // Data class to hold generation results
    public static class GenerationData {
        public final int generation;
        public final int bestFitness;
        public final double avgFitness;
        public final String bestGuess;
        public final double adaptiveMutateProb; // Add mutation probability field

        public GenerationData(int generation, int bestFitness, double avgFitness, String bestGuess, double adaptiveMutateProb) {
            this.generation = generation;
            this.bestFitness = bestFitness;
            this.avgFitness = avgFitness;
            this.bestGuess = bestGuess;
            this.adaptiveMutateProb = adaptiveMutateProb; // Store mutation probability
        }
    }

}
