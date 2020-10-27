package uonsupportdesk.session;

public final class Session {

    private final String email;

    private final String name;

    private final AccessLevel accessLevel;

    public Session(String email, String name, AccessLevel accessLevel) {
        this.email = email;
        this.name = name;
        this.accessLevel = accessLevel;
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
