package uonsupportdesk.command;

public final class LoginRequest implements Command {

    private final String command;

    private final String username;

    private final String password;

    public LoginRequest(String command, String username, String password) {
        this.command = command;
        this.username = username;
        this.password = password;
    }

    public String getCommand() {
        return command;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
