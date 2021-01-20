package uonsupportdesk.command;

public class CloseTicketRequest implements Command {
    private final String command;

    private final int ticketId;

    private final String ticketType;

    public CloseTicketRequest(int ticketId, String ticketType) {
        this.command = "closeticket";
        this.ticketId = ticketId;
        this.ticketType = ticketType;
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
