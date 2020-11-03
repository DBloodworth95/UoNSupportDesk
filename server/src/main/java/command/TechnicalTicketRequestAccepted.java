package command;

public class TechnicalTicketRequestAccepted implements Command {
    private final String response;

    private final String enquiryType;

    private final int userId;

    public TechnicalTicketRequestAccepted(int userId, String enquiryType) {
        this.response = "technicalticketsuccess";
        this.userId = userId;
        this.enquiryType = enquiryType;
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
}
