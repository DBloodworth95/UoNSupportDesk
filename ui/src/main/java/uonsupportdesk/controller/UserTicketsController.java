package uonsupportdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.FetchTicketCollectionRequest;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.UserTicketsView;

public class UserTicketsController implements ClientListener {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private int currentTicketId;

    private int currentConversationId;

    private final UserTicketsView userTicketsView;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    public UserTicketsController(UserTicketsView userTicketsView, Session session, ClientBootstrap clientBootstrap, int currentTicketId, int currentConversationId) {
        this.userTicketsView = userTicketsView;
        this.session = session;
        this.clientBootstrap = clientBootstrap;
        this.currentTicketId = currentTicketId;
        this.currentConversationId = currentConversationId;
    }

    public UserTicketsView initView() {
        submitWrappedFetchTicketCommand();
        System.out.println(currentTicketId + " " + currentConversationId);
        return userTicketsView;
    }

    private FetchTicketCollectionRequest wrapFetchTicketRequestAsCommand() {
        return new FetchTicketCollectionRequest(session.getSessionId());
    }

    private void submitWrappedFetchTicketCommand() {
        FetchTicketCollectionRequest fetchTicketCollectionRequest = wrapFetchTicketRequestAsCommand();

        try {
            String requestAsString = jsonMapper.writeValueAsString(fetchTicketCollectionRequest);
            System.out.println(requestAsString);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processMessageFromClient(String msg) {

    }
}
