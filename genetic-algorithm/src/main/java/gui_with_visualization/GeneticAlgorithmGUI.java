//package gui_with_visualization;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GeneticAlgorithmGUI extends JFrame {
//    private JTextField goalField;
//    private JTextField popSizeField;
//    private JTextField generationsField;
//    private JTextField mutateProbField;
//    private JButton startButton;
//    private JTable table;
//    private DefaultTableModel tableModel;
//    private XYSeries bestFitnessSeries;
//    private XYSeries avgFitnessSeries;
//
//    public GeneticAlgorithmGUI() {
//        setTitle("Genetic Algorithm Simulator");
//        setSize(1500, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Input panel at the top
//        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//
//        inputPanel.add(new JLabel("String to Guess:"));
//        goalField = new JTextField(10);
//        inputPanel.add(goalField);
//
//        inputPanel.add(new JLabel("Organisms per Generation:"));
//        popSizeField = new JTextField(5);
//        inputPanel.add(popSizeField);
//
//        inputPanel.add(new JLabel("Number of Generations:"));
//        generationsField = new JTextField(5);
//        inputPanel.add(generationsField);
//
//        inputPanel.add(new JLabel("Mutation Probability:"));
//        mutateProbField = new JTextField(5);
//        inputPanel.add(mutateProbField);
//
//        startButton = new JButton("Start");
//        inputPanel.add(startButton);
//
//        add(inputPanel, BorderLayout.NORTH);
//
//        // Table to display results
//        tableModel = new DefaultTableModel(
//            new String[]{"Generation", "Best Fitness", "Average Fitness", "Best Guess", "Mutation Probability"}, 0);
//        table = new JTable(tableModel);
//        JScrollPane tableScrollPane = new JScrollPane(table);
//        tableScrollPane.setPreferredSize(new Dimension(500, 400));
//
//        // Create chart datasets
//        bestFitnessSeries = new XYSeries("Best Fitness");
//        avgFitnessSeries = new XYSeries("Average Fitness");
//
//        // Tabbed pane for charts
//        JTabbedPane tabbedPane = new JTabbedPane();
//
//        // Chart for Best Fitness
//        XYSeriesCollection bestFitnessDataset = new XYSeriesCollection(bestFitnessSeries);
//        JFreeChart bestFitnessChart = ChartFactory.createXYLineChart(
//            "Best Fitness vs Generations",
//            "Generation",
//            "Best Fitness",
//            bestFitnessDataset
//        );
//        ChartPanel bestFitnessChartPanel = new ChartPanel(bestFitnessChart);
//        tabbedPane.addTab("Best Fitness", bestFitnessChartPanel);
//
//        // Chart for Average Fitness
//        XYSeriesCollection avgFitnessDataset = new XYSeriesCollection(avgFitnessSeries);
//        JFreeChart avgFitnessChart = ChartFactory.createXYLineChart(
//            "Average Fitness vs Generations",
//            "Generation",
//            "Average Fitness",
//            avgFitnessDataset
//        );
//        ChartPanel avgFitnessChartPanel = new ChartPanel(avgFitnessChart);
//        tabbedPane.addTab("Average Fitness", avgFitnessChartPanel);
//
//        // Split pane for table and tabbed chart
//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, tabbedPane);
//        add(splitPane, BorderLayout.CENTER);
//
//        // Start button listener
//        startButton.addActionListener(new StartButtonListener());
//    }
//
//    private class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            tableModel.setRowCount(0); // Clears previous data
//            bestFitnessSeries.clear(); // Clear previous chart data
//            avgFitnessSeries.clear();
//
//            try {
//                String goal = goalField.getText();
//                int popSize = Integer.parseInt(popSizeField.getText());
//                int numGenerations = Integer.parseInt(generationsField.getText());
//                double mutateProb = Double.parseDouble(mutateProbField.getText());
//
//                // Validate inputs
//                if (goal.isEmpty() || popSize <= 0 || numGenerations <= 0 || mutateProb <= 0 || mutateProb > 1) {
//                    throw new IllegalArgumentException("Please enter valid input values.");
//                }
//
//                // Run the genetic algorithm
//                runGeneticAlgorithm(goal, popSize, numGenerations, mutateProb);
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(null, "Please enter valid numerical values.", "Input Error",
//                        JOptionPane.ERROR_MESSAGE);
//            } catch (IllegalArgumentException ex) {
//                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    private void runGeneticAlgorithm(String goal, int popSize, int numGenerations, double mutateProb) {
//        Population population = new Population(goal, popSize, numGenerations, mutateProb);
//
//        for (int g = 0; g < numGenerations; g++) {
//            Population.GenerationData data = population.iterateOneGeneration(g);
//
//            SwingUtilities.invokeLater(() -> {
//                tableModel.addRow(new Object[]{
//                        data.generation,
//                        data.bestFitness,
//                        data.avgFitness,
//                        data.bestGuess,
//                        data.adaptiveMutateProb
//                });
//                bestFitnessSeries.add(data.generation, data.bestFitness);
//                avgFitnessSeries.add(data.generation, data.avgFitness);
//            });
//
//            if (data.bestFitness == goal.length()) {
//                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
//                        null, "Goal reached in generation " + data.generation + "!", "Success", JOptionPane.INFORMATION_MESSAGE
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
//package gui_with_visualization;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GeneticAlgorithmGUI extends JFrame {
//    private JTextField goalField;
//    private JTextField popSizeField;
//    private JTextField generationsField;
//    private JTextField mutateProbField;
//    private JButton startButton;
//    private JTable table;
//    private DefaultTableModel tableModel;
//    private JLabel resultLabel;
//    private XYSeries bestFitnessSeries;
//    private XYSeries avgFitnessSeries;
//
//    public GeneticAlgorithmGUI() {
//        setTitle("Genetic Algorithm Simulator");
//        setSize(1200, 800);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Input panel at the top
//        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//
//        inputPanel.add(new JLabel("String to Guess:"));
//        goalField = new JTextField(10);
//        inputPanel.add(goalField);
//
//        inputPanel.add(new JLabel("Organisms per Generation:"));
//        popSizeField = new JTextField(5);
//        inputPanel.add(popSizeField);
//
//        inputPanel.add(new JLabel("Number of Generations:"));
//        generationsField = new JTextField(5);
//        inputPanel.add(generationsField);
//
//        inputPanel.add(new JLabel("Mutation Probability:"));
//        mutateProbField = new JTextField(5);
//        inputPanel.add(mutateProbField);
//
//        startButton = new JButton("Start");
//        inputPanel.add(startButton);
//
//        add(inputPanel, BorderLayout.NORTH);
//
//        // Table to display results
//        tableModel = new DefaultTableModel(
//                new String[]{"Generation", "Best Fitness", "Average Fitness", "Best Guess", "Mutation Probability"}, 0);
//        table = new JTable(tableModel);
//        JScrollPane tableScrollPane = new JScrollPane(table);
//
//        // Create chart datasets
//        bestFitnessSeries = new XYSeries("Best Fitness");
//        avgFitnessSeries = new XYSeries("Average Fitness");
//
//        // Tabbed pane for charts
//        JTabbedPane tabbedPane = new JTabbedPane();
//
//        // Chart for Best Fitness
//        XYSeriesCollection bestFitnessDataset = new XYSeriesCollection(bestFitnessSeries);
//        JFreeChart bestFitnessChart = ChartFactory.createXYLineChart(
//                "Best Fitness vs Generations",
//                "Generation",
//                "Best Fitness",
//                bestFitnessDataset
//        );
//        ChartPanel bestFitnessChartPanel = new ChartPanel(bestFitnessChart);
//        tabbedPane.addTab("Best Fitness", bestFitnessChartPanel);
//
//        // Chart for Average Fitness
//        XYSeriesCollection avgFitnessDataset = new XYSeriesCollection(avgFitnessSeries);
//        JFreeChart avgFitnessChart = ChartFactory.createXYLineChart(
//                "Average Fitness vs Generations",
//                "Generation",
//                "Average Fitness",
//                avgFitnessDataset
//        );
//        ChartPanel avgFitnessChartPanel = new ChartPanel(avgFitnessChart);
//        tabbedPane.addTab("Average Fitness", avgFitnessChartPanel);
//
//        // Split pane for table and charts
//        JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, tabbedPane);
//        topSplitPane.setResizeWeight(0.5); // Adjusts size proportionally
//
//        // Label for result (actual generations and time)
//        resultLabel = new JLabel("Generations needed: 0 | Time: 0.000 s");
//
//        // Bottom pane for status
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        bottomPanel.add(resultLabel);
//
//        // Main split pane for top and bottom sections
//        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplitPane, bottomPanel);
//        mainSplitPane.setResizeWeight(0.85); // Adjusts vertical size proportionally
//
//        add(mainSplitPane, BorderLayout.CENTER);
//
//        // Start button listener
//        startButton.addActionListener(new StartButtonListener());
//    }
//
//    private class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            tableModel.setRowCount(0); // Clears previous data
//            bestFitnessSeries.clear(); // Clear previous chart data
//            avgFitnessSeries.clear();
//
//            try {
//                String goal = goalField.getText();
//                int popSize = Integer.parseInt(popSizeField.getText());
//                int numGenerations = Integer.parseInt(generationsField.getText());
//                double mutateProb = Double.parseDouble(mutateProbField.getText());
//
//                // Validate inputs
//                if (goal.isEmpty() || popSize <= 0 || numGenerations <= 0 || mutateProb <= 0 || mutateProb > 1) {
//                    throw new IllegalArgumentException("Please enter valid input values.");
//                }
//
//                // Run the genetic algorithm
//                long startTime = System.nanoTime();
//                int actualGenerations = runGeneticAlgorithm(goal, popSize, numGenerations, mutateProb);
//                long endTime = System.nanoTime();
//
//                double elapsedTime = (endTime - startTime) / 1_000_000_000.0; // Convert to seconds
//                resultLabel.setText(
//                        String.format("Generations needed: %d | Time: %.9f s", actualGenerations, elapsedTime));
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(null, "Please enter valid numerical values.", "Input Error",
//                        JOptionPane.ERROR_MESSAGE);
//            } catch (IllegalArgumentException ex) {
//                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    private int runGeneticAlgorithm(String goal, int popSize, int numGenerations, double mutateProb) {
//        Population population = new Population(goal, popSize, numGenerations, mutateProb);
//        int actualGenerations = 0;
//
//        for (int g = 0; g < numGenerations; g++) {
//            actualGenerations = g;
//            Population.GenerationData data = population.iterateOneGeneration(g);
//
//            SwingUtilities.invokeLater(() -> {
//                tableModel.addRow(new Object[]{
//                        data.generation,
//                        data.bestFitness,
//                        data.avgFitness,
//                        data.bestGuess,
//                        data.adaptiveMutateProb
//                });
//                bestFitnessSeries.add(data.generation, data.bestFitness);
//                avgFitnessSeries.add(data.generation, data.avgFitness);
//            });
//
//            if (data.bestFitness == goal.length()) {
//                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
//                        null, "Goal reached in generation " + data.generation + "!", "Success", JOptionPane.INFORMATION_MESSAGE
//                ));
//                break;
//            }
//        }
//        return actualGenerations;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GeneticAlgorithmGUI gui = new GeneticAlgorithmGUI();
//            gui.setVisible(true);
//        });
//    }
//}
//package gui_with_visualization;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GeneticAlgorithmGUI extends JFrame {
//    private JTextField goalField;
//    private JTextField popSizeField;
//    private JTextField generationsField;
//    private JTextField mutateProbField;
//    private JButton startButton;
//    private JTable table;
//    private DefaultTableModel tableModel;
//    private JLabel resultLabel;
//    private XYSeries bestFitnessSeries;
//    private XYSeries avgFitnessSeries;
//
//    public GeneticAlgorithmGUI() {
//        setTitle("Genetic Algorithm Simulator");
//        setSize(1200, 800);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Input panel at the top
//        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//
//        inputPanel.add(new JLabel("String to Guess:"));
//        goalField = new JTextField(10);
//        inputPanel.add(goalField);
//
//        inputPanel.add(new JLabel("Organisms per Generation:"));
//        popSizeField = new JTextField(5);
//        inputPanel.add(popSizeField);
//
//        inputPanel.add(new JLabel("Number of Generations:"));
//        generationsField = new JTextField(5);
//        inputPanel.add(generationsField);
//
//        inputPanel.add(new JLabel("Mutation Probability:"));
//        mutateProbField = new JTextField(5);
//        inputPanel.add(mutateProbField);
//
//        startButton = new JButton("Start");
//        inputPanel.add(startButton);
//
//        add(inputPanel, BorderLayout.NORTH);
//
//        // Table to display results
//        tableModel = new DefaultTableModel(
//                new String[]{"Generation", "Best Fitness", "Average Fitness", "Best Guess", "Mutation Probability"}, 0);
//        table = new JTable(tableModel);
//        JScrollPane tableScrollPane = new JScrollPane(table);
//
//        // Create chart datasets
//        bestFitnessSeries = new XYSeries("Best Fitness");
//        avgFitnessSeries = new XYSeries("Average Fitness");
//
//        // Tabbed pane for charts
//        JTabbedPane tabbedPane = new JTabbedPane();
//
//        // Chart for Best Fitness
//        XYSeriesCollection bestFitnessDataset = new XYSeriesCollection(bestFitnessSeries);
//        JFreeChart bestFitnessChart = ChartFactory.createXYLineChart(
//                "Best Fitness vs Generations",
//                "Generation",
//                "Best Fitness",
//                bestFitnessDataset
//        );
//        ChartPanel bestFitnessChartPanel = new ChartPanel(bestFitnessChart);
//        tabbedPane.addTab("Best Fitness", bestFitnessChartPanel);
//
//        // Chart for Average Fitness
//        XYSeriesCollection avgFitnessDataset = new XYSeriesCollection(avgFitnessSeries);
//        JFreeChart avgFitnessChart = ChartFactory.createXYLineChart(
//                "Average Fitness vs Generations",
//                "Generation",
//                "Average Fitness",
//                avgFitnessDataset
//        );
//        ChartPanel avgFitnessChartPanel = new ChartPanel(avgFitnessChart);
//        tabbedPane.addTab("Average Fitness", avgFitnessChartPanel);
//
//        // Split pane for table and charts
//        JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, tabbedPane);
//        topSplitPane.setResizeWeight(0.5); // Adjusts size proportionally
//
//        // Label for result (actual generations and time)
//        resultLabel = new JLabel("Generations needed: 0 | Time: 0.000 s");
//
//        // Bottom pane for status
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        bottomPanel.add(resultLabel);
//
//        // Main split pane for top and bottom sections
//        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplitPane, bottomPanel);
//        mainSplitPane.setResizeWeight(0.85); // Adjusts vertical size proportionally
//
//        add(mainSplitPane, BorderLayout.CENTER);
//
//        // Start button listener
//        startButton.addActionListener(new StartButtonListener());
//    }
//
//    private class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            tableModel.setRowCount(0); // Clears previous data
//            bestFitnessSeries.clear(); // Clear previous chart data
//            avgFitnessSeries.clear();
//
//            try {
//                String goal = goalField.getText();
//                int popSize = Integer.parseInt(popSizeField.getText());
//                int numGenerations = Integer.parseInt(generationsField.getText());
//                double mutateProb = Double.parseDouble(mutateProbField.getText());
//
//                // Validate inputs
//                if (goal.isEmpty() || popSize <= 0 || numGenerations <= 0 || mutateProb <= 0 || mutateProb > 1) {
//                    throw new IllegalArgumentException("Please enter valid input values.");
//                }
//
//                // Run the genetic algorithm
//                long startTime = System.nanoTime();
//                boolean goalReached = runGeneticAlgorithm(goal, popSize, numGenerations, mutateProb);
//                long endTime = System.nanoTime();
//
//                double elapsedTime = (endTime - startTime) / 1_000_000_000.0; // Convert to seconds
//                resultLabel.setText(goalReached
//                        ? String.format("Goal reached! Generations needed: %d | Time: %.3f s", numGenerations, elapsedTime)
//                        : String.format("Goal not reached in %d generations. | Time: %.3f s", numGenerations, elapsedTime));
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(null, "Please enter valid numerical values.", "Input Error",
//                        JOptionPane.ERROR_MESSAGE);
//            } catch (IllegalArgumentException ex) {
//                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    private boolean runGeneticAlgorithm(String goal, int popSize, int numGenerations, double mutateProb) {
//        Population population = new Population(goal, popSize, numGenerations, mutateProb);
//
//        for (int g = 0; g < numGenerations; g++) {
//            Population.GenerationData data = population.iterateOneGeneration(g);
//
//            SwingUtilities.invokeLater(() -> {
//                tableModel.addRow(new Object[]{
//                        data.generation,
//                        data.bestFitness,
//                        data.avgFitness,
//                        data.bestGuess,
//                        data.adaptiveMutateProb
//                });
//                bestFitnessSeries.add(data.generation, data.bestFitness);
//                avgFitnessSeries.add(data.generation, data.avgFitness);
//            });
//
//            if (data.bestFitness == goal.length()) {
//                return true; // Goal reached
//            }
//        }
//        return false; // Goal not reached within available generations
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GeneticAlgorithmGUI gui = new GeneticAlgorithmGUI();
//            gui.setVisible(true);
//        });
//    }
//}
//package gui_with_visualization;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class GeneticAlgorithmGUI extends JFrame {
//    private JTextField goalField;
//    private JTextField popSizeField;
//    private JTextField generationsField;
//    private JTextField mutateProbField;
//    private JButton startButton;
//    private JTable table;
//    private DefaultTableModel tableModel;
//    private JLabel resultLabel;
//    private XYSeries bestFitnessSeries;
//    private XYSeries avgFitnessSeries;
//
//    public GeneticAlgorithmGUI() {
//        setTitle("Genetic Algorithm Simulator");
//        setSize(1200, 800);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Input panel at the top
//        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//
//        inputPanel.add(new JLabel("String to Guess:"));
//        goalField = new JTextField(10);
//        inputPanel.add(goalField);
//
//        inputPanel.add(new JLabel("Organisms per Generation:"));
//        popSizeField = new JTextField(5);
//        inputPanel.add(popSizeField);
//
//        inputPanel.add(new JLabel("Number of Generations:"));
//        generationsField = new JTextField(5);
//        inputPanel.add(generationsField);
//
//        inputPanel.add(new JLabel("Mutation Probability:"));
//        mutateProbField = new JTextField(5);
//        inputPanel.add(mutateProbField);
//
//        startButton = new JButton("Start");
//        inputPanel.add(startButton);
//
//        add(inputPanel, BorderLayout.NORTH);
//
//        // Table to display results
//        tableModel = new DefaultTableModel(
//                new String[]{"Generation", "Best Fitness", "Average Fitness", "Best Guess", "Mutation Probability"}, 0);
//        table = new JTable(tableModel);
//        JScrollPane tableScrollPane = new JScrollPane(table);
//
//        // Create chart datasets
//        bestFitnessSeries = new XYSeries("Best Fitness");
//        avgFitnessSeries = new XYSeries("Average Fitness");
//
//        // Tabbed pane for charts
//        JTabbedPane tabbedPane = new JTabbedPane();
//
//        // Chart for Best Fitness
//        XYSeriesCollection bestFitnessDataset = new XYSeriesCollection(bestFitnessSeries);
//        JFreeChart bestFitnessChart = ChartFactory.createXYLineChart(
//                "Best Fitness vs Generations",
//                "Generation",
//                "Best Fitness",
//                bestFitnessDataset
//        );
//        ChartPanel bestFitnessChartPanel = new ChartPanel(bestFitnessChart);
//        tabbedPane.addTab("Best Fitness", bestFitnessChartPanel);
//
//        // Chart for Average Fitness
//        XYSeriesCollection avgFitnessDataset = new XYSeriesCollection(avgFitnessSeries);
//        JFreeChart avgFitnessChart = ChartFactory.createXYLineChart(
//                "Average Fitness vs Generations",
//                "Generation",
//                "Average Fitness",
//                avgFitnessDataset
//        );
//        ChartPanel avgFitnessChartPanel = new ChartPanel(avgFitnessChart);
//        tabbedPane.addTab("Average Fitness", avgFitnessChartPanel);
//
//        // Split pane for table and charts
//        JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, tabbedPane);
//        topSplitPane.setResizeWeight(0.5); // Adjusts size proportionally
//
//        // Label for result (actual generations and time)
//        resultLabel = new JLabel("Generations needed: 0 | Time: 0.000 s");
//
//        // Bottom pane for status
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        bottomPanel.add(resultLabel);
//
//        // Main split pane for top and bottom sections
//        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplitPane, bottomPanel);
//        mainSplitPane.setResizeWeight(0.85); // Adjusts vertical size proportionally
//
//        add(mainSplitPane, BorderLayout.CENTER);
//
//        // Start button listener
//        startButton.addActionListener(new StartButtonListener());
//    }
//
//    private class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            tableModel.setRowCount(0); // Clears previous data
//            bestFitnessSeries.clear(); // Clear previous chart data
//            avgFitnessSeries.clear();
//
//            try {
//                String goal = goalField.getText();
//                int popSize = Integer.parseInt(popSizeField.getText());
//                int numGenerations = Integer.parseInt(generationsField.getText());
//                double mutateProb = Double.parseDouble(mutateProbField.getText());
//
//                // Validate inputs
//                if (goal.isEmpty() || popSize <= 0 || numGenerations <= 0 || mutateProb <= 0 || mutateProb > 1) {
//                    throw new IllegalArgumentException("Please enter valid input values.");
//                }
//
//                // Run the genetic algorithm
//                long startTime = System.nanoTime();
//                int actualGenerations = runGeneticAlgorithm(goal, popSize, numGenerations, mutateProb);
//                long endTime = System.nanoTime();
//
//                double elapsedTime = (endTime - startTime) / 1_000_000_000.0; // Convert to seconds
//
//                if (actualGenerations < numGenerations) {
//                    resultLabel.setText(
//                            String.format("Goal reached! Generations needed: %d | Time: %.3f s", actualGenerations, elapsedTime));
//                } else {
//                    resultLabel.setText(
//                            String.format("Goal not reached in %d generations. | Time: %.3f s", numGenerations, elapsedTime));
//                }
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(null, "Please enter valid numerical values.", "Input Error",
//                        JOptionPane.ERROR_MESSAGE);
//            } catch (IllegalArgumentException ex) {
//                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    private int runGeneticAlgorithm(String goal, int popSize, int numGenerations, double mutateProb) {
//        Population population = new Population(goal, popSize, numGenerations, mutateProb);
//
//        for (int g = 0; g < numGenerations; g++) {
//            Population.GenerationData data = population.iterateOneGeneration(g);
//
//            SwingUtilities.invokeLater(() -> {
//                tableModel.addRow(new Object[]{
//                        data.generation,
//                        data.bestFitness,
//                        data.avgFitness,
//                        data.bestGuess,
//                        data.adaptiveMutateProb
//                });
//                bestFitnessSeries.add(data.generation, data.bestFitness);
//                avgFitnessSeries.add(data.generation, data.avgFitness);
//            });
//
//            if (data.bestFitness == goal.length()) {
//                return g + 1; // Return the generation number where the goal was reached
//            }
//        }
//        return numGenerations; // Return max generations if the goal wasn't reached
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GeneticAlgorithmGUI gui = new GeneticAlgorithmGUI();
//            gui.setVisible(true);
//        });
//    }
//}
package gui_with_visualization;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneticAlgorithmGUI extends JFrame {
    private JTextField goalField;
    private JTextField popSizeField;
    private JTextField generationsField;
    private JTextField mutateProbField;
    private JButton startButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel resultLabel;
    private XYSeries bestFitnessSeries;
    private XYSeries avgFitnessSeries;

    public GeneticAlgorithmGUI() {
        setTitle("Genetic Algorithm Simulator");
        setSize(1920, 1080); // Updated window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel at the top
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        inputPanel.add(new JLabel("String to Guess:"));
        goalField = new JTextField(30);
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
        tableModel = new DefaultTableModel(
                new String[]{"Generation", "Best Fitness", "Average Fitness", "Best Guess", "Mutation Probability"}, 0);
        table = new JTable(tableModel);

        // Adjust column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120);  // Generation (slightly wider)
        columnModel.getColumn(1).setPreferredWidth(135);  // Best Fitness (slightly wider)
        columnModel.getColumn(2).setPreferredWidth(140);  // Average Fitness (slightly wider)
        columnModel.getColumn(3).setPreferredWidth(325);  // Best Guess (more space)
        columnModel.getColumn(4).setPreferredWidth(200);  // Mutation Probability (slightly wider)

        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create chart datasets
        bestFitnessSeries = new XYSeries("Best Fitness");
        avgFitnessSeries = new XYSeries("Average Fitness");

        // Tabbed pane for charts
        JTabbedPane tabbedPane = new JTabbedPane();

        // Chart for Best Fitness
        XYSeriesCollection bestFitnessDataset = new XYSeriesCollection(bestFitnessSeries);
        JFreeChart bestFitnessChart = ChartFactory.createXYLineChart(
                "Best Fitness vs Generations",
                "Generation",
                "Best Fitness",
                bestFitnessDataset
        );
        ChartPanel bestFitnessChartPanel = new ChartPanel(bestFitnessChart);
        tabbedPane.addTab("Best Fitness", bestFitnessChartPanel);

        // Chart for Average Fitness
        XYSeriesCollection avgFitnessDataset = new XYSeriesCollection(avgFitnessSeries);
        JFreeChart avgFitnessChart = ChartFactory.createXYLineChart(
                "Average Fitness vs Generations",
                "Generation",
                "Average Fitness",
                avgFitnessDataset
        );
        ChartPanel avgFitnessChartPanel = new ChartPanel(avgFitnessChart);
        tabbedPane.addTab("Average Fitness", avgFitnessChartPanel);

        // Split pane for table and charts
        JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, tabbedPane);
        topSplitPane.setResizeWeight(0.5); // Adjusts size proportionally

        // Label for result (actual generations and time)
        resultLabel = new JLabel("Generations needed: 0 | Time: 0.000 s");

        // Bottom pane for status
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(resultLabel);

        // Main split pane for top and bottom sections
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplitPane, bottomPanel);
        mainSplitPane.setResizeWeight(0.85); // Adjusts vertical size proportionally
        mainSplitPane.setDividerLocation(800); // Sets the divider location higher

        add(mainSplitPane, BorderLayout.CENTER);

        // Start button listener
        startButton.addActionListener(new StartButtonListener());
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tableModel.setRowCount(0); // Clears previous data
            bestFitnessSeries.clear(); // Clear previous chart data
            avgFitnessSeries.clear();

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
                long startTime = System.nanoTime();
                int actualGenerations = runGeneticAlgorithm(goal, popSize, numGenerations, mutateProb);
                long endTime = System.nanoTime();

                double elapsedTime = (endTime - startTime) / 1_000_000_000.0; // Convert to seconds

                if (actualGenerations < numGenerations) {
                    resultLabel.setText(
                            String.format("Goal reached! Generations needed: %d | Time: %.3f s", actualGenerations, elapsedTime));
                } else {
                    resultLabel.setText(
                            String.format("Goal not reached in %d generations. | Time: %.3f s", numGenerations, elapsedTime));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numerical values.", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int runGeneticAlgorithm(String goal, int popSize, int numGenerations, double mutateProb) {
        Population population = new Population(goal, popSize, numGenerations, mutateProb);

        for (int g = 0; g < numGenerations; g++) {
            Population.GenerationData data = population.iterateOneGeneration(g);

            SwingUtilities.invokeLater(() -> {
                tableModel.addRow(new Object[]{
                        data.generation,
                        data.bestFitness,
                        data.avgFitness,
                        data.bestGuess,
                        String.format("%.6f", data.adaptiveMutateProb).replaceAll("0+$", "")
                });
                bestFitnessSeries.add(data.generation, data.bestFitness);
                avgFitnessSeries.add(data.generation, data.avgFitness);
            });

            if (data.bestFitness == goal.length()) {
                return g + 1; // Return the generation number where the goal was reached
            }
        }
        return numGenerations; // Return max generations if the goal wasn't reached
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GeneticAlgorithmGUI gui = new GeneticAlgorithmGUI();
            gui.setVisible(true);
        });
    }
}
