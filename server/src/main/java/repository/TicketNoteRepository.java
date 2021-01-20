package repository;

import ticket.TicketNote;

import java.sql.*;

public final class TicketNoteRepository implements Repository {
    public static String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    public static String DATABASE_USERNAME = "root";

    public static String DATABASE_PASSWORD = "root";

    public static String UPDATE_TICKET_NOTE_QUERY = "UPDATE ticket_notes SET body= ? WHERE ticket_id= ? AND ticket_type= ?";

    public static String FETCH_TICKET_NOTE_QUERY = "SELECT * FROM ticket_notes WHERE ticket_id= ? AND ticket_type= ?";

    public static TicketNote submit(int ticketId, String ticketType, String body) {
        TicketNote addedNote = null;

        try {
            TicketNote previousVersion = get(ticketId, ticketType);

            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TICKET_NOTE_QUERY);
            preparedStatement.setString(1, previousVersion.getBody() + body);
            preparedStatement.setInt(2, ticketId);
            preparedStatement.setString(3, ticketType);
            preparedStatement.execute();

            addedNote = new TicketNote(previousVersion.getId(), ticketId, ticketType, previousVersion.getBody() + body);

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return addedNote;
    }

    public static TicketNote get(int ticketId, String ticketType) {
        TicketNote fetchedNote = null;

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(FETCH_TICKET_NOTE_QUERY);
            preparedStatement.setInt(1, ticketId);
            preparedStatement.setString(2, ticketType);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int ticketNoteId = resultSet.getInt("ticket_note_id");
                int fetchedTicketId = resultSet.getInt("ticket_id");
                String fetchtedTicketType = resultSet.getString("ticket_type");
                String body = resultSet.getString("body");

                fetchedNote = new TicketNote(ticketNoteId, fetchedTicketId, fetchtedTicketType, body);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return fetchedNote;
    }
}
