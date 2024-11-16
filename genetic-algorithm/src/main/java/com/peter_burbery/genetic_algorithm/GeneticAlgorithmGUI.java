package com.peter_burbery.genetic_algorithm;

//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GeneticAlgorithmGUI extends JFrame {
//    private JTextField goalField;
//    private JButton startButton;
//    private JTable resultTable;
//    private DefaultTableModel tableModel;
//
//    public GeneticAlgorithmGUI() {
//        setTitle("Genetic Algorithm Simulator");
//        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Input panel
//        JPanel inputPanel = new JPanel();
//        inputPanel.setLayout(new FlowLayout());
//
//        JLabel goalLabel = new JLabel("Enter Goal:");
//        goalField = new JTextField(20);
//        startButton = new JButton("Start");
//
//        inputPanel.add(goalLabel);
//        inputPanel.add(goalField);
//        inputPanel.add(startButton);
//
//        add(inputPanel, BorderLayout.NORTH);
//
//        // Table setup
//        String[] columnNames = {"Generation", "Best Fitness", "Average Fitness", "Best Guess"};
//        tableModel = new DefaultTableModel(columnNames, 0);
//        resultTable = new JTable(tableModel);
//
//        add(new JScrollPane(resultTable), BorderLayout.CENTER);
//
//        // Start button action
//        startButton.addActionListener(new StartButtonListener());
//    }
//
//    private class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String goal = goalField.getText();
//            if (goal.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Please enter a goal string!", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // Run genetic algorithm in a new thread
//            new Thread(() -> runGeneticAlgorithm(goal)).start();
//        }
//    }
//
//    private void runGeneticAlgorithm(String goal) {
//        int popSize = 100;  // Example population size
//        int numGenerations = 200;  // Example number of generations
//        double mutateProb = 0.05;  // Mutation probability
//
//        Population population = new Population(goal, popSize, numGenerations, mutateProb);
//
////        for (int g = 0; g < numGenerations; g++) {
////            population.iterate();
////
////            int bestFitness = population.getBestFitness();
////            double avgFitness = population.getAverageFitness();
////            String bestGuess = population.getBestGuess();
////
////            // Update table in the Swing thread
////            SwingUtilities.invokeLater(() -> {
////                tableModel.addRow(new Object[]{g, bestFitness, avgFitness, bestGuess});
////            });
////
////            // Stop if goal is reached
////            if (bestFitness == goal.length()) {
////                JOptionPane.showMessageDialog(null, "Goal reached in generation " + g + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
////                break;
////            }
////        }
//        for (int g = 0; g < numGenerations; g++) {
//            int generation = g; // Create a final or effectively final variable
//
//            // Update the genetic algorithm
//            population.iterate();
//
//            int bestFitness = population.getBestFitness();
//            double avgFitness = population.getAverageFitness();
//            String bestGuess = population.getBestGuess();
//
//            // Update table in the Swing thread
//            SwingUtilities.invokeLater(() -> {
//                tableModel.addRow(new Object[]{generation, bestFitness, avgFitness, bestGuess});
//            });
//
//            // Stop if goal is reached
//            if (bestFitness == goal.length()) {
//                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
//                    null, "Goal reached in generation " + generation + "!", "Success", JOptionPane.INFORMATION_MESSAGE
//                ));
//                break;
//            }
//        }
//
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GeneticAlgorithmGUI gui = new GeneticAlgorithmGUI();
//            gui.setVisible(true);
//        });
//    }
//}
//

