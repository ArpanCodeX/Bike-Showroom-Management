package showroom.auth.util;

import java.sql.*;

public class DatabaseTest {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bikeshowroom";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "system";

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Test connection
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                System.out.println("Database connection successful!");
                
                // Get table structure
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "users", null);
                
                if (!tables.next()) {
                    System.out.println("Users table does not exist! Creating it...");
                    // Create the users table if it doesn't exist
                    try (Statement stmt = conn.createStatement()) {
                        String createTableSQL = 
                            "CREATE TABLE users (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(100) NOT NULL, " +
                            "phone VARCHAR(15) NOT NULL, " +
                            "address TEXT NOT NULL, " +
                            "email VARCHAR(100) NOT NULL UNIQUE, " +
                            "password VARCHAR(100) NOT NULL" +
                            ")";
                        stmt.executeUpdate(createTableSQL);
                        System.out.println("Users table created successfully!");
                    }
                } else {
                    System.out.println("Users table exists. Checking structure...");
                    ResultSet columns = metaData.getColumns(null, null, "users", null);
                    while (columns.next()) {
                        String columnName = columns.getString("COLUMN_NAME");
                        String columnType = columns.getString("TYPE_NAME");
                        System.out.println(columnName + " : " + columnType);
                    }
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