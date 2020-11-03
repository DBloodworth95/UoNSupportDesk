package repository;

import ticket.AcademicTicket;

import java.sql.*;

public final class AcademicTicketRepository implements Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String INSERT_TICKET_QUERY = "INSERT INTO academic_tickets (name, email, enquiry_type, description, pathway, year, author_id) VALUES (?,?,?,?,?,?,?)";

    public static AcademicTicket submit(int userId, String name, String email, String enquiryType, String description, String pathway, String year) {
        AcademicTicket academicTicket = null;
        int ticketId = 0;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TICKET_QUERY);
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
}
