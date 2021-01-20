package repository;

import ticket.UnassignedTicket;
import ticket.UserTicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserTicketRepository implements Repository {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    private static final String DATABASE_USERNAME = "root";

    private static final String DATABASE_PASSWORD = "root";

    private static final String FIND_ACADEMIC_TICKET_QUERY = "SELECT * FROM academic_tickets WHERE author_id=? AND archived=0 OR participant_id=? AND archived=0";

    private static final String FIND_IT_TICKET_QUERY = "SELECT * FROM it_tickets WHERE author_id=? AND archived=0 OR participant_id=? AND archived=0";

    private static final String FIND_ACADEMIC_ARCHIVED_TICKET_QUERY = "SELECT * FROM academic_tickets WHERE author_id=? AND archived=1 OR participant_id=? AND archived=1";

    private static final String FIND_IT_ARCHIVED_TICKET_QUERY = "SELECT * FROM it_tickets WHERE author_id=? AND archived=1 OR participant_id=? AND archived=1";

    private static final String FIND_UNASSIGNED_ACADEMIC_TICKET_QUERY = "SELECT * FROM academic_tickets WHERE participant_id=?";

    private static final String FIND_UNASSIGNED_IT_TICKET_QUERY = "SELECT * FROM it_tickets WHERE participant_id=?";

    private static final String FIND_PARTICIPANT_OF_ACADEMIC_TICKET_QUERY = "SELECT participant_id FROM academic_tickets WHERE ticket_id=?";

    private static final String FIND_PARTICIPANT_OF_IT_TICKET_QUERY = "SELECT participant_id FROM it_tickets WHERE ticket_id=?";

    private static final String FIND_AUTHOR_OF_ACADEMIC_TICKET_QUERY = "SELECT author_id FROM academic_tickets WHERE ticket_id=?";

    private static final String FIND_AUTHOR_OF_IT_TICKET_QUERY = "SELECT author_id FROM it_tickets WHERE ticket_id=?";

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

    public static int getAuthorOfTechnicalTicket(int ticketId) {
        int author_id = 0;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUTHOR_OF_IT_TICKET_QUERY);
            preparedStatement.setInt(1, ticketId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                author_id = resultSet.getInt("author_id");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return author_id;
    }

    public static int getAuthorOfAcademicTicket(int ticketId) {
        int authorId = 0;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUTHOR_OF_ACADEMIC_TICKET_QUERY);
            preparedStatement.setInt(1, ticketId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                authorId = resultSet.getInt("author_id");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return authorId;
    }

    private static List<UserTicket> getAcademicTickets(int authorId) {
        List<UserTicket> tickets = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACADEMIC_TICKET_QUERY);
            preparedStatement.setInt(1, authorId);
            preparedStatement.setInt(2, authorId);
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

    private static List<UserTicket> getArchivedAcademicTickets(int authorId) {
        List<UserTicket> tickets = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ACADEMIC_ARCHIVED_TICKET_QUERY);
            preparedStatement.setInt(1, authorId);
            preparedStatement.setInt(2, authorId);
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
            preparedStatement.setInt(2, authorId);
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

    private static List<UserTicket> getArchivedTechnicalTickets(int authorId) {
        List<UserTicket> tickets = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_IT_ARCHIVED_TICKET_QUERY);
            preparedStatement.setInt(1, authorId);
            preparedStatement.setInt(2, authorId);
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

    public static List<UserTicket> getAllArchived(int authorId) {
        List<UserTicket> userTickets = new ArrayList<>();

        userTickets.addAll(getArchivedAcademicTickets(authorId));
        userTickets.addAll(getArchivedTechnicalTickets(authorId));

        return userTickets;
    }

    public static List<UnassignedTicket> getAllUnassigned() {
        List<UnassignedTicket> unassignedTickets = new ArrayList<>();

        unassignedTickets.addAll(getUnassignedAcademicTickets());
        unassignedTickets.addAll(getUnassignedTechnicalTickets());

        return unassignedTickets;
    }

    private static List<UnassignedTicket> getUnassignedTechnicalTickets() {
        List<UnassignedTicket> unassignedTickets = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_UNASSIGNED_IT_TICKET_QUERY);
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String ticketType = resultSet.getString("enquiry_type");

                UnassignedTicket unassignedTicket = new UnassignedTicket(ticketId, name, description, ticketType);
                unassignedTickets.add(unassignedTicket);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return unassignedTickets;
    }

    private static List<UnassignedTicket> getUnassignedAcademicTickets() {
        List<UnassignedTicket> unassignedTickets = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_UNASSIGNED_ACADEMIC_TICKET_QUERY);
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int ticketId = resultSet.getInt("ticket_id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String ticketType = resultSet.getString("enquiry_type");

                UnassignedTicket unassignedTicket = new UnassignedTicket(ticketId, name, description, ticketType);
                unassignedTickets.add(unassignedTicket);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return unassignedTickets;
    }
}
