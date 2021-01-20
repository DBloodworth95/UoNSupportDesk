package repository;

import ticket.TechnicalTicket;
import ticket.TicketAssignmentUpdate;

import java.sql.*;

public final class TechnicalTicketRepository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String INSERT_TICKET_QUERY = "INSERT INTO it_tickets (name, email, enquiry_type, description, author_id) VALUES (?,?,?,?,?)";

    private static final String UPDATE_TICKET_ASSIGNEE_QUERY = "UPDATE it_tickets SET participant_id = ? WHERE ticket_id = ?";


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

    public static TicketAssignmentUpdate submitTicketAssignment(int ticketId, int assigneeId, String assigneeName, String ticketType) {
        TicketAssignmentUpdate ticketAssignmentUpdate = null;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TICKET_ASSIGNEE_QUERY);
            preparedStatement.setInt(1, assigneeId);
            preparedStatement.setInt(2, ticketId);
            preparedStatement.execute();

            ticketAssignmentUpdate = new TicketAssignmentUpdate(ticketId, assigneeId, assigneeName, ticketType);

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ticketAssignmentUpdate;
    }
}
