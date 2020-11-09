package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.CreateTicketRequestAccepted;
import command.UnassignedTicketListRequestAccepted;
import command.UserTicketListRequestAccepted;
import repository.AcademicTicketRepository;
import repository.TechnicalTicketRepository;
import repository.UserTicketRepository;
import ticket.*;

import java.util.List;

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

    public String getUserTickets(JsonNode userTicketsRequestFromClient) {
        String response;
        int userId = userTicketsRequestFromClient.get("sessionId").asInt();
        List<UserTicket> userTickets = UserTicketRepository.getAll(userId);

        if (userTickets.isEmpty()) {
            return generateFailedResponse();
        }

        response = generateAllTicketsSuccessResponse(userTickets);

        return response;
    }

    public String getUnassignedTickets() {
        String response;
        List<UnassignedTicket> unassignedTickets = UserTicketRepository.getAllUnassigned();

        if (unassignedTickets.isEmpty()) {
            return generateFailedResponse();
        }

        response = generateUnassignedTicketSuccessResponse(unassignedTickets);

        return response;
    }

    private String generateUnassignedTicketSuccessResponse(List<UnassignedTicket> unassignedTickets) {
        String responseAsString = null;
        UnassignedTicketListRequestAccepted unassignedTicketListRequestAccepted = new UnassignedTicketListRequestAccepted(unassignedTickets);

        try {
            responseAsString = responseMapper.writeValueAsString(unassignedTicketListRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return responseAsString;
    }

    private String generateAllTicketsSuccessResponse(List<UserTicket> messages) {
        String responseAsString = null;
        UserTicketListRequestAccepted userTicketListRequestAccepted = new UserTicketListRequestAccepted(messages);

        try {
            responseAsString = responseMapper.writeValueAsString(userTicketListRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return responseAsString;
    }

    private String generateSuccessResponse(Ticket ticket, int userId, String enquiryType) {
        CreateTicketRequestAccepted responseAsCommand;
        String responseAsString = null;

        try {
            responseAsCommand = new CreateTicketRequestAccepted(userId, enquiryType, ticket.ticketID());
            responseAsString = responseMapper.writeValueAsString(responseAsCommand);
        } catch (JsonProcessingException ignored) {

        }

        return responseAsString;
    }

    private String generateFailedResponse() {
        return "{\"response\":\"ticketrequestfailed\"}";
    }
}
