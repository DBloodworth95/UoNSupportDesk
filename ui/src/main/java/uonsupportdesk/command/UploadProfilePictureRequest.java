package uonsupportdesk.command;

public class UploadProfilePictureRequest implements Command {

    public final String command;

    public final int userId;

    public final byte[] imageAsStream;

    public UploadProfilePictureRequest(int userId, byte[] imageAsStream) {
        this.command = "uploadimage";
        this.userId = userId;
        this.imageAsStream = imageAsStream;
    }

    public String getCommand() {
        return command;
    }

    public int getUserId() {
        return userId;
    }

    public byte[] getImageAsStream() {
        return imageAsStream;
    }
}
