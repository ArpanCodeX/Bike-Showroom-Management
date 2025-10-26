package showroom.auth.util;

import java.sql.*;

public class DatabaseReset {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bikeshowroom";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "system";

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect and reset table
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                System.out.println("Database connection successful!");
                
                try (Statement stmt = conn.createStatement()) {
                    // Drop the existing users table
                    stmt.executeUpdate("DROP TABLE IF EXISTS users");
                    System.out.println("Old users table dropped.");
                    
                    // Create new users table with correct structure
                    String createTableSQL = 
                        "CREATE TABLE users (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(100) NOT NULL, " +
                        "phone VARCHAR(15) NOT NULL, " +
                        "address TEXT NOT NULL, " +
                        "email VARCHAR(100) NOT NULL UNIQUE, " +
                        "password VARCHAR(100) NOT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
                    
                    stmt.executeUpdate(createTableSQL);
                    System.out.println("New users table created successfully!");
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}