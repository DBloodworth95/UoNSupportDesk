package command;

public class CreateTicketRequestAccepted implements Command {
    private final String response;

    private final String enquiryType;

    private final int userId;

    private final int ticketId;

    public CreateTicketRequestAccepted(int userId, String enquiryType, int ticketId) {
        this.response = "createticketsuccess";
        this.userId = userId;
        this.enquiryType = enquiryType;
        this.ticketId = ticketId;
    }

    public String getResponse() {
        return response;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public int getUserId() {
        return userId;
    }

    public int getTicketId() {
        return ticketId;
    }
}