//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GeneticAlgorithmGUI extends JFrame {
//    private JTextField goalField;
//    private JButton startButton;
//    private JTable resultTable;
//    private DefaultTableModel tableModel;
//
//    public GeneticAlgorithmGUI() {
//        setTitle("Genetic Algorithm Simulator");
//        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Input panel
//        JPanel inputPanel = new JPanel(new FlowLayout());
//        JLabel goalLabel = new JLabel("Enter Goal:");
//        goalField = new JTextField(20);
//        startButton = new JButton("Start");
//
//        inputPanel.add(goalLabel);
//        inputPanel.add(goalField);
//        inputPanel.add(startButton);
//
//        add(inputPanel, BorderLayout.NORTH);
//
//        // Table setup
//        String[] columnNames = {"Generation", "Best Fitness", "Average Fitness", "Best Guess"};
//        tableModel = new DefaultTableModel(columnNames, 0);
//        resultTable = new JTable(tableModel);
//
//        add(new JScrollPane(resultTable), BorderLayout.CENTER);
//
//        // Start button action
//        startButton.addActionListener(new StartButtonListener());
//    }
//
//    private class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String goal = goalField.getText();
//            if (goal.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Please enter a goal string!", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            // Clear the table for a new run
//            tableModel.setRowCount(0);
//
//            // Run genetic algorithm in a new thread
//            new Thread(() -> runGeneticAlgorithm(goal)).start();
//        }
//    }
//
////    private void runGeneticAlgorithm(String goal) {
////        int popSize = 100;  // Example population size
////        int numGenerations = 200;  // Example number of generations
////        double mutateProb = 0.05;  // Mutation probability
////
////        Population population = new Population(goal, popSize, numGenerations, mutateProb);
////
////        for (int g = 0; g < numGenerations; g++) {
////            population.iterate();
////
////            int bestFitness = population.getBestFitness();
////            double avgFitness = population.getAverageFitness();
////            String bestGuess = population.getBestGuess();
////
////            // Update table in the Swing thread
////            int finalG = g;
////            SwingUtilities.invokeLater(() -> {
////                tableModel.addRow(new Object[]{finalG, bestFitness, avgFitness, bestGuess});
////            });
////
////            // Stop if goal is reached
////            if (bestFitness == goal.length()) {
////                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
////                    null, "Goal reached in generation " + finalG + "!", "Success", JOptionPane.INFORMATION_MESSAGE
////                ));
////                break;
////            }
////        }
////    }
//    private void runGeneticAlgorithm(String goal) {
//        int popSize = 100;  // Example population size
//        int numGenerations = 10_000;  // Example number of generations
//        double mutateProb = 0.05;  // Mutation probability
//
//        Population population = new Population(goal, popSize, numGenerations, mutateProb);
//
//        for (int g = 0; g < numGenerations; g++) {
//            // Update the population by one generation
//            Population.GenerationData data = population.iterateOneGeneration(g);
//
//            // Update table in the Swing thread
//            SwingUtilities.invokeLater(() -> {
//                tableModel.addRow(new Object[]{data.generation, data.bestFitness, data.avgFitness, data.bestGuess});
//            });
//
//            // Stop if goal is reached
//            if (data.bestFitness == goal.length()) {
//                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
//                    null, "Goal reached in generation " + data.generation + "!", "Success", JOptionPane.INFORMATION_MESSAGE
//                ));
//                break;
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GeneticAlgorithmGUI gui = new GeneticAlgorithmGUI();
//            gui.setVisible(true);
//        });
//    }
//}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class GeneticAlgorithmGUI extends JFrame {
    private JTextField goalField;
    private JTextField popSizeField;
    private JTextField generationsField;
    private JTextField mutateProbField;
    private JButton startButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public GeneticAlgorithmGUI() {
        setTitle("Genetic Algorithm Simulator");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel at the top
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        inputPanel.add(new JLabel("String to Guess:"));
        goalField = new JTextField(10);
        inputPanel.add(goalField);
        
        inputPanel.add(new JLabel("Organisms per Generation:"));
        popSizeField = new JTextField(5);
        inputPanel.add(popSizeField);
        
        inputPanel.add(new JLabel("Number of Generations:"));
        generationsField = new JTextField(5);
        inputPanel.add(generationsField);
        
        inputPanel.add(new JLabel("Mutation Probability:"));
        mutateProbField = new JTextField(5);
        inputPanel.add(mutateProbField);
        
        startButton = new JButton("Start");
        inputPanel.add(startButton);
        
        add(inputPanel, BorderLayout.NORTH);

        // Table to display results
        tableModel = new DefaultTableModel(new String[]{"Generation", "Best Fitness", "Average Fitness", "Best Guess"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Start button listener
        startButton.addActionListener(new StartButtonListener());
    }

//    private class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String goal = goalField.getText();
//            int popSize = Integer.parseInt(popSizeField.getText());
//            int numGenerations = Integer.parseInt(generationsField.getText());
//            double mutateProb = Double.parseDouble(mutateProbField.getText());
//
//            // Run the genetic algorithm
//            runGeneticAlgorithm(goal, popSize, numGenerations, mutateProb);
//        }
//    }
    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear the table for a new run
            tableModel.setRowCount(0); // Clears previous data
            
            try {
                String goal = goalField.getText();
                int popSize = Integer.parseInt(popSizeField.getText());
                int numGenerations = Integer.parseInt(generationsField.getText());
                double mutateProb = Double.parseDouble(mutateProbField.getText());

                // Validate inputs
                if (goal.isEmpty() || popSize <= 0 || numGenerations <= 0 || mutateProb <= 0 || mutateProb > 1) {
                    throw new IllegalArgumentException("Please enter valid input values.");
                }

                // Run the genetic algorithm
                runGeneticAlgorithm(goal, popSize, numGenerations, mutateProb);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numerical values.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void runGeneticAlgorithm(String goal, int popSize, int numGenerations, double mutateProb) {
        Population population = new Population(goal, popSize, numGenerations, mutateProb);

        for (int g = 0; g < numGenerations; g++) {
            Population.GenerationData data = population.iterateOneGeneration(g);

            SwingUtilities.invokeLater(() -> {
                tableModel.addRow(new Object[]{data.generation, data.bestFitness, data.avgFitness, data.bestGuess});
            });

            if (data.bestFitness == goal.length()) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    null, "Goal reached in generation " + data.generation + "!", "Success", JOptionPane.INFORMATION_MESSAGE
                ));
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GeneticAlgorithmGUI gui = new GeneticAlgorithmGUI();
            gui.setVisible(true);
        });
    }
}
