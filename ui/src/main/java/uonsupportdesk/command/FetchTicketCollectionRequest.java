package uonsupportdesk.command;

public class FetchTicketCollectionRequest implements Command {

    private final String command;

    private final int sessionId;

    public FetchTicketCollectionRequest(int sessionId) {
        this.command = "getalltickets";
        this.sessionId = sessionId;
    }

    public String getCommand() {
        return command;
    }

    public int getSessionId() {
        return sessionId;
    }
}
