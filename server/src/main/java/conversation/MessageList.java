package conversation;

import java.util.List;

public class MessageList {
    private final int ticketId;

    private final String ticketType;

    private final List<Message> messages;

    public MessageList(int ticketId, String ticketType, List<Message> messages) {
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.messages = messages;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
