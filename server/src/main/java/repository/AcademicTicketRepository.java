package repository;

import command.TicketClosedUpdate;
import ticket.AcademicTicket;
import command.TicketAssignmentUpdate;

import java.sql.*;

public final class AcademicTicketRepository implements Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String INSERT_TICKET_QUERY = "INSERT INTO academic_tickets (name, email, enquiry_type, description, pathway, year, author_id) VALUES (?,?,?,?,?,?,?)";

    private static final String UPDATE_TICKET_ASSIGNEE_QUERY = "UPDATE academic_tickets SET participant_id=? WHERE ticket_id=?";

    private static final String CLOSE_TICKET_QUERY = "UPDATE academic_tickets SET archived=? WHERE ticket_id=?";

    public static AcademicTicket submit(int userId, String name, String email, String enquiryType, String description, String pathway, String year) {
        AcademicTicket academicTicket = null;
        int ticketId = 0;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TICKET_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, enquiryType);
            preparedStatement.setString(4, description);
            preparedStatement.setString(5, pathway);
            preparedStatement.setString(6, year);
            preparedStatement.setInt(7, userId);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                ticketId = resultSet.getInt(1);
            }

            academicTicket = new AcademicTicket(ticketId, userId, name, email, enquiryType, description, pathway, year);

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return academicTicket;
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

    public static TicketClosedUpdate closeTicket(int ticketId) {
        TicketClosedUpdate ticketClosedUpdate = null;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(CLOSE_TICKET_QUERY);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, ticketId);
            preparedStatement.execute();

            ticketClosedUpdate = new TicketClosedUpdate(ticketId, "Academic");

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return ticketClosedUpdate;
    }
}
