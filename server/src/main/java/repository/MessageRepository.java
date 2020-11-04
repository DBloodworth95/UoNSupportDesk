package repository;

import conversation.InitialConversation;
import conversation.Message;
import conversation.MessageList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository implements Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String CREATE_CONVERSATION_QUERY = "INSERT INTO messages (ticket_id) VALUES (?)";

    private static final String FIND_ALL_TICKET_MESSAGES = "SELECT * FROM messages WHERE ticket_id=? AND ticket_type=?";

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

    public static MessageList findAll(int ticketId, String ticketType) {
        MessageList messageList = null;
        List<Message> messages = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TICKET_MESSAGES);
            preparedStatement.setInt(1, ticketId);
            preparedStatement.setString(2, ticketType);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String body = resultSet.getString("message");
                int authorId = resultSet.getInt("author_id");
                String timestamp = resultSet.getTimestamp("timestamp").toString();

                Message message = new Message(ticketId, ticketType, body, authorId, timestamp);
                messages.add(message);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

            messageList = new MessageList(ticketId, ticketType, messages);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return messageList;
    }
}
