package uonsupportdesk.session;

public enum AccessLevel {
    SUPPORT_TEAM, USER;

    public static AccessLevel fromInt(int id) {
        switch (id) {
            case 1: return SUPPORT_TEAM;
            case 2: return USER;
            default: throw new IllegalArgumentException("Invalid ID");
        }
    }

    public static AccessLevel fromString(String accessLevel) {
        switch (accessLevel) {
        case "SUPPORT_TEAM": return SUPPORT_TEAM;
        case "USER": return USER;
        default: throw new IllegalArgumentException("Invalid Access Level Type");
        }
    }
}
