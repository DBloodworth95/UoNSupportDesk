package uonsupportdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.AssignTicketRequest;
import uonsupportdesk.command.FetchUnassignedTicketRequest;
import uonsupportdesk.command.SuccessfulUnassignedTicketFetch;
import uonsupportdesk.module.component.UnassignedTicketWidget;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.TicketCentreView;

public class TicketCentreController implements ClientListener {
    private final ClientBootstrap clientBootstrap;

    private final Session session;

    private final TicketCentreView ticketCentreView;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private static final String SUCCESSFUL_TICKET_FETCH_RESPONSE = "allunassignedtickets";

    public TicketCentreController(ClientBootstrap clientBootstrap, Session session, TicketCentreView ticketCentreView) {
        this.clientBootstrap = clientBootstrap;
        this.session = session;
        this.ticketCentreView = ticketCentreView;
    }

    public TicketCentreView initView() {
        clientBootstrap.getInitializer().getHandler().addListener(this);
        submitWrappedUnassignedTicketRequest();
        return ticketCentreView;
    }

    private void submitWrappedUnassignedTicketRequest() {
        try {
            FetchUnassignedTicketRequest fetchUnassignedTicketRequest = new FetchUnassignedTicketRequest();
            String requestAsString = jsonMapper.writeValueAsString(fetchUnassignedTicketRequest);
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
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processTicketsForViewRendering(JsonNode responseFromServer) {
        String responseAsString = responseFromServer.toPrettyString();

        try {
            SuccessfulUnassignedTicketFetch successfulUnassignedTicketFetch = jsonMapper.readValue(responseAsString, SuccessfulUnassignedTicketFetch.class);
            Platform.runLater(() -> ticketCentreView.renderMessageWidgets(successfulUnassignedTicketFetch.getUnassignedTickets()));
            Platform.runLater(this::listenForAssignEvents);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void listenForAssignEvents() {
        for (UnassignedTicketWidget unassignedTicketWidget : ticketCentreView.getTicketWidgets()) {
            unassignedTicketWidget.getAssignTicketButton().setOnAction(buttonPressed -> handleTicketAssignRequest(unassignedTicketWidget));
        }
    }

    private void handleTicketAssignRequest(UnassignedTicketWidget unassignedTicketWidget) {
        int ticketId = unassignedTicketWidget.getTicketId();
        int assigneeId = session.getSessionId();
        String assigneeName = session.getName();
        String ticketType = unassignedTicketWidget.getTicketType();

        try {
            AssignTicketRequest assignTicketRequest = new AssignTicketRequest(ticketId, assigneeId, assigneeName, ticketType);
            String requestAsString = jsonMapper.writeValueAsString(assignTicketRequest);

            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
