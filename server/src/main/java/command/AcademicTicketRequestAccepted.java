package command;

public final class AcademicTicketRequestAccepted implements Command {

    private final String response;

    private final String enquiryType;

    private final int userId;

    public AcademicTicketRequestAccepted(int userId, String enquiryType) {
        this.response = "academicticketsuccess";
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
