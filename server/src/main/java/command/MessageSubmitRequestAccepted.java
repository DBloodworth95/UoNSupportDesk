package command;

public class MessageSubmitRequestAccepted implements Command {
    private final String response;

    private final int ticketId;

    private final String ticketType;

    private final String body;

    private final String timestamp;

    private final int authorId;

    public MessageSubmitRequestAccepted(int ticketId, String ticketType, String body, String timestamp, int authorId) {
        this.response = "incomingmessage";
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.body = body;
        this.timestamp = timestamp;
        this.authorId = authorId;
    }

    public String getResponse() {
        return response;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getBody() {
        return body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getAuthorId() {
        return authorId;
    }
}
