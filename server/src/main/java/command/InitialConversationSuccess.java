package command;

public class InitialConversationSuccess {
    private final String response;

    private final int ticketId;

    private final int conversationId;

    public InitialConversationSuccess(int ticketId, int conversationId) {
        this.response = "convostartsuccess";
        this.ticketId = ticketId;
        this.conversationId = conversationId;
    }

    public String getResponse() {
        return response;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getConversationId() {
        return conversationId;
    }
}
