package repository;

import account.AccessLevel;
import account.Account;

import java.sql.*;

public final class AccountRepository implements Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE email=? AND password=?";

    public static Account find(String username, String password) {
        Account account = null;
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String passwordToCheckAgainst = resultSet.getString("password");
                int userId = resultSet.getInt("user_id");
                int accessLevel = resultSet.getInt("access_level");

                if (password.equals(passwordToCheckAgainst)) {
                    account = new Account(userId, name, email, password, AccessLevel.fromInt(accessLevel));
                }
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return account;
    }
}
