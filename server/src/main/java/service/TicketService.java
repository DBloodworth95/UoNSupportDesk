package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.AcademicTicketRequestAccepted;
import command.LoginRequestAccepted;
import repository.AcademicTicketRepository;
import ticket.AcademicTicket;

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

    private String generateSuccessResponse(AcademicTicket academicTicket) {
        AcademicTicketRequestAccepted academicTicketRequestAccepted = new AcademicTicketRequestAccepted("academicticketsuccess");
        String response = null;

        try {
            response = responseMapper.writeValueAsString(academicTicketRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return response;
    }

    private String generateFailedResponse() {
        return "{\"response\":\"academicticketfailed\"}";
    }
}
