package uz.app.payapp.db;

import lombok.SneakyThrows;
import uz.app.payapp.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    static Database database = Database.getInstance();



    @SneakyThrows
    public static List<User> getUsers() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        ResultSet resultSet = database.getStatement().executeQuery(query);
        while (resultSet.next()) {
            User user = new User();
            user.setId(Integer.parseInt(resultSet.getString("id")));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setPhone(resultSet.getString("phone"));
            users.add(user);
        }
        return users;
    }

    @SneakyThrows
    public static void resetPass (String email, String password) {
        String query = "UPDATE users SET password = ? WHERE email = ?";
        try (PreparedStatement statement = database.getConnection().prepareStatement(query)) {
            statement.setString(1, password);
            statement.setString(2, email);
            statement.executeUpdate();
        }
    }

    @SneakyThrows
    public static boolean checkUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = '%s'".formatted(email);
        ResultSet resultSet = database.getStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("email").equals(email);
        }
        return false;
    }
    @SneakyThrows
    public static boolean checkUserByPhone(String phone) {
        String query = "SELECT * FROM users WHERE phone = '%s'".formatted(phone);
        ResultSet resultSet = database.getStatement().executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString("phone").equals(phone);
        }
        return false;
    }
}
