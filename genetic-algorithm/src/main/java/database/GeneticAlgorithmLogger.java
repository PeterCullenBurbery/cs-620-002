package database;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GeneticAlgorithmLogger {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/GENETIC_ALGORITHM.localdomain";
    private static final String DB_USER = "GENETIC_ALGORITHM";
    private static final String DB_PASSWORD = "1234";

    private Connection connection;

    public GeneticAlgorithmLogger() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public String logStart(String input, int organisms, int generations, double mutationRate) throws SQLException {
        String insertSQL = """
            BEGIN 
                INSERT INTO GENETIC_ALGORITHM_RUN (GENETIC_ALGORITHM_input, organisms, generations, mutation_rate, start_time)
                VALUES (?, ?, ?, ?, ?)
                RETURNING GENETIC_ALGORITHM_RUN_id INTO ?;
            END;
        """;

        try (CallableStatement stmt = connection.prepareCall(insertSQL)) {
            stmt.setString(1, input);
            stmt.setInt(2, organisms);
            stmt.setInt(3, generations);
            stmt.setDouble(4, mutationRate);
            stmt.setTimestamp(5, Timestamp.from(ZonedDateTime.now(ZoneId.systemDefault()).toInstant()));
            stmt.registerOutParameter(6, Types.VARCHAR); // Register the OUT parameter
            stmt.execute();
            return stmt.getString(6); // Get the generated ID
        }
    }


    public void logCompletion(String runId, int numGenerationsToConverge, boolean converged) throws SQLException {
        String updateSQL = """
            UPDATE GENETIC_ALGORITHM_RUN
            SET number_of_generations_to_converge = ?, converged = ?, end_time = ?
            WHERE GENETIC_ALGORITHM_RUN_id = ?""";

        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setInt(1, numGenerationsToConverge);
            stmt.setInt(2, converged ? 1 : 0);
            stmt.setTimestamp(3, Timestamp.from(ZonedDateTime.now(ZoneId.systemDefault()).toInstant()));
            stmt.setString(4, runId);
            stmt.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null) connection.close();
    }
}
