package uonsupportdesk.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = SubmitMessageRequest.Builder.class)
public class SubmitMessageRequest implements Command {

    private final String command;

    private final int ticketId;

    private final String ticketType;

    private final String messageBody;

    private final String timestamp;

    private final int authorId;

    public SubmitMessageRequest(String command, int ticketId, String ticketType, String messageBody, String timestamp, int authorId) {
        this.command = command;
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.messageBody = messageBody;
        this.timestamp = timestamp;
        this.authorId = authorId;
    }

    public String getCommand() {
        return command;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getAuthorId() {
        return authorId;
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private final String command;

        private int ticketId;

        private String ticketType;

        private String messageBody;

        private String timestamp;

        private int authorId;

        public Builder() {
            this.command = "sendmessage";
        }

        public Builder withTicketId(int ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder withTicketType(String ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public Builder withMessageBody(String messageBody) {
            this.messageBody = messageBody;
            return this;
        }

        public Builder withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withAuthorId(int authorId) {
            this.authorId = authorId;
            return this;
        }

        public SubmitMessageRequest build() {
            return new SubmitMessageRequest(command, ticketId, ticketType, messageBody, timestamp, authorId);
        }
    }
}
