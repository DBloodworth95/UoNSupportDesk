package repository;

import ticket.UserTicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserTicketRepository implements Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String FIND_ACADEMIC_TICKET_QUERY = "SELECT * FROM academic_tickets WHERE author_id=?";

    private static final String FIND_IT_TICKET_QUERY = "SELECT * FROM it_tickets WHERE author_id=?";

    private static final String FIND_PARTICIPANT_OF_ACADEMIC_TICKET_QUERY = "SELECT participant_id FROM academic_tickets WHERE ticket_id=?";

    private static final String FIND_PARTICIPANT_OF_IT_TICKET_QUERY = "SELECT participant_id FROM it_tickets WHERE ticket_id=?";

    public static int getParticipantOfAcademicTicket(int ticketId) {
        int participantId = 0;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PARTICIPANT_OF_ACADEMIC_TICKET_QUERY);
            preparedStatement.setInt(1, ticketId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                participantId = resultSet.getInt("participant_id");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return participantId;
    }

    public static int getParticipantOfTechnicalTicket(int ticketId) {
        int participantId = 0;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PARTICIPANT_OF_IT_TICKET_QUERY);
            preparedStatement.setInt(1, ticketId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                participantId = resultSet.getInt("participant_id");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return participantId;
    }

    private static List<UserTicket> getAcademicTickets(int authorId) {
        List<UserTicket> tickets = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACADEMIC_TICKET_QUERY);
            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String ticketType = resultSet.getString("enquiry_type");

                UserTicket userTicket = new UserTicket(ticketId, name, description, ticketType, authorId);
                tickets.add(userTicket);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return tickets;
    }

    private static List<UserTicket> getTechnicalTickets(int authorId) {
        List<UserTicket> tickets = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_IT_TICKET_QUERY);
            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String ticketType = resultSet.getString("enquiry_type");

                UserTicket userTicket = new UserTicket(ticketId, name, description, ticketType, authorId);
                tickets.add(userTicket);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return tickets;
    }

    public static List<UserTicket> getAll(int authorId) {
        List<UserTicket> userTickets = new ArrayList<>();

        userTickets.addAll(getAcademicTickets(authorId));
        userTickets.addAll(getTechnicalTickets(authorId));

        return userTickets;
    }
}
