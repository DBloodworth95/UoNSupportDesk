package service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageService implements Service {

    private final ObjectMapper responseMapper = new ObjectMapper();

    public void submitConversation(String ticketDetails) {

    }
}
