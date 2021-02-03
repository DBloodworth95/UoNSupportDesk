package command;

import ticket.UserTicket;

public class UserTicketListRequestAccepted {
    private final String response;

    private final UserTicket userTicket;

    public UserTicketListRequestAccepted(UserTicket userTicket) {
        this.response = "ticketrequestsuccess";
        this.userTicket = userTicket;
    }

    public String getResponse() {
        return response;
    }

    public UserTicket getUserTicket() {
        return userTicket;
    }
}
