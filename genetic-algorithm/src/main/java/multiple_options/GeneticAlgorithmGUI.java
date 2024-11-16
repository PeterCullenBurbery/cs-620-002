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
//package multiple_options;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import javax.swing.table.DefaultTableModel;
//import org.jfree.chart.*;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
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
//        setSize(1920, 1080); // Updated window size
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new CardLayout());
//
//        // Main Selection Panel
//        JPanel selectionPanel = new JPanel(new GridLayout(2, 1, 10, 10));
//        JButton tableAndPlotsButton = new JButton("See a Table and Plots");
//        JButton compareParamsButton = new JButton("Compare Different Parameters");
//
//        selectionPanel.add(tableAndPlotsButton);
//        selectionPanel.add(compareParamsButton);
//
//        add(selectionPanel, "SelectionPanel");
//
//        // Table and Plot GUI setup
//        JPanel tableAndPlotPanel = createTableAndPlotPanel();
//        add(tableAndPlotPanel, "TableAndPlotPanel");
//
//        // Action Listeners for Selection Buttons
//        tableAndPlotsButton.addActionListener(e -> {
//            CardLayout cl = (CardLayout) getContentPane().getLayout();
//            cl.show(getContentPane(), "TableAndPlotPanel");
//        });
//
//        compareParamsButton.addActionListener(e -> {
//            JOptionPane.showMessageDialog(this, "Compare Parameters functionality coming soon!", 
//                    "Info", JOptionPane.INFORMATION_MESSAGE);
//        });
//
//        // Display the initial selection panel
//        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "SelectionPanel");
//    }
//
//    private JPanel createTableAndPlotPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//
//        // Input panel at the top
//        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        inputPanel.add(new JLabel("String to Guess:"));
//        goalField = new JTextField(30);
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
//        panel.add(inputPanel, BorderLayout.NORTH);
//
//        // Table to display results
//        tableModel = new DefaultTableModel(
//                new String[]{"Generation", "Best Fitness", "Average Fitness", "Best Guess", "Mutation Probability"}, 0);
//        table = new JTable(tableModel);
//
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
//                bestFitnessDataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
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
//                avgFitnessDataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );
//        ChartPanel avgFitnessChartPanel = new ChartPanel(avgFitnessChart);
//        tabbedPane.addTab("Average Fitness", avgFitnessChartPanel);
//
//        // Split pane for table and charts
//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScrollPane, tabbedPane);
//        splitPane.setResizeWeight(0.5); 
//
//        // Bottom pane for status
//        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        resultLabel = new JLabel("Generations needed: 0 | Time: 0.000 s");
//        bottomPanel.add(resultLabel);
//
//        // Main panel layout
//        panel.add(splitPane, BorderLayout.CENTER);
//        panel.add(bottomPanel, BorderLayout.SOUTH);
//
//        // Start button listener
//        startButton.addActionListener(new StartButtonListener());
//
//        return panel;
//    }
//
//    private class StartButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            tableModel.setRowCount(0);
//            bestFitnessSeries.clear();
//            avgFitnessSeries.clear();
//
//            try {
//                String goal = goalField.getText();
//                int popSize = Integer.parseInt(popSizeField.getText());
//                int numGenerations = Integer.parseInt(generationsField.getText());
//                double mutateProb = Double.parseDouble(mutateProbField.getText());
//
//                if (goal.isEmpty() || popSize <= 0 || numGenerations <= 0 || mutateProb <= 0 || mutateProb > 1) {
//                    throw new IllegalArgumentException("Please enter valid input values.");
//                }
//
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
//        long startTime = System.nanoTime();
//
//        for (int g = 0; g < numGenerations; g++) {
//            Population.GenerationData data = population.iterateOneGeneration(g);
//
//            SwingUtilities.invokeLater(() -> {
//                tableModel.addRow(new Object[]{
//                        data.generation, data.bestFitness, data.avgFitness, data.bestGuess,
//                        String.format("%.6f", data.adaptiveMutateProb)
//                });
//                bestFitnessSeries.add(data.generation, data.bestFitness);
//                avgFitnessSeries.add(data.generation, data.avgFitness);
//            });
//
//            if (data.bestFitness == goal.length()) {
//                long endTime = System.nanoTime();
//                double elapsedTime = (endTime - startTime) / 1_000_000_000.0;
//                resultLabel.setText(String.format("Goal reached in %d generations | Time: %.3f s", g + 1, elapsedTime));
//                return;
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
//package multiple_options;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.DoubleStream;
//
//public class GeneticAlgorithmGUI extends JFrame {
//    private JTextField goalField, popSizeField, generationsField, mutateRatesField, trialsField;
//    private JButton compareButton;
//    private JTable compareTable;
//    private DefaultTableModel compareTableModel;
//
//    public GeneticAlgorithmGUI() {
//        setTitle("Genetic Algorithm Parameter Comparison");
//        setSize(1200, 800);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Input panel
//        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
//
//        inputPanel.add(new JLabel("String to Guess:"));
//        goalField = new JTextField("For God so loved the world", 20);
//        inputPanel.add(goalField);
//
//        inputPanel.add(new JLabel("Organisms per Generation:"));
//        popSizeField = new JTextField("100", 5);
//        inputPanel.add(popSizeField);
//
//        inputPanel.add(new JLabel("Number of Generations:"));
//        generationsField = new JTextField("10000", 5);
//        inputPanel.add(generationsField);
//
//        inputPanel.add(new JLabel("Mutation Rates (comma-separated):"));
//        mutateRatesField = new JTextField("0.01, 0.05", 10);
//        inputPanel.add(mutateRatesField);
//
//        inputPanel.add(new JLabel("Number of Trials:"));
//        trialsField = new JTextField("30", 5);
//        inputPanel.add(trialsField);
//
//        compareButton = new JButton("Compare Parameters");
//        inputPanel.add(compareButton);
//
//        add(inputPanel, BorderLayout.NORTH);
//
//        // Table to display comparison results
//        compareTableModel = new DefaultTableModel();
//        compareTable = new JTable(compareTableModel);
//        add(new JScrollPane(compareTable), BorderLayout.CENTER);
//
//        // Compare button action listener
//        compareButton.addActionListener(new CompareButtonListener());
//    }
//
//    private class CompareButtonListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                String goal = goalField.getText();
//                int popSize = Integer.parseInt(popSizeField.getText());
//                int numGenerations = Integer.parseInt(generationsField.getText());
//                String[] rates = mutateRatesField.getText().split(",");
//                int numTrials = Integer.parseInt(trialsField.getText());
//
//                // Prepare table
//                compareTableModel.setRowCount(0);
//                compareTableModel.setColumnCount(0);
//                compareTableModel.addColumn("Statistic");
//
//                for (String rate : rates) {
//                    compareTableModel.addColumn("Mutation " + rate.trim());
//                }
//
//                // Run trials and gather statistics for each mutation rate
//                List<double[]> stats = new ArrayList<>();
//
//                for (String rateStr : rates) {
//                    double rate = Double.parseDouble(rateStr.trim());
//                    double[] generationsToReachGoal = new double[numTrials];
//
//                    for (int i = 0; i < numTrials; i++) {
//                        Population population = new Population(goal, popSize, numGenerations, rate);
//                        int actualGenerations = runGeneticAlgorithm(population, goal, numGenerations);
//                        generationsToReachGoal[i] = actualGenerations;
//                    }
//
//                    // Calculate statistics for this mutation rate
//                    double min = DoubleStream.of(generationsToReachGoal).min().orElse(0);
//                    double max = DoubleStream.of(generationsToReachGoal).max().orElse(0);
//                    double avg = DoubleStream.of(generationsToReachGoal).average().orElse(0);
//                    double stdDev = calculateStandardDeviation(generationsToReachGoal, avg);
//                    double q1 = calculatePercentile(generationsToReachGoal, 25);
//                    double q3 = calculatePercentile(generationsToReachGoal, 75);
//
//                    stats.add(new double[]{min, max, avg, q1, q3, stdDev});
//                }
//
//                // Populate table with statistics
//                String[] statNames = {"Min", "Max", "Average", "Q1", "Q3", "Std Dev"};
//                for (int i = 0; i < statNames.length; i++) {
//                    Object[] row = new Object[rates.length + 1];
//                    row[0] = statNames[i];
//                    for (int j = 0; j < stats.size(); j++) {
//                        row[j + 1] = String.format("%.2f", stats.get(j)[i]);
//                    }
//                    compareTableModel.addRow(row);
//                }
//
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//
//        private int runGeneticAlgorithm(Population population, String goal, int maxGenerations) {
//            for (int g = 0; g < maxGenerations; g++) {
//                Population.GenerationData data = population.iterateOneGeneration(g);
//                if (data.bestFitness == goal.length()) {
//                    return g + 1;
//                }
//            }
//            return maxGenerations;
//        }
//
//        private double calculateStandardDeviation(double[] data, double mean) {
//            return Math.sqrt(DoubleStream.of(data).map(x -> Math.pow(x - mean, 2)).average().orElse(0));
//        }
//
//        private double calculatePercentile(double[] data, double percentile) {
//            double[] sorted = DoubleStream.of(data).sorted().toArray();
//            int index = (int) Math.ceil((percentile / 100.0) * sorted.length) - 1;
//            return sorted[index];
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
package multiple_options;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class GeneticAlgorithmGUI extends JFrame {
    private JTextField goalField, popSizeField, generationsField, mutateRatesField, trialsField;
    private JButton compareButton;
    private JTable compareTable;
    private DefaultTableModel compareTableModel;
    private JLabel progressLabel;

    public GeneticAlgorithmGUI() {
        setTitle("Genetic Algorithm Parameter Comparison");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("String to Guess:"));
        goalField = new JTextField("For God so loved the world", 20);
        inputPanel.add(goalField);

        inputPanel.add(new JLabel("Organisms per Generation:"));
        popSizeField = new JTextField("100", 5);
        inputPanel.add(popSizeField);

        inputPanel.add(new JLabel("Number of Generations:"));
        generationsField = new JTextField("10000", 5);
        inputPanel.add(generationsField);

        inputPanel.add(new JLabel("Mutation Rates (comma-separated):"));
        mutateRatesField = new JTextField("0.01, 0.05", 10);
        inputPanel.add(mutateRatesField);

        inputPanel.add(new JLabel("Number of Trials:"));
        trialsField = new JTextField("30", 5);
        inputPanel.add(trialsField);

        compareButton = new JButton("Compare Parameters");
        inputPanel.add(compareButton);

        add(inputPanel, BorderLayout.NORTH);

        // Progress label
        progressLabel = new JLabel("Progress: 0/0");
        add(progressLabel, BorderLayout.SOUTH);

        // Table to display comparison results
        compareTableModel = new DefaultTableModel();
        compareTable = new JTable(compareTableModel);
        add(new JScrollPane(compareTable), BorderLayout.CENTER);

        // Compare button action listener
        compareButton.addActionListener(new CompareButtonListener());
    }

    private class CompareButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String goal = goalField.getText();
                int popSize = Integer.parseInt(popSizeField.getText());
                int numGenerations = Integer.parseInt(generationsField.getText());
                String[] rates = mutateRatesField.getText().split(",");
                int numTrials = Integer.parseInt(trialsField.getText());

                // Prepare table
                compareTableModel.setRowCount(0);
                compareTableModel.setColumnCount(0);
                compareTableModel.addColumn("Statistic");

                for (String rate : rates) {
                    compareTableModel.addColumn("Mutation " + rate.trim());
                }

                // Run trials and gather statistics for each mutation rate
                new Thread(() -> {
                    List<double[]> stats = new ArrayList<>();
                    int totalRuns = rates.length * numTrials;
                    int completedRuns = 0;

                    for (String rateStr : rates) {
                        double rate = Double.parseDouble(rateStr.trim());
                        double[] generationsToReachGoal = new double[numTrials];

                        for (int i = 0; i < numTrials; i++) {
                            Population population = new Population(goal, popSize, numGenerations, rate);
                            int actualGenerations = runGeneticAlgorithm(population, goal, numGenerations);
                            generationsToReachGoal[i] = actualGenerations;

                            completedRuns++;
                            final int finalCompletedRuns = completedRuns;
                            SwingUtilities.invokeLater(() -> progressLabel.setText(
                                    String.format("Progress: %d/%d", finalCompletedRuns, totalRuns)
                            ));
                        }

                        // Calculate statistics for this mutation rate
                        double min = DoubleStream.of(generationsToReachGoal).min().orElse(0);
                        double max = DoubleStream.of(generationsToReachGoal).max().orElse(0);
                        double avg = DoubleStream.of(generationsToReachGoal).average().orElse(0);
                        double stdDev = calculateStandardDeviation(generationsToReachGoal, avg);
                        double q1 = calculatePercentile(generationsToReachGoal, 25);
                        double q3 = calculatePercentile(generationsToReachGoal, 75);

                        stats.add(new double[]{min, max, avg, q1, q3, stdDev});
                    }

                    // Populate table with statistics
                    String[] statNames = {"Min", "Max", "Average", "Q1", "Q3", "Std Dev"};
                    for (int i = 0; i < statNames.length; i++) {
                        Object[] row = new Object[rates.length + 1];
                        row[0] = statNames[i];
                        for (int j = 0; j < stats.size(); j++) {
                            row[j + 1] = String.format("%.2f", stats.get(j)[i]);
                        }
                        compareTableModel.addRow(row);
                    }
                }).start();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private int runGeneticAlgorithm(Population population, String goal, int maxGenerations) {
            for (int g = 0; g < maxGenerations; g++) {
                Population.GenerationData data = population.iterateOneGeneration(g);
                if (data.bestFitness == goal.length()) {
                    return g + 1;
                }
            }
            return maxGenerations;
        }

        private double calculateStandardDeviation(double[] data, double mean) {
            return Math.sqrt(DoubleStream.of(data).map(x -> Math.pow(x - mean, 2)).average().orElse(0));
        }

        private double calculatePercentile(double[] data, double percentile) {
            double[] sorted = DoubleStream.of(data).sorted().toArray();
            int index = (int) Math.ceil((percentile / 100.0) * sorted.length) - 1;
            return sorted[index];
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GeneticAlgorithmGUI gui = new GeneticAlgorithmGUI();
            gui.setVisible(true);
        });
    }
}
