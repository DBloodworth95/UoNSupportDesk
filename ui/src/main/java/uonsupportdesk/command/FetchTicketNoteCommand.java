package uonsupportdesk.command;

public class FetchTicketNoteCommand implements Command {
    private final String command;

    private final int ticketId;

    private final String ticketType;

    public FetchTicketNoteCommand(int ticketId, String ticketType) {
        this.command = "fetchticketnote";
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
