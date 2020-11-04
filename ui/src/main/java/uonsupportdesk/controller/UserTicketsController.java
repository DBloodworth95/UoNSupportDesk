package uonsupportdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.FetchTicketCollectionRequest;
import uonsupportdesk.command.SuccessfulTicketListFetch;
import uonsupportdesk.module.component.AssignedTicketWidget;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.UserTicketsView;

public class UserTicketsController implements ClientListener {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private int currentTicketId;

    private int currentConversationId;

    private final UserTicketsView userTicketsView;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    private static final String SUCCESSFUL_TICKET_FETCH_MESSAGE = "ticketrequestsuccess";

    public UserTicketsController(UserTicketsView userTicketsView, Session session, ClientBootstrap clientBootstrap, int currentTicketId, int currentConversationId) {
        this.userTicketsView = userTicketsView;
        this.session = session;
        this.clientBootstrap = clientBootstrap;
        this.currentTicketId = currentTicketId;
        this.currentConversationId = currentConversationId;
    }

    public UserTicketsView initView() {
        submitWrappedFetchTicketCommand();
        clientBootstrap.getInitializer().getHandler().addListener(this);
        return userTicketsView;
    }

    private FetchTicketCollectionRequest wrapFetchTicketRequestAsCommand() {
        return new FetchTicketCollectionRequest(session.getSessionId());
    }

    private void submitWrappedFetchTicketCommand() {
        FetchTicketCollectionRequest fetchTicketCollectionRequest = wrapFetchTicketRequestAsCommand();

        try {
            String requestAsString = jsonMapper.writeValueAsString(fetchTicketCollectionRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processMessageFromClient(String msg) {
        try {
            JsonNode responseFromServer = jsonMapper.readTree(msg);
            String responseFromServerAsString = responseFromServer.get("response").asText();

            if (responseFromServerAsString.equalsIgnoreCase(SUCCESSFUL_TICKET_FETCH_MESSAGE)) {
                processTicketsForViewRendering(responseFromServer);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processTicketsForViewRendering(JsonNode responseFromServer) {
        String responseAsString = responseFromServer.toPrettyString();

        try {
            SuccessfulTicketListFetch successfulTicketListFetch = jsonMapper.readValue(responseAsString, SuccessfulTicketListFetch.class);
            Platform.runLater(() -> userTicketsView.renderTicketWidgets(successfulTicketListFetch.getUserTickets()));
            Platform.runLater(this::keepTrackOfActiveChat);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void keepTrackOfActiveChat() {
        for (AssignedTicketWidget ticketWidget : userTicketsView.getTicketwidgets()) {
            ticketWidget.setOnMouseClicked(e -> currentChatIs(ticketWidget.getTicketId(), ticketWidget.getTicketType()));
        }
    }

    private void currentChatIs(int ticketId, String ticketType) {
        System.out.println(ticketId + " " + ticketType);
    }
}
