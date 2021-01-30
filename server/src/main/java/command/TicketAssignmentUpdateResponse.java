package command;

public class TicketAssignmentUpdateResponse {

    private final String response;

    private final int ticketId;

    private final int assigneeId;

    private final String ticketType;

    public TicketAssignmentUpdateResponse(int ticketId, int assigneeId, String ticketType) {
        this.response = "ticketwasassigned";
        this.ticketId = ticketId;
        this.assigneeId = assigneeId;
        this.ticketType = ticketType;
    }

    public String getResponse() {
        return response;
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
