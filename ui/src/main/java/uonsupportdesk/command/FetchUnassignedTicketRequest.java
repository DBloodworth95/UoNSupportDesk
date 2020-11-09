package uonsupportdesk.command;

public class FetchUnassignedTicketRequest implements Command {
    private final String command;

    public FetchUnassignedTicketRequest() {
        this.command = "fetchunassignedtickets";
    }

    public String getCommand() {
        return command;
    }
}
