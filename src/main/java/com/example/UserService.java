package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class UserService {

    private static final String DB_URL = "jdbc:mysql://localhost/db";
    private static final String DB_USER = "root";

    /**
     * Retrieves a database connection using environment variables
     */
    private Connection getConnection() throws SQLException {
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new IllegalStateException("Database password not configured");
        }

        return DriverManager.getConnection(DB_URL, DB_USER, password);
    }

    /**
     * Finds a user by username (safe from SQL Injection)
     */
    public void findUser(String username) throws SQLException {
        validateUsername(username);

        String query = "SELECT id, name, email FROM users WHERE name = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, username);
            ps.executeQuery();
        }
    }

    /**
     * Deletes a user by username (safe from SQL Injection)
     */
    public void deleteUser(String username) throws SQLException {
        validateUsername(username);

        String query = "DELETE FROM users WHERE name = ?";

        try (
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, username);
            ps.executeUpdate();
        }
    }

    /**
     * Validates username input
     */
    private void validateUsername(String username) {
        if (Objects.isNull(username) || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }
    }
}
