package command;

public class AddTicketNoteRequestAccepted implements Command {
    private final String response;

    private final int id;

    private final int ticketId;

    private final String ticketType;

    private final String body;

    public AddTicketNoteRequestAccepted(int id, int ticketId, String ticketType, String body) {
        this.response = "addticketnoteaccepted";
        this.id = id;
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.body = body;
    }

    public String getResponse() {
        return response;
    }

    public int getId() {
        return id;
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
}
