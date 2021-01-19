package ticket;

public class TicketNote {
    private final int id;

    private final int ticketId;

    private final String ticketType;

    private final String body;

    public TicketNote(int id, int ticketId, String ticketType, String body) {
        this.id = id;
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getBody() {
        return body;
    }
}
