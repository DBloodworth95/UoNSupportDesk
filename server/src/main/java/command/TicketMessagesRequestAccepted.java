package command;

import conversation.Message;

import java.util.List;


public class TicketMessagesRequestAccepted implements Command {
    private final String response;

    private final int ticketId;

    private final String ticketType;

    private final List<Message> messages;

    public TicketMessagesRequestAccepted(int ticketId, String ticketType, List<Message> messages) {
        this.response = "getticketmessagessuccess";
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.messages = messages;
    }

    public String getResponse() {
        return response;
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
