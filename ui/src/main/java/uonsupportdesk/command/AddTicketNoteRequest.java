package uonsupportdesk.command;

public class AddTicketNoteRequest {
    private final String command;

    private final int ticketId;

    private final String ticketType;

    private final String body;

    public AddTicketNoteRequest(int ticketId, String ticketType, String body) {
        this.command = "addnote";
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.body = body;
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

    public String getCommand() {
        return command;
    }
}
