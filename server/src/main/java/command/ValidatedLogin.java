package command;

import account.AccessLevel;

public class ValidatedLogin implements Command {
    private final String response;

    private final int userId;

    private final String email;

    private final String name;

    private final AccessLevel accessLevel;

    public ValidatedLogin(String response, int userId, String email, String name, AccessLevel accessLevel) {
        this.response = response;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.accessLevel = accessLevel;
    }

    public int getUserId() {
        return userId;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public String getResponse() {
        return response;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
