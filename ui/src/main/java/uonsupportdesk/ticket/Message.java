package uonsupportdesk.ticket;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Message.Builder.class)
public class Message {
    private final int ticketId;

    private final String ticketType;

    private final String message;

    private final int authorId;

    private final String timestamp;

    public Message(int ticketId, String ticketType, String message, int authorId, String timestamp) {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.message = message;
        this.authorId = authorId;
        this.timestamp = timestamp;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getMessage() {
        return message;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private int ticketId;

        private String ticketType;

        private String message;

        private int authorId;

        private String timestamp;

        private Builder() {

        }

        public Builder withTicketId(int ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder withTicketType(String ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withAuthorId(int authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Message build() {
            return new Message(ticketId, ticketType, message, authorId, timestamp);
        }

    }
}
