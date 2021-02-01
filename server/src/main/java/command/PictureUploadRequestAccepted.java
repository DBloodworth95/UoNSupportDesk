package command;

public class PictureUploadRequestAccepted implements Command {

    private final String response;

    private final byte[] picture;

    public PictureUploadRequestAccepted(byte[] picture) {
        this.response = "uploadsuccess";
        this.picture = picture;
    }

    public String getResponse() {
        return response;
    }

    public byte[] getPicture() {
        return picture;
    }
}
