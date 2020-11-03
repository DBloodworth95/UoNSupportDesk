package ticket;

public class AcademicTicket implements Ticket {
    private final int userId;

    private final String name;

    private final String email;

    private final String enquiryType;

    private final String description;

    private final String pathway;

    private final String year;

    public AcademicTicket(int userId, String name, String email, String enquiryType, String description, String pathway, String year) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.enquiryType = enquiryType;
        this.description = description;
        this.pathway = pathway;
        this.year = year;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public String getDescription() {
        return description;
    }

    public String getPathway() {
        return pathway;
    }

    public String getYear() {
        return year;
    }
}
