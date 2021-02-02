package account;

public class ProfilePicture {

    private final int userIdOfPicture;

    private final byte[] picture;

    public ProfilePicture(int userIdOfPicture, byte[] picture) {
        this.userIdOfPicture = userIdOfPicture;
        this.picture = picture;
    }

    public int getUserIdOfPicture() {
        return userIdOfPicture;
    }

    public byte[] getPicture() {
        return picture;
    }
}
