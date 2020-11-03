package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.AcademicTicketRequestAccepted;
import command.Command;
import command.TechnicalTicketRequestAccepted;
import repository.AcademicTicketRepository;
import repository.TechnicalTicketRepository;
import ticket.AcademicTicket;
import ticket.TechnicalTicket;
import ticket.Ticket;

public final class TicketService implements Service {

    private final ObjectMapper responseMapper = new ObjectMapper();

    public String submitAcademicTicket(JsonNode ticketRequestFromClient) {
        int userId = ticketRequestFromClient.get("userId").asInt();
        String name = ticketRequestFromClient.get("name").asText();
        String email = ticketRequestFromClient.get("email").asText();
        String enquiryType = ticketRequestFromClient.get("enquiryType").asText();
        String description = ticketRequestFromClient.get("description").asText();
        String pathway = ticketRequestFromClient.get("pathway").asText();
        String year = ticketRequestFromClient.get("year").asText();

        AcademicTicket academicTicket = AcademicTicketRepository.submit(userId, name, email, enquiryType, description, pathway, year);
        if (academicTicket == null) return generateFailedResponse();

        String response = generateSuccessResponse(academicTicket, userId, enquiryType);
        if (response == null) return generateFailedResponse();

        return response;
    }

    public String submitTechnicalTicket(JsonNode ticketRequestFromClient) {
        int userId = ticketRequestFromClient.get("userId").asInt();
        String name = ticketRequestFromClient.get("name").asText();
        String email = ticketRequestFromClient.get("email").asText();
        String enquiryType = ticketRequestFromClient.get("enquiryType").asText();
        String description = ticketRequestFromClient.get("description").asText();

        TechnicalTicket technicalTicket = TechnicalTicketRepository.submit(userId, name, email, enquiryType, description);
        if (technicalTicket == null) return generateFailedResponse();

        String response = generateSuccessResponse(technicalTicket, userId, enquiryType);
        if (response == null) return generateFailedResponse();

        return response;
    }

    private String generateSuccessResponse(Ticket ticket, int userId, String enquiryType) {
        Command responseAsCommand = null;
        String responseAsString = null;

        if (ticket instanceof AcademicTicket) {
            responseAsCommand = new AcademicTicketRequestAccepted(userId, enquiryType, ticket.ticketID());
        } else if (ticket instanceof TechnicalTicket) {
            responseAsCommand = new TechnicalTicketRequestAccepted(userId, enquiryType, ticket.ticketID());
        }

        try {
            responseAsString = responseMapper.writeValueAsString(responseAsCommand);
            createConversation(responseAsString);
        } catch (JsonProcessingException ignored) {

        }

        return responseAsString;
    }

    private String generateFailedResponse() {
        return "{\"response\":\"ticketrequestfailed\"}";
    }

    private void createConversation(String ticketDetails) {
        MessageService messageService = new MessageService();
        messageService.submitConversation(ticketDetails);
    }
}
