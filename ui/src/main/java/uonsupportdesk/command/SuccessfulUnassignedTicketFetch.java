package uonsupportdesk.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import uonsupportdesk.ticket.UnassignedTicket;

import java.util.List;

@JsonDeserialize(builder = SuccessfulUnassignedTicketFetch.Builder.class)
public class SuccessfulUnassignedTicketFetch {
    private final String response;

    private final List<UnassignedTicket> unassignedTickets;

    public SuccessfulUnassignedTicketFetch(String response, List<UnassignedTicket> unassignedTickets) {
        this.response = response;
        this.unassignedTickets = unassignedTickets;
    }

    public String getResponse() {
        return response;
    }

    public List<UnassignedTicket> getUnassignedTickets() {
        return unassignedTickets;
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private String response;

        private List<UnassignedTicket> unassignedTickets;

        private Builder() {

        }

        public Builder withResponse(String response) {
            this.response = response;
            return this;
        }

        public Builder withUnassignedTickets(List<UnassignedTicket> unassignedTickets) {
            this.unassignedTickets = unassignedTickets;
            return this;
        }

        public SuccessfulUnassignedTicketFetch build() {
            return new SuccessfulUnassignedTicketFetch(response, unassignedTickets);
        }
    }
}
