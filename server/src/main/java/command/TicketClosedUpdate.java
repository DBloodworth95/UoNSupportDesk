package command;

public class TicketClosedUpdate implements Command {
    private final String response;

    private final int ticketId;

    private final String ticketType;

    public TicketClosedUpdate(int ticketId, String ticketType) {
        this.response = "ticketclosedsuccess";
        this.ticketId = ticketId;
        this.ticketType = ticketType;
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
}
