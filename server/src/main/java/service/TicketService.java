package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.*;
import repository.AcademicTicketRepository;
import repository.TechnicalTicketRepository;
import repository.TicketNoteRepository;
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

    public String fetchTicketNote(JsonNode ticketNoteRequestFromClient) {
        int ticketId = ticketNoteRequestFromClient.get("ticketId").asInt();
        String ticketType = ticketNoteRequestFromClient.get("ticketType").asText();
        TicketNote ticketNote;

        ticketNote = TicketNoteRepository.get(ticketId, ticketType);
        if (ticketNote == null) return generateFailedResponse();

        String response = generateSuccessFetchTicketNoteResponse(ticketNote);
        if (response == null) return generateFailedResponse();

        return response;
    }

    public String submitTicketNote(JsonNode addTicketNoteRequest) {
        int ticketId = addTicketNoteRequest.get("ticketId").asInt();
        String ticketType = addTicketNoteRequest.get("ticketType").asText();
        String body = addTicketNoteRequest.get("body").asText();
        TicketNote ticketNote;

        ticketNote = TicketNoteRepository.submit(ticketId, ticketType, body);

        if (ticketNote == null) return generateFailedResponse();

        String response = generateSuccessAddTicketNoteResponse(ticketNote);
        if (response == null) return generateFailedResponse();

        return response;
    }

    public String assignTicket(JsonNode ticketDetails) {
        TicketAssignmentUpdate ticketAssignmentUpdate = null;
        int ticketId = ticketDetails.get("ticketId").asInt();
        int assigneeId = ticketDetails.get("assigneeId").asInt();
        String assigneeName = ticketDetails.get("assigneeName").asText();
        String ticketType = ticketDetails.get("ticketType").asText();

        if (ticketType.equalsIgnoreCase("academic")) {
            ticketAssignmentUpdate = AcademicTicketRepository.submitTicketAssignment(ticketId, assigneeId, assigneeName, ticketType);
        } else if (ticketType.equalsIgnoreCase("it")) {
            ticketAssignmentUpdate = TechnicalTicketRepository.submitTicketAssignment(ticketId, assigneeId, assigneeName, ticketType);
        }

        if (ticketAssignmentUpdate == null) return generateFailedResponse();

        String response = generateSuccessTicketAssignedResponse(ticketAssignmentUpdate);
        if (response == null) return generateFailedResponse();

        return response;
    }

    public String closeTicket(JsonNode ticketToClose) {
        TicketClosedUpdate ticketClosedUpdate = null;
        int ticketId = ticketToClose.get("ticketId").asInt();
        String ticketType = ticketToClose.get("ticketType").asText();

        if (ticketType.equalsIgnoreCase("academic")) {
            ticketClosedUpdate = AcademicTicketRepository.closeTicket(ticketId);
        } else if (ticketType.equalsIgnoreCase("it")) {
            ticketClosedUpdate = TechnicalTicketRepository.closeTicket(ticketId);
        }

        if (ticketClosedUpdate == null) return generateFailedResponse();

        String response = generateSuccessTicketClosureResponse(ticketClosedUpdate);
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

    public String getUserArchivedTickets(JsonNode userTicketsRequestFromClient) {
        String response;
        int userId = userTicketsRequestFromClient.get("sessionId").asInt();
        List<UserTicket> userTickets = UserTicketRepository.getAllArchived(userId);

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

    public int getAuthorId(JsonNode ticketDetails) {
        int authorId = 0;
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();

        if (ticketType.equalsIgnoreCase("academic")) {
            authorId = UserTicketRepository.getAuthorOfAcademicTicket(ticketId);
        } else if (ticketType.equalsIgnoreCase("it")) {
            authorId = UserTicketRepository.getAuthorOfTechnicalTicket(ticketId);
        }

        return authorId;
    }

    public int getParticipantId(JsonNode ticketDetails) {
        int authorId = 0;
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();

        if (ticketType.equalsIgnoreCase("academic")) {
            authorId = UserTicketRepository.getParticipantOfAcademicTicket(ticketId);
        } else if (ticketType.equalsIgnoreCase("it")) {
            authorId = UserTicketRepository.getParticipantOfTechnicalTicket(ticketId);
        }

        return authorId;
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

    private String generateSuccessTicketAssignedResponse(TicketAssignmentUpdate ticketAssignmentUpdate) {
        String responseAsString = null;
        int ticketId = ticketAssignmentUpdate.getTicketId();
        int assigneeId = ticketAssignmentUpdate.getAssigneeId();
        String assigneeName = ticketAssignmentUpdate.getAssigneeName();
        String ticketType = ticketAssignmentUpdate.getTicketType();

        TicketAssignmentRequestAccepted ticketAssignmentRequestAccepted = new TicketAssignmentRequestAccepted(ticketId, assigneeId, assigneeName, ticketType);

        try {
            responseAsString = responseMapper.writeValueAsString(ticketAssignmentRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return responseAsString;
    }

    private String generateSuccessAddTicketNoteResponse(TicketNote ticketNote) {
        String responseAsString = null;
        int ticketNoteId = ticketNote.getId();
        int ticketId = ticketNote.getTicketId();
        String ticketType = ticketNote.getTicketType();
        String body = ticketNote.getBody();

        AddTicketNoteRequestAccepted addTicketNoteRequestAccepted = new AddTicketNoteRequestAccepted(ticketNoteId, ticketId, ticketType, body);

        try {
            responseAsString = responseMapper.writeValueAsString(addTicketNoteRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return responseAsString;
    }

    private String generateSuccessFetchTicketNoteResponse(TicketNote ticketNote) {
        String responseAsString = null;
        int ticketNoteId = ticketNote.getId();
        int ticketId = ticketNote.getTicketId();
        String ticketType = ticketNote.getTicketType();
        String body = ticketNote.getBody();

        TicketNoteRequestAccepted ticketNoteRequestAccepted = new TicketNoteRequestAccepted(ticketNoteId, ticketId, ticketType, body);

        try {
            responseAsString = responseMapper.writeValueAsString(ticketNoteRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return responseAsString;
    }

    private String generateSuccessTicketClosureResponse(TicketClosedUpdate ticketClosedUpdate) {
        String responseAsString = null;

        try {
            responseAsString = responseMapper.writeValueAsString(ticketClosedUpdate);
        } catch (JsonProcessingException ignored) {

        }

        return responseAsString;
    }

    private String generateFailedResponse() {
        return "{\"response\":\"ticketrequestfailed\"}";
    }
}
