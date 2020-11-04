package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import conversation.InitialConversation;
import repository.ConversationRepository;

public class MessageService implements Service {

    private final ObjectMapper responseMapper = new ObjectMapper();

    public String submitMessage(String ticketDetails) {
        InitialConversation initialConversation = null;
        JsonNode ticketDetailsAsJson;

        try {
            ticketDetailsAsJson = responseMapper.readTree(ticketDetails);
            int ticketId = ticketDetailsAsJson.get("ticketId").asInt();

            initialConversation = ConversationRepository.submit(ticketId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (initialConversation == null) return generateFailedResponse();

        String response = null;
        if (response == null) return generateFailedResponse();

        return response;
    }

    private String generateFailedResponse() {
        return "{\"response\":\"ticketrequestfailed\"}";
    }

}
