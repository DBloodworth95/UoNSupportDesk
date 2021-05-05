package command;

public class TicketAssignmentRequestAccepted implements Command {
    private final String response;

    private final int ticketId;

    private final int assineeId;

    private final String assigneeName;

    private final String ticketType;

    private final byte[] profilePicture;

    public TicketAssignmentRequestAccepted(int ticketId, int assineeId, String assigneeName, String ticketType, byte[] profilePicture) {
        this.response = "ticketassigned";
        this.ticketId = ticketId;
        this.assineeId = assineeId;
        this.assigneeName = assigneeName;
        this.ticketType = ticketType;
        this.profilePicture = profilePicture;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
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
