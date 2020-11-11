package uonsupportdesk.command;

public class AssignTicketRequest {
    private final String command;

    private final int ticketId;

    private final int assigneeId;

    private final String assigneeName;

    private final String ticketType;

    public AssignTicketRequest(int ticketId, int assigneeId, String assigneeName, String ticketType) {
        this.command = "assignticket";
        this.ticketId = ticketId;
        this.assigneeId = assigneeId;
        this.assigneeName = assigneeName;
        this.ticketType = ticketType;
    }

    public String getCommand() {
        return command;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public String getTicketType() {
        return ticketType;
    }
}
