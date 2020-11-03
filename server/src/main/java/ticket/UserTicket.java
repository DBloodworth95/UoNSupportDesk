package ticket;

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
}
