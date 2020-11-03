package repository;

import ticket.TechnicalTicket;

import java.sql.*;

public class TechnicalTicketRepository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String INSERT_TICKET_QUERY = "INSERT INTO it_tickets (name, email, enquiry_type, description, author_id) VALUES (?,?,?,?,?)";

    public static TechnicalTicket submit(int userId, String name, String email, String enquiryType, String description) {
        TechnicalTicket technicalTicket = null;
        int ticketId = 0;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TICKET_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, enquiryType);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, userId);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                ticketId = resultSet.getInt(1);
            }

            technicalTicket = new TechnicalTicket(ticketId, userId, name, email, enquiryType, description);

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return technicalTicket;
    }
}
