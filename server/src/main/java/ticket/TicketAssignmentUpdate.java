package ticket;

public class TicketAssignmentUpdate {
    private final int ticketId;

    private final int assigneeId;

    private final String ticketType;

    public TicketAssignmentUpdate(int ticketId, int assigneeId, String ticketType) {
        this.ticketId = ticketId;
        this.assigneeId = assigneeId;
        this.ticketType = ticketType;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public String getTicketType() {
        return ticketType;
    }
}
