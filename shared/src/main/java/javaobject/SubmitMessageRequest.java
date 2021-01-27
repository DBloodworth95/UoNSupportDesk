package javaobject;

import java.io.Serializable;

public class SubmitMessageRequest implements Serializable {

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
}
