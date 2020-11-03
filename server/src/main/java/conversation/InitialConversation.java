package conversation;

public class InitialConversation {
    private final int ticketId;

    private final int conversationId;

    public InitialConversation(int ticketId, int conversationId) {
        this.ticketId = ticketId;
        this.conversationId = conversationId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getConversationId() {
        return conversationId;
    }
}
