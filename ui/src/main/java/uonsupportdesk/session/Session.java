package uonsupportdesk.session;

public final class Session {

    private final int sessionId;

    private final String email;

    private final String name;

    private final AccessLevel accessLevel;

    public Session(int sessionId, String email, String name, AccessLevel accessLevel) {
        this.sessionId = sessionId;
        this.email = email;
        this.name = name;
        this.accessLevel = accessLevel;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
}
