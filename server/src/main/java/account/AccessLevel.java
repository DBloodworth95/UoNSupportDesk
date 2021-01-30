package account;

public enum AccessLevel {
    SUPPORT_TEAM, USER;

    public static AccessLevel fromInt(int id) {
        switch (id) {
            case 1: return SUPPORT_TEAM;
            case 2: return USER;
            default: throw new IllegalArgumentException("Invalid ID");
        }
    }

    public static int toInt(AccessLevel accessLevel) {
        switch (accessLevel) {
            case SUPPORT_TEAM: return 1;
            case USER: return 2;
            default: throw new IllegalArgumentException("Invalid Access Level");
        }
    }
}
