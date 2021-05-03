package command;

public class TestPacket implements Command {

    private final String response;

    public TestPacket() {
        this.response = "responding";
    }

    public String getResponse() {
        return response;
    }
}
