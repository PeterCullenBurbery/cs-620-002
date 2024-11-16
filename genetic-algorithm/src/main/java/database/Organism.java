package database;

import java.util.Random;

public class Organism implements Comparable<Organism> {
    private String value;
    private String goalString;

    public Organism(String goalString) {
        this.goalString = goalString;
        this.value = generateRandomString(goalString.length());
    }

    public Organism(String value, String goalString) {
        this.value = value;
        this.goalString = goalString;
    }

    private String generateRandomString(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public int fitness() {
        int count = 0;
        for (int i = 0; i < goalString.length(); i++) {
            if (value.charAt(i) == goalString.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public Organism[] mate(Organism other) {
        Random rand = new Random();
        int crossover = rand.nextInt(goalString.length());
        String child1 = this.value.substring(0, crossover) + other.value.substring(crossover);
        String child2 = other.value.substring(0, crossover) + this.value.substring(crossover);
        return new Organism[]{
            new Organism(child1, this.goalString),
            new Organism(child2, this.goalString)
        };
    }

    public void mutate(double mutateProb) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(this.value);
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
        for (int i = 0; i < sb.length(); i++) {
            if (rand.nextDouble() < mutateProb) {
                sb.setCharAt(i, chars.charAt(rand.nextInt(chars.length())));
            }
        }
        this.value = sb.toString();
    }

    @Override
    public int compareTo(Organism other) {
        return Integer.compare(other.fitness(), this.fitness());
    }
    public String getValue() {
        return value;
    }
    @Override
    public String toString() {
        return "Value = " + value + " Goal = " + goalString + " Fitness = " + fitness();
    }
}

