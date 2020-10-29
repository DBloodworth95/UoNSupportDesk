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

        String response = generateSuccessResponse(academicTicket);
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

        String response = generateSuccessResponse(technicalTicket);
        if (response == null) return generateFailedResponse();

        return response;
    }

    private String generateSuccessResponse(Ticket ticket) {
        Command commandResponse = null;

        if (ticket instanceof AcademicTicket) {
            commandResponse = new AcademicTicketRequestAccepted("academicticketsuccess");
        } else if (ticket instanceof TechnicalTicket) {
            commandResponse = new TechnicalTicketRequestAccepted("technicalticketsuccess");
        }

        String response = null;
        try {
            response = responseMapper.writeValueAsString(commandResponse);
        } catch (JsonProcessingException ignored) {

        }

        return response;
    }

    private String generateFailedResponse() {
        return "{\"response\":\"academicticketfailed\"}";
    }
}
