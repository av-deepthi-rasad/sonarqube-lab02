package com.example;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        UserService userService = new UserService();

        runCalculator(calculator);
        runUserOperations(userService);
    }

    private static void runCalculator(Calculator calculator) {
        try {
            int result = calculator.calculate(10, 5, Calculator.Operation.ADD);
            LOGGER.info(() -> "Calculation result: " + result);
        } catch (IllegalArgumentException | ArithmeticException ex) {
            LOGGER.log(Level.WARNING, "Calculation failed", ex);
        }
    }

    private static void runUserOperations(UserService userService) {
        String username = "admin";

        try {
            userService.findUser(username);

            // Safety gate for destructive operation
            if (isDeleteAllowed()) {
                userService.deleteUser(username);
                LOGGER.info("User deleted successfully");
            } else {
                LOGGER.warning("Delete operation blocked by safety check");
            }

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Database operation failed", ex);
        }
    }

    /**
     * Safety gate for destructive operations
     */
    private static boolean isDeleteAllowed() {
        return Boolean.parseBoolean(
            System.getenv().getOrDefault("ALLOW_USER_DELETE", "false")
        );
    }
}
