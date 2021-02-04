package uonsupportdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.*;
import uonsupportdesk.module.component.note.TicketNote;
import uonsupportdesk.module.component.ticket.AssignedTicketWidget;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.ArchiveTicketsView;


public class ArchiveTicketController implements ClientListener {
    private int currentTicketId;

    private String currentTicketType;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    private final ArchiveTicketsView archiveTicketsView;

    private static final String SUCCESSFUL_TICKET_FETCH_RESPONSE = "ticketrequestsuccess";

    private static final String SUCCESSFUL_TICKET_MESSAGES_FETCH_RESPONSE = "getticketmessagessuccess";

    private static final String SUCCESSFUL_TICKET_NOTE_FETCH_RESPONSE = "ticketnoteaccepted";

    public ArchiveTicketController(ClientBootstrap clientBootstrap, Session session, ArchiveTicketsView archiveTicketsView) {
        this.clientBootstrap = clientBootstrap;
        this.session = session;
        this.archiveTicketsView = archiveTicketsView;
    }

    public ArchiveTicketsView initView() {
        attachButtonListeners();
        submitWrappedFetchArchiveTicketCommand();
        clientBootstrap.getInitializer().getHandler().addListener(this);

        return archiveTicketsView;
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
            } else if (responseFromServerAsString.equalsIgnoreCase(SUCCESSFUL_TICKET_NOTE_FETCH_RESPONSE)) {
                processTicketNoteForViewRendering(responseFromServer);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void submitWrappedFetchArchiveTicketCommand() {
        FetchArchiveTicketCollectionRequest fetchArchiveTicketCollectionRequest = wrapFetchArchiveTicketRequestAsCommand();

        try {
            String requestAsString = jsonMapper.writeValueAsString(fetchArchiveTicketCollectionRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processMessagesForViewRendering(JsonNode responseFromServer) {
        String responseAsString = responseFromServer.toPrettyString();

        try {
            SuccessfulTicketMessagesFetch successfulTicketMessagesFetch = jsonMapper.readValue(responseAsString, SuccessfulTicketMessagesFetch.class);
            Platform.runLater(() -> archiveTicketsView.renderMessageWidgets(successfulTicketMessagesFetch.getMessages(), session.getSessionId()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processTicketsForViewRendering(JsonNode responseFromServer) {
        String responseAsString = responseFromServer.toPrettyString();

        try {
            SuccessfulTicketFetch successfulTicketFetch = jsonMapper.readValue(responseAsString, SuccessfulTicketFetch.class);
            Platform.runLater(() -> archiveTicketsView.renderTicketWidget(successfulTicketFetch.getUserTicket(), session));
            Platform.runLater(this::keepTrackOfActiveChat);
            Platform.runLater(() -> fetchCurrentChatMessages(currentTicketId, currentTicketType));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private FetchArchiveTicketCollectionRequest wrapFetchArchiveTicketRequestAsCommand() {
        return new FetchArchiveTicketCollectionRequest(session.getSessionId());
    }

    private void attachButtonListeners() {
        archiveTicketsView.getViewNoteButton().setOnAction(e -> submitFetchTicketNoteRequest());
    }

    public void removeListener() {
        clientBootstrap.getInitializer().getHandler().removeListener(this);
    }

    public void updateActiveChat(int ticketId, String ticketType) {
        this.currentTicketId = ticketId;
        this.currentTicketType = ticketType;
        archiveTicketsView.clearMessageList();
        fetchCurrentChatMessages(ticketId, ticketType);
    }

    private void submitFetchTicketNoteRequest() {
        FetchTicketNoteCommand fetchTicketNoteRequest = new FetchTicketNoteCommand(currentTicketId, currentTicketType);

        try {
            String requestAsString = jsonMapper.writeValueAsString(fetchTicketNoteRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void keepTrackOfActiveChat() {
        for (AssignedTicketWidget ticketWidget : archiveTicketsView.getTicketWidgets()) {
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

    private void processTicketNoteForViewRendering(JsonNode responseFromServer) {
        int noteId = responseFromServer.get("id").asInt();
        int ticketId = responseFromServer.get("ticketId").asInt();
        String ticketType = responseFromServer.get("ticketType").asText();
        String body = responseFromServer.get("body").asText();

        TicketNote ticketNote = new TicketNote(noteId, ticketId, ticketType, body);

        Platform.runLater(() -> archiveTicketsView.openNoteWidget(ticketNote));
    }

    public ArchiveTicketsView getArchiveTicketsView() {
        return archiveTicketsView;
    }
}
