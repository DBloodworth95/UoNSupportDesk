package uonsupportdesk.command;

public class FetchTicketMessagesCommand {
    private final String command;

    private final int ticketId;

    private final String ticketType;

    public FetchTicketMessagesCommand(int ticketId, String ticketType) {
        this.command = "fetchmessages";
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
