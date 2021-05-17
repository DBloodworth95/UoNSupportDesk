package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.MessageSubmitRequestAccepted;
import command.TicketMessagesRequestAccepted;
import conversation.Message;
import conversation.MessageList;
import repository.AccountRepository;
import repository.MessageRepository;
import repository.UserTicketRepository;

public class MessageService implements Service {

    private final ObjectMapper responseMapper = new ObjectMapper();

    public int getParticipant(JsonNode ticketDetails) {
        int participantId = 0;
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();

        if (ticketType.equalsIgnoreCase("academic")) {
            participantId = UserTicketRepository.getParticipantOfAcademicTicket(ticketId);
        } else if (ticketType.equalsIgnoreCase("it")) {
            participantId = UserTicketRepository.getParticipantOfTechnicalTicket(ticketId);
        }

        return participantId;
    }

    public int getAuthor(JsonNode ticketDetails) {
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

    public int getSendTo(JsonNode ticketDetails) {
        int sendTo;
        int messageAuthor = ticketDetails.get("authorId").asInt();
        int ticketParticipant = getParticipant(ticketDetails);

        if (messageAuthor == ticketParticipant) {
            sendTo = getAuthor(ticketDetails);
        } else {
            sendTo = ticketParticipant;
        }

        return sendTo;
    }

    public String submitMessage(JsonNode ticketDetails) {
        Message message;
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();
        String body = ticketDetails.get("messageBody").asText();
        String timestamp = ticketDetails.get("timestamp").asText();
        int authorId = ticketDetails.get("authorId").asInt();

        message = MessageRepository.submit(ticketId, ticketType, body, timestamp, authorId);

        if (message == null) return generateFailedResponse();

        String response = generateSuccessMessageResponse(message);
        if (response == null) return generateFailedResponse();

        return response;
    }

    public String getTicketMessages(JsonNode ticketDetails) {
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();
        String participantName = "empty";
        int participantId = 0;

        if (ticketType.equalsIgnoreCase("academic")) {
            participantId = UserTicketRepository.getParticipantOfAcademicTicket(ticketId);
        } else if (ticketType.equalsIgnoreCase("it")) {
            participantId = UserTicketRepository.getParticipantOfTechnicalTicket(ticketId);
        }

        if (participantId != 0) {
            participantName = AccountRepository.getNameOfAccountHolder(participantId);
        }

        MessageList messageList = MessageRepository.findAll(ticketId, ticketType);
        if (messageList == null) return generateFailedResponse();

        String response = generateSuccessResponse(messageList, participantName);
        if (response == null) return generateFailedResponse();

        return response;
    }

    private String generateSuccessMessageResponse(Message message) {
        MessageSubmitRequestAccepted messageSubmitRequestAccepted = new MessageSubmitRequestAccepted(message.getTicketId(), message.getTicketType(),
                message.getMessage(), message.getTimestamp(), message.getAuthorId());
        String response = null;

        try {
            response = responseMapper.writeValueAsString(messageSubmitRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return response;
    }

    private String generateSuccessResponse(MessageList messageList, String participantName) {
        TicketMessagesRequestAccepted ticketMessagesRequestAccepted = new TicketMessagesRequestAccepted(messageList.getTicketId(), messageList.getTicketType(),
                messageList.getMessages(), participantName);
        String response = null;

        try {
            response = responseMapper.writeValueAsString(ticketMessagesRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return response;
    }

    private String generateFailedResponse() {
        return "{\"response\":\"ticketrequestfailed\"}";
    }

}
