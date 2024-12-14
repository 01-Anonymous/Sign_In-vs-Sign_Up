package uz.app.payapp.db;

import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConfirmationCodeDAO {
    static Database database = Database.getInstance();

    @SneakyThrows
    public static void saveConfirmationCode(String email, String confirmationCode) {
        String query = "INSERT INTO confirmation_code(email, code) VALUES (?, ?)";
        System.out.println(query);
        try (PreparedStatement preparedStatement = database.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, confirmationCode);

            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public static void deleteConfirmationCode(String email, String confirmationCode) {
        String query = "DELETE FROM confirmation_code WHERE email = ? AND code = ? AND NOW() - time >= INTERVAL '60 seconds'";
        try (PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, confirmationCode);
            statement.executeUpdate();
        }

    }

    @SneakyThrows
    public static void deleteAllConfirmationCodes(String email) {
        String query = "DELETE FROM confirmation_code WHERE email = ?";

        try (PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            statement.executeUpdate();
        }

    }

    @SneakyThrows
    public static String getConfirmationCode(String email) {
        String query = "SELECT code FROM confirmation_code WHERE email = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("code");
            }
        }
        return "";
    }
}
