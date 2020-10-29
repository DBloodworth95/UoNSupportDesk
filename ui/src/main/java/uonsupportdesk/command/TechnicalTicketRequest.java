package uonsupportdesk.command;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder(withPrefix = "")
public class TechnicalTicketRequest implements Command {
    private int userId;
    private String command;
    private String name;
    private String email;
    private String enquiryType;
    private String description;

    public static class Builder {

        private final int userId;
        private String command;
        private String name;
        private String email;
        private String enquiryType;
        private String description;

        public Builder(int userId) {
            this.userId = userId;
        }

        public Builder technicalTicketCommand() {
            this.command = "technicalticket";

            return this;
        }

        public Builder withFullName(String name) {
            this.name = name;

            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;

            return this;
        }

        public Builder withEnquiryType(String enquiryType) {
            this.enquiryType = enquiryType;

            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;

            return this;
        }

        public TechnicalTicketRequest build() {
            TechnicalTicketRequest technicalTicketRequest = new TechnicalTicketRequest();
            technicalTicketRequest.userId = this.userId;
            technicalTicketRequest.command = this.command;
            technicalTicketRequest.name = this.name;
            technicalTicketRequest.email = this.email;
            technicalTicketRequest.enquiryType = this.enquiryType;
            technicalTicketRequest.description = this.description;

            return technicalTicketRequest;
        }
    }

    private TechnicalTicketRequest() {

    }

    public int getUserId() {
        return userId;
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public String getDescription() {
        return description;
    }
}
