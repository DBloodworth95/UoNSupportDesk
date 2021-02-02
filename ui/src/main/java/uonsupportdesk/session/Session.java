package uonsupportdesk.session;

public final class Session {

    private final int sessionId;

    private final String email;

    private final String name;

    private final AccessLevel accessLevel;

    private final byte[] profilePicture;

    public Session(int sessionId, String email, String name, AccessLevel accessLevel, byte[] profilePicture) {
        this.sessionId = sessionId;
        this.email = email;
        this.name = name;
        this.accessLevel = accessLevel;
        this.profilePicture = profilePicture;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
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
