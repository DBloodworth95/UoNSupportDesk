package command;

public class TicketClosedUpdate implements Command {
    private final String command;

    private final int ticketId;

    private final String ticketType;

    public TicketClosedUpdate(int ticketId, String ticketType) {
        this.command = "ticketclosedsuccess";
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
