package command;

public class TicketAssignmentRequestAccepted implements Command {
    private final String response;

    private final int ticketId;

    private final int assineeId;

    private final String assigneeName;

    private final String ticketType;

    public TicketAssignmentRequestAccepted(int ticketId, int assineeId, String assigneeName, String ticketType) {
        this.response = "ticketassigned";
        this.ticketId = ticketId;
        this.assineeId = assineeId;
        this.assigneeName = assigneeName;
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

    public String getAssigneeName() {
        return assigneeName;
    }

    public String getTicketType() {
        return ticketType;
    }
}
