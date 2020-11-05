package uonsupportdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.FetchTicketCollectionRequest;
import uonsupportdesk.command.FetchTicketMessagesCommand;
import uonsupportdesk.command.SuccessfulTicketListFetch;
import uonsupportdesk.command.SuccessfulTicketMessagesFetch;
import uonsupportdesk.module.component.AssignedTicketWidget;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.UserTicketsView;

public class UserTicketsController implements ClientListener {

    private int currentTicketId;

    private String currentTicketType;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private final UserTicketsView userTicketsView;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    private static final String SUCCESSFUL_TICKET_FETCH_RESPONSE = "ticketrequestsuccess";

    private static final String SUCCESSFUL_TICKET_MESSAGES_FETCH_RESPONSE = "getticketmessagessuccess";

    public UserTicketsController(UserTicketsView userTicketsView, Session session, ClientBootstrap clientBootstrap, int currentTicketId, String currentTicketType) {
        this.userTicketsView = userTicketsView;
        this.session = session;
        this.clientBootstrap = clientBootstrap;
        this.currentTicketId = currentTicketId;
        this.currentTicketType = currentTicketType;
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

            if (responseFromServerAsString.equalsIgnoreCase(SUCCESSFUL_TICKET_FETCH_RESPONSE)) {
                processTicketsForViewRendering(responseFromServer);
            } else if (responseFromServerAsString.equalsIgnoreCase(SUCCESSFUL_TICKET_MESSAGES_FETCH_RESPONSE)) {
                processMessagesForViewRendering(responseFromServer);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processMessagesForViewRendering(JsonNode responseFromServer) {
        String responseAsString = responseFromServer.toPrettyString();

        try {
            SuccessfulTicketMessagesFetch successfulTicketMessagesFetch = jsonMapper.readValue(responseAsString, SuccessfulTicketMessagesFetch.class);
            Platform.runLater(() -> userTicketsView.renderMessageWidgets(successfulTicketMessagesFetch.getMessages(), session.getSessionId()));
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
        for (AssignedTicketWidget ticketWidget : userTicketsView.getTicketWidgets()) {
            ticketWidget.setOnMouseClicked(e -> setActiveChat(ticketWidget.getTicketId(), ticketWidget.getTicketType()));
        }
    }

    private void setActiveChat(int ticketId, String ticketType) {
        fetchCurrentChatMessages(ticketId, ticketType);
        this.currentTicketId = ticketId;
        this.currentTicketType = ticketType;
    }

    private void fetchCurrentChatMessages(int ticketId, String ticketType) {
        FetchTicketMessagesCommand fetchTicketMessagesRequest = new FetchTicketMessagesCommand(ticketId, ticketType);

        try {
            String requestAsString = jsonMapper.writeValueAsString(fetchTicketMessagesRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
