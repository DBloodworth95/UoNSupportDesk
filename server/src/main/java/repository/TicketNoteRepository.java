package repository;

import ticket.TicketNote;

import java.sql.*;

public class TicketNoteRepository implements Repository {
    public static String DATABASE_URL = "jdbc:mysql://localhost:3306/uonsuport-schema";

    public static String DATABASE_USERNAME = "root";

    public static String DATABASE_PASSWORD = "root";

    public static String UPDATE_TICKET_NOTE_QUERY = "UPDATE ticket_notes SET body= ? WHERE ticket_id= ? AND ticket_type= ?";

    public static String FETCH_TICKET_NOTE_QUERY = "SELECT * FROM ticket_notes WHERE ticket_id= ? AND ticket_type= ?";

    public static TicketNote update(int ticketId, String ticketType, String body) {
        //TODO: Add note updating.
        return null;
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
