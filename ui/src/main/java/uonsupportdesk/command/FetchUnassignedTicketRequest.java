package uonsupportdesk.command;

public class FetchUnassignedTicketRequest implements Command {
    private final String command;

    public FetchUnassignedTicketRequest() {
        this.command = "fetchunassignedtickets";
    }

    public FetchUnassignedTicketRequest(String command) {
        this.command = command;
    }
}
