package command;

public class TechnicalTicketRequestAccepted implements Command {
    private final String response;

    public TechnicalTicketRequestAccepted() {
        this.response = "technicalticketsuccess";
    }

    public String getResponse() {
        return response;
    }
}
