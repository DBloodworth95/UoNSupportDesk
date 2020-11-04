package conversation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private final int ticketId;

    private final String ticketType;

    private final String message;

    private final int authorId;

    private final String timestamp;

    public Message(int ticketId, String ticketType, String message, int authorId, String timestamp) {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.message = message;
        this.authorId = authorId;
        this.timestamp = timestamp;
    }

    private Date getStringToDateConversion() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date date = null;
        try {
            date = formatter.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getMessage() {
        return message;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
