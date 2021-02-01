package account;

public class ProfilePicture {

    private final byte[] picture;

    public ProfilePicture(byte[] picture) {
        this.picture = picture;
    }

    public byte[] getPicture() {
        return picture;
    }
}
