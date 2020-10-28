package uonsupportdesk.command;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder(withPrefix = "")
public class AcademicTicketRequest {

    private int userId;
    private String command;
    private String name;
    private String email;
    private String enquiryType;
    private String description;
    private String pathway;
    private String year;

    public static class Builder {

        private final int userId;
        private String command;
        private String name;
        private String email;
        private String enquiryType;
        private String description;
        private String pathway;
        private String year;

        public Builder(int userId) {
            this.userId = userId;
        }

        public Builder academicTicketCommand() {
            this.command = "academicticket";

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

        public Builder onPathway(String pathway) {
            this.pathway = pathway;

            return this;
        }

        public Builder onYear(String year) {
            this.year = year;

            return this;
        }

        public AcademicTicketRequest build() {
            AcademicTicketRequest academicTicketRequest = new AcademicTicketRequest();
            academicTicketRequest.userId = this.userId;
            academicTicketRequest.command = this.command;
            academicTicketRequest.name = this.name;
            academicTicketRequest.email = this.email;
            academicTicketRequest.enquiryType = this.enquiryType;
            academicTicketRequest.description = this.description;
            academicTicketRequest.pathway = this.pathway;
            academicTicketRequest.year = this.year;

            return academicTicketRequest;
        }
    }

    private AcademicTicketRequest() {

    }

    public String getCommand() {
        return command;
    }

    public int getUserId() {
        return userId;
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

    public String getPathway() {
        return pathway;
    }

    public String getYear() {
        return year;
    }
}
