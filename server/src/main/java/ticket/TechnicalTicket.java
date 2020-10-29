package ticket;

public class TechnicalTicket implements Ticket {
    private final int userId;

    private final String name;

    private final String email;

    private final String enquiryType;

    private final String description;

    public TechnicalTicket(int userId, String name, String email, String enquiryType, String description) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.enquiryType = enquiryType;
        this.description = description;
    }
}
