package uonsupportdesk.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import uonsupportdesk.ticket.Message;

import java.util.List;

@JsonDeserialize(builder = SuccessfulTicketMessagesFetch.Builder.class)
public class SuccessfulTicketMessagesFetch {
    private final String response;

    private final int ticketId;

    private final String ticketType;

    private final List<Message> messages;

    public SuccessfulTicketMessagesFetch(String response, int ticketId, String ticketType, List<Message> messages) {
        this.response = response;
        this.ticketId = ticketId;
        this.ticketType = ticketType;
        this.messages = messages;
    }

    public String getResponse() {
        return response;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private String response;

        private int ticketId;

        private String ticketType;

        private List<Message> messages;

        private Builder() {

        }

        public Builder withResponse(String response) {
            this.response = response;
            return this;
        }

        public Builder withTicketId(int ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder withTicketType(String ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public Builder withMessages(List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public SuccessfulTicketMessagesFetch build() {
            return new SuccessfulTicketMessagesFetch(response, ticketId, ticketType, messages);
        }
    }
}
