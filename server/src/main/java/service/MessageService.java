package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.TicketMessagesRequestAccepted;
import conversation.InitialConversation;
import conversation.MessageList;
import repository.MessageRepository;

public class MessageService implements Service {

    private final ObjectMapper responseMapper = new ObjectMapper();

    public String submitMessage(String ticketDetails) {
        InitialConversation initialConversation = null;
        JsonNode ticketDetailsAsJson;

        try {
            ticketDetailsAsJson = responseMapper.readTree(ticketDetails);
            int ticketId = ticketDetailsAsJson.get("ticketId").asInt();

            initialConversation = MessageRepository.submit(ticketId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (initialConversation == null) return generateFailedResponse();

        String response = null;
        if (response == null) return generateFailedResponse();

        return response;
    }

    public String getTicketMessages(JsonNode ticketDetails) {
        int ticketId = ticketDetails.get("ticketId").asInt();
        String ticketType = ticketDetails.get("ticketType").asText();

        MessageList messageList = MessageRepository.findAll(ticketId, ticketType);
        if (messageList == null) return generateFailedResponse();

        String response = generateSuccessResponse(messageList);
        if (response == null) return generateFailedResponse();

        return response;
    }

    private String generateSuccessResponse(MessageList messageList) {
        TicketMessagesRequestAccepted ticketMessagesRequestAccepted = new TicketMessagesRequestAccepted(messageList.getTicketId(), messageList.getTicketType(),
                messageList.getMessages());
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
