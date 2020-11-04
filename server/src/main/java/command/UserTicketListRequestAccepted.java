package command;

import ticket.UserTicket;

import java.util.List;

public class UserTicketListRequestAccepted {
    private final String response;

    private final List<UserTicket> messages;

    public UserTicketListRequestAccepted(List<UserTicket> messages) {
        this.response = "ticketrequestsuccess";
        this.messages = messages;
    }

    public String getResponse() {
        return response;
    }

    public List<UserTicket> getUserTickets() {
        return messages;
    }
}
