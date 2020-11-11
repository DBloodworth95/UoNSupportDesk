package ticket;

public class TicketAssignmentUpdate {
    private final int ticketId;

    private final int assigneeId;

    private final String assigneeName;

    private final String ticketType;

    public TicketAssignmentUpdate(int ticketId, int assigneeId, String assigneeName, String ticketType) {
        this.ticketId = ticketId;
        this.assigneeId = assigneeId;
        this.assigneeName = assigneeName;
        this.ticketType = ticketType;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public String getTicketType() {
        return ticketType;
    }
}
