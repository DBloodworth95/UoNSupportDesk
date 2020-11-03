package command;

public class TechnicalTicketRequestAccepted implements Command {
    private final String response;

    private final String enquiryType;

    private final int userId;

    private final int ticketId;

    public TechnicalTicketRequestAccepted(int userId, String enquiryType, int ticketId) {
        this.response = "technicalticketsuccess";
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
