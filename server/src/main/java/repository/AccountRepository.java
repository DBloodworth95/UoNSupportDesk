package repository;

import account.AccessLevel;
import account.Account;
import account.ProfilePicture;

import java.io.*;
import java.sql.*;

public final class AccountRepository implements Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String FIND_USERNAME_QUERY = "SELECT name FROM users WHERE user_id=?";

    private static final String UPDATE_PROFILE_PICTURE_QUERY = "UPDATE users SET profile_picture=? WHERE user_id=?";

    private static final String FIND_USER_QUERY = "SELECT * FROM users WHERE email=? AND password=?";

    public static Account find(String username, String password) {
        Account account = null;
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD); //Create a connection to the database using the relevant db credentials
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_QUERY); //Generate a prepared statement to prevent SQL injection.
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery(); //Execute the query and store the result set.

            while (resultSet.next()) { //Iterate through the result set and store them locally
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");
                String passwordToCheckAgainst = resultSet.getString("password");
                int userId = resultSet.getInt("user_id");
                int accessLevel = resultSet.getInt("access_level");
                InputStream byteArrayInputStream = resultSet.getBinaryStream("profile_picture");

                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream(); //Create a bytebuffer for the profile picture.
                int byteRead;
                byte[] profilePicture = new byte[16384]; //Create a byte array to store the profile picture bytes which were fetched from the db

                while ((byteRead = byteArrayInputStream.read(profilePicture, 0, profilePicture.length)) != -1) { //Whilst there are still bytes to be read.
                    byteBuffer.write(profilePicture, 0, byteRead); //Write the bytes from the profile picture into a byte array.
                }

                if (password.equals(passwordToCheckAgainst)) { //If the password retrieved from the db matches the password from the login request.
                    account = new Account(userId, name, email, password, AccessLevel.fromInt(accessLevel), profilePicture); //Create an account object for the "Login Success" response.
                }
            }

            resultSet.close();//Close the result set, prepared statement and connection to the database.
            preparedStatement.close();
            connection.close();
        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
        return account; //Return the account for the "Login Success" Response.
    }

    public static String getNameOfAccountHolder(int participantId) {
        String accountHolderName = "empty";
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USERNAME_QUERY);
            preparedStatement.setInt(1, participantId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                accountHolderName = resultSet.getString("name");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return accountHolderName;
    }

    public static ProfilePicture submitProfilePicture(int userId, byte[] imageAsBytes) {
        ProfilePicture profilePicture = null;
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(imageAsBytes);

            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROFILE_PICTURE_QUERY);
            preparedStatement.setBinaryStream(1, byteStream, imageAsBytes.length);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();

            profilePicture = new ProfilePicture(userId, imageAsBytes);

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return profilePicture;
    }
}
