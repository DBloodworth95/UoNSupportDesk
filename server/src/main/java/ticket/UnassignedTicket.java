package ticket;

public class UnassignedTicket {
    private final int ticketId;

    private final String name;

    private final String description;

    private final String ticketType;

    public UnassignedTicket(int ticketId, String name, String description, String ticketType) {
        this.ticketId = ticketId;
        this.name = name;
        this.description = description;
        this.ticketType = ticketType;
    }

    public String getTicketType() {
        return ticketType;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
