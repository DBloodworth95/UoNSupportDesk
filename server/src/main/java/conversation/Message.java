package conversation;

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
