package uonsupportdesk.command;

public class FetchArchiveTicketCollectionRequest implements Command {

    private final String command;

    private final int sessionId;

    public FetchArchiveTicketCollectionRequest(int sessionId) {
        this.command = "getallarchivetickets";
        this.sessionId = sessionId;
    }

    public String getCommand() {
        return command;
    }

    public int getSessionId() {
        return sessionId;
    }
}
