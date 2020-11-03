package command;

import ticket.UserTicket;

import java.util.List;

public class UserTicketListRequestAccepted {
    private final String response;

    private final List<UserTicket> userTickets;

    public UserTicketListRequestAccepted(List<UserTicket> userTickets) {
        this.response = "ticketrequestsuccess";
        this.userTickets = userTickets;
    }

    public String getResponse() {
        return response;
    }

    public List<UserTicket> getUserTickets() {
        return userTickets;
    }
}
