package command;

import ticket.UnassignedTicket;

import java.util.List;

public class UnassignedTicketListRequestAccepted implements Command {
    private final String response;

    private final List<UnassignedTicket> unassignedTickets;

    public UnassignedTicketListRequestAccepted(List<UnassignedTicket> unassignedTickets) {
        this.response = "allunassignedtickets";
        this.unassignedTickets = unassignedTickets;
    }

    public String getResponse() {
        return response;
    }

    public List<UnassignedTicket> getUnassignedTickets() {
        return unassignedTickets;
    }
}
