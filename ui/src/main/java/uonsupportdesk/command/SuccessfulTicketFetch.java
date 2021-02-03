package uonsupportdesk.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import uonsupportdesk.ticket.UserTicket;

@JsonDeserialize(builder = SuccessfulTicketFetch.Builder.class)
public class SuccessfulTicketFetch {
    private final String response;

    private final UserTicket userTicket;

    public SuccessfulTicketFetch(String response, UserTicket userTicket) {
        this.response = response;
        this.userTicket = userTicket;
    }

    public String getResponse() {
        return response;
    }

    public UserTicket getUserTicket() {
        return userTicket;
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private String response;

        private UserTicket userTicket;

        private Builder() {

        }

        public Builder withResponse(String response) {
            this.response = response;
            return this;
        }

        public Builder withUserTicket(UserTicket userTicket) {
            this.userTicket = userTicket;
            return this;
        }

        public SuccessfulTicketFetch build() {
            return new SuccessfulTicketFetch(response, userTicket);
        }
    }
}
