package command;

public class TechnicalTicketRequestAccepted implements Command {
    private final String response;

    public TechnicalTicketRequestAccepted(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
