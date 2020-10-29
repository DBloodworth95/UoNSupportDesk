package command;

public final class AcademicTicketRequestAccepted implements Command {

    private final String response;

    public AcademicTicketRequestAccepted() {
        this.response = "academicticketsuccess";
    }

    public String getResponse() {
        return response;
    }
}
