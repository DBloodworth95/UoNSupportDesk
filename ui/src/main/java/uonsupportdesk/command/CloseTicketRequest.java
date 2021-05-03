package uonsupportdesk.command;

public class CloseTicketRequest implements Command {
    private final String command;

    private final int ticketId;

    private final int requesterId;

    private final String ticketType;

    public CloseTicketRequest(int ticketId, int requesterId, String ticketType) {
        this.command = "closeticket";
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.requesterId = requesterId;
    }

    public int getRequesterId() {
        return requesterId;
    }

    public String getCommand() {
        return command;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }
}
