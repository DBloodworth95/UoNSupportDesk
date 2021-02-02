package account;

public class Account {

    private final int userId;

    private final String name;

    private final String email;

    private final String password;

    private final AccessLevel accessLevel;

    private final byte[] profilePicture;

    public Account(int userId, String name, String email, String password, AccessLevel accessLevel, byte[] profilePicture) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accessLevel = accessLevel;
        this.profilePicture = profilePicture;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
}
