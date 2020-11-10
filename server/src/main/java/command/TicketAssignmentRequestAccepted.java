package command;

public class TicketAssignmentRequestAccepted implements Command {
    private final String response;

    private final int ticketId;

    private final int assineeId;

    private final String ticketType;

    public TicketAssignmentRequestAccepted(int ticketId, int assineeId, String ticketType) {
        this.response = "ticketassigned";
        this.ticketId = ticketId;
        this.assineeId = assineeId;
        this.ticketType = ticketType;
    }

    public String getResponse() {
        return response;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getAssineeId() {
        return assineeId;
    }

    public String getTicketType() {
        return ticketType;
    }
}
