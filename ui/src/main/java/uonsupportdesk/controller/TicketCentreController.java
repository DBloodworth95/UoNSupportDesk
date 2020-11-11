package uonsupportdesk.controller;

import com.dlsc.workbenchfx.Workbench;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.AssignTicketRequest;
import uonsupportdesk.command.FetchUnassignedTicketRequest;
import uonsupportdesk.command.SuccessfulUnassignedTicketFetch;
import uonsupportdesk.module.AssignedTicketsModule;
import uonsupportdesk.module.component.UnassignedTicketWidget;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.TicketCentreView;

public class TicketCentreController implements ClientListener {
    private final ClientBootstrap clientBootstrap;

    private final Session session;

    private final TicketCentreView ticketCentreView;

    private final Workbench workbench;

    private final AssignedTicketsModule assignedTicketsModule;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private static final String SUCCESSFUL_TICKET_FETCH_RESPONSE = "allunassignedtickets";

    private static final String SUCCESSFUL_TICKET_ASSIGN_RESPONSE = "ticketassigned";

    public TicketCentreController(ClientBootstrap clientBootstrap, Session session, TicketCentreView ticketCentreView, Workbench workbench, AssignedTicketsModule assignedTicketsModule) {
        this.clientBootstrap = clientBootstrap;
        this.session = session;
        this.ticketCentreView = ticketCentreView;
        this.workbench = workbench;
        this.assignedTicketsModule = assignedTicketsModule;
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
            } else if (responseFromServerAsString.equalsIgnoreCase(SUCCESSFUL_TICKET_ASSIGN_RESPONSE)) {
                startAssignedConversation(responseFromServer);
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

    private void startAssignedConversation(JsonNode responseFromServer) {
        int initialTicketId = responseFromServer.get("ticketId").asInt();
        String ticketType = responseFromServer.get("ticketType").asText();

        assignedTicketsModule.setInitialTicketId(initialTicketId);
        assignedTicketsModule.setInitialTicketType(ticketType);

        Platform.runLater(() -> workbench.openModule(assignedTicketsModule));
    }
}
