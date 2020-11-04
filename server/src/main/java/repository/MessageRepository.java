package repository;

import conversation.InitialConversation;

import java.sql.*;

public class MessageRepository implements Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String CREATE_CONVERSATION_QUERY = "INSERT INTO messages (ticket_id) VALUES (?)";

    public static InitialConversation submit(int ticketId) {
        InitialConversation initialConversation = null;
        int conversationId = 0;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CONVERSATION_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ticketId);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                conversationId = resultSet.getInt(1);
            }

            initialConversation = new InitialConversation(ticketId, conversationId);

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return initialConversation;
    }
}
