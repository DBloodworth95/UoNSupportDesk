package command;

public final class AcademicTicketRequestAccepted implements Command {

    private final String response;

    public AcademicTicketRequestAccepted(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
