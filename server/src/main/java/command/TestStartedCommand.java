package command;

public class TestStartedCommand implements Command {

    private final String response;

    public TestStartedCommand() {
        this.response = "teststart";
    }

    public String getResponse() {
        return response;
    }
}
