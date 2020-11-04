package uonsupportdesk.ticket;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UserTicket.Builder.class)
public class UserTicket {
    private final int ticketId;

    private final String authorName;

    private final String description;

    private final int authorId;

    public UserTicket(int ticketId, String authorName, String description, int authorId) {
        this.ticketId = ticketId;
        this.authorName = authorName;
        this.description = description;
        this.authorId = authorId;
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

    public int getAuthorId() {
        return authorId;
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private int ticketId;

        private String authorName;

        private String description;

        private int authorId;

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

        public Builder withAuthorId(int authorId) {
            this.authorId = authorId;
            return this;
        }

        public UserTicket build() {
            return new UserTicket(ticketId, authorName, description, authorId);
        }
    }
}
