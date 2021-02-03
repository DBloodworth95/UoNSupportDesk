package uonsupportdesk.ticket;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UserTicket.Builder.class)
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

    public int getParticipantId() {
        return participantId;
    }

    public byte[] getProfilePictureOfParticipant() {
        return profilePictureOfParticipant;
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

    @JsonPOJOBuilder
    public static final class Builder {
        private int ticketId;

        private String authorName;

        private String description;

        private String ticketType;

        private int authorId;

        private int participantId;

        private byte[] profilePictureOfParticipant;

        private Builder() {

        }

        public Builder withTicketId(int ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder withAuthorName(String authorName) {
            this.authorName = authorName;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withTicketType(String ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public Builder withAuthorId(int authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder withParticipantId(int participantId) {
            this.participantId = participantId;
            return this;
        }

        public Builder withProfilePictureOfParticipant(byte[] profilePictureOfParticipant) {
            this.profilePictureOfParticipant = profilePictureOfParticipant;
            return this;
        }

        public UserTicket build() {
            return new UserTicket(ticketId, authorName, description, ticketType, authorId, participantId, profilePictureOfParticipant);
        }
    }
}
