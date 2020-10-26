package command;

import account.AccessLevel;

public class ValidatedLogin {
    private final String response;

    private final String email;

    private final String name;

    private final AccessLevel accessLevel;

    public ValidatedLogin(String response, String email, String name, AccessLevel accessLevel) {
        this.response = response;
        this.email = email;
        this.name = name;
        this.accessLevel = accessLevel;
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
