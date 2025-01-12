package org.example.UIProcessor.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.DataBase.DataStorage;
import org.example.Web.DataBase.FunctionsConverter;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private String username;
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        if (isInputValid(username, password)) {
            if (authenticate(username, password)) {
                showAlert("Success", "Login successful!", Alert.AlertType.INFORMATION);
                this.username = username;
                openCalculator();
            } else {
                showAlert("Error", "Invalid username or password!", Alert.AlertType.ERROR);
            }
        }
    }

    public void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (isInputValid(username, password)) {
            String result = registerUser(username, password);
            switch (result) {
                case "SUCCESS" -> showAlert("Success", "Registration successful! You can now log in.", Alert.AlertType.INFORMATION);
                case "USERNAME_EXISTS" -> showAlert("Error", "Username already exists! Please choose a different one.", Alert.AlertType.ERROR);
                default -> {
                    if (result.startsWith("PASSWORD_EXISTS")) {
                        String existingUser = result.split(" ", 2)[1];
                        showAlert("Error", "The password is already in use by \"" + existingUser + "\"", Alert.AlertType.ERROR);
                    } else {
                        showAlert("Error", "Registration failed due to a database error!", Alert.AlertType.ERROR);
                    }
                }
            }
        }
    }

    private boolean isInputValid(String username, String password) {
        if (username.length() < 3) {
            showAlert("Error", "Username must be at least 3 characters long!", Alert.AlertType.ERROR);
            return false;
        }
        if (password.length() < 5) {
            showAlert("Error", "Password must be at least 5 characters long!", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            loadFunctionsFromDatabase(connection, username);
            return resultSet.next();
        } catch (Exception e) {
            handleDatabaseError(e, "Database error during login!");
        }
        return false;
    }

    private String registerUser(String username, String password) {
        try (Connection connection = getConnection()) {
            if (recordExists(connection, "SELECT * FROM users WHERE username = ?", username)) {
                return "USERNAME_EXISTS";
            }
            if (recordExists(connection, "SELECT username FROM users WHERE password = ?", password)) {
                return "PASSWORD_EXISTS " + getExistingUser(connection, password);
            }
            executeUpdate(connection, "INSERT INTO users (username, password) VALUES (?, ?)", username, password);
            return "SUCCESS";
        } catch (Exception e) {
            handleDatabaseError(e, "Registration failed!");
        }
        return "ERROR";
    }

    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/calculator";
        String dbUser = System.getenv("DATASOURCE_USERNAME");
        String dbPassword = System.getenv("DATASOURCE_PASSWORD");
        return DriverManager.getConnection(url, dbUser, dbPassword);
    }

    private boolean recordExists(Connection connection, String query, String value) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    private String getExistingUser(Connection connection, String password) throws Exception {
        String query = "SELECT username FROM users WHERE password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getString("username") : null;
        }
    }

    public void loadFunctionsFromDatabase(Connection connection, String username) throws SQLException {
        String query = "SELECT functions FROM users WHERE username = ?";
        String jsonResult = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    jsonResult = resultSet.getString("functions");
                }
            }
        }

        if (jsonResult == null || jsonResult.trim().isEmpty()) {
            System.out.println("No functions found or JSON is empty for username: " + username);
            DataStorage.getInstance().setFunctions(new HashMap<>());
        } else {
            try {
                Map<String, String> functionsMap = new FunctionsConverter().convertToEntityAttribute(jsonResult);
                DataStorage.getInstance().setFunctions(functionsMap);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("Failed to parse JSON for username: " + username);
                DataStorage.getInstance().setFunctions(new HashMap<>());
            }
        }
    }



    private void executeUpdate(Connection connection, String query, String... values) throws Exception {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < values.length; i++) {
                statement.setString(i + 1, values[i]);
            }
            statement.executeUpdate();
        }
    }

    private void handleDatabaseError(Exception e, String message) {
        e.printStackTrace();
        showAlert("Error", message, Alert.AlertType.ERROR);
    }

    private void openCalculator() {
        try {
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/Main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 900);

            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("Calculator");
            dashboardStage.setScene(scene);

            dashboardStage.setOnCloseRequest(event -> {
                boolean success = saveData();
                if (!success) {
                    event.consume();
                    System.out.println("Closing canceled: data save failed.");
                }
            });

            dashboardStage.show();
        } catch (Exception e) {
            handleDatabaseError(e, "Failed to open the calculator!");
        }
    }


    private boolean saveData() {
        try {
            System.out.println("Saving data...");
            Map<String, String> functions = DataStorage.getInstance().getFunctions();
            String jsonFunctions = new FunctionsConverter().convertToDatabaseColumn(functions);

            try (Connection connection = getConnection()) {
                String query = "UPDATE users SET functions = ? WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, jsonFunctions);
                    statement.setString(2, this.username);
                    statement.executeUpdate();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
