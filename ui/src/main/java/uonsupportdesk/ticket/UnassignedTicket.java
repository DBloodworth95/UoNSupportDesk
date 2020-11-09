package uonsupportdesk.ticket;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UnassignedTicket.Builder.class)
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

    public int getTicketId() {
        return ticketId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTicketType() {
        return ticketType;
    }

    @JsonPOJOBuilder
    public static final class Builder {
        private int ticketId;

        private String name;

        private String description;

        private String ticketType;

        private Builder() {

        }

        public Builder withTicketId(int ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withTicketType(String ticketType) {
            this.ticketType = ticketType;
            return this;
        }

        public UnassignedTicket build() {
            return new UnassignedTicket(ticketId, name, description, ticketType);
        }
    }
}
