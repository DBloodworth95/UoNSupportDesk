package uonsupportdesk.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import uonsupportdesk.ticket.UserTicket;

import java.util.List;

@JsonDeserialize(builder = SuccessfulTicketListFetch.Builder.class)
public class SuccessfulTicketListFetch {
    private final String response;

    private final List<UserTicket> userTickets;

    public SuccessfulTicketListFetch(String response, List<UserTicket> userTickets) {
        this.response = response;
        this.userTickets = userTickets;
    }

    public String getResponse() {
        return response;
    }

    public List<UserTicket> getUserTickets() {
        return userTickets;
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private String response;

        private List<UserTicket> userTickets;

        private Builder() {

        }

        public Builder withResponse(String response) {
            this.response = response;
            return this;
        }

        public Builder withUserTickets(List<UserTicket> userTickets) {
            this.userTickets = userTickets;
            return this;
        }

        public SuccessfulTicketListFetch build() {
            return new SuccessfulTicketListFetch(response, userTickets);
        }
    }
}
