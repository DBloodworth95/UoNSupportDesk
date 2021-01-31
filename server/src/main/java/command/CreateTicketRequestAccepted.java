package command;

public class CreateTicketRequestAccepted implements Command {
    private final String response;

    private final String enquiryType;

    private final String description;

    private final String name;

    private final int userId;

    private final int ticketId;

    public CreateTicketRequestAccepted(int userId, String enquiryType, int ticketId, String description, String name) {
        this.response = "createticketsuccess";
        this.userId = userId;
        this.enquiryType = enquiryType;
        this.ticketId = ticketId;
        this.description = description;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
