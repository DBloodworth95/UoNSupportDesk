package ticket;

public class AcademicTicket {
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
}
