package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Declare these as constants for global reuse
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SuperMarket_DB;encrypt=false";
    private static final String USER = "nyda";       // SQL Server username
    private static final String PASSWORD = "123";    // Your actual password

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Connected to SuperMarket_DB successfully!");
            } else {
                System.out.println("❌ Connection returned null!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Connection test failed!");
            e.printStackTrace();
        }
    }

    /**
     * Provides a reusable connection to the database.
     */
    public static Connection getConnection() {
        try {
            // Load SQL Server JDBC driver (optional with JDBC 4+, but safe to include)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Return the connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ SQL Server JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Failed to connect to database!");
            e.printStackTrace();
        }
        return null;
    }
}
