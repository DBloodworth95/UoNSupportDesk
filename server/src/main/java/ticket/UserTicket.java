package ticket;

public class UserTicket {
    private final int ticketId;

    private final String authorName;

    private final String description;

    private final String ticketType;

    private final int authorId;

    private final int participantId;

    private final byte[] profilePictureOfParticipant;

    public UserTicket(int ticketId, String authorName, String description, String ticketType, int authorId, int participantId, byte[] profilePictureOfParticipant) {
        this.ticketId = ticketId;
        this.authorName = authorName;
        this.description = description;
        this.ticketType = ticketType;
        this.authorId = authorId;
        this.participantId = participantId;
        this.profilePictureOfParticipant = profilePictureOfParticipant;
    }

    public byte[] getProfilePictureOfParticipant() {
        return profilePictureOfParticipant;
    }

    public int getParticipantId() {
        return participantId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDescription() {
        return description;
    }

    public String getTicketType() {
        return ticketType;
    }

    public int getAuthorId() {
        return authorId;
    }
}
