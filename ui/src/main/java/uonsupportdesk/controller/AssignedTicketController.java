package uonsupportdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.*;
import uonsupportdesk.module.component.AssignedTicketWidget;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.AssignedTicketsView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AssignedTicketController implements ClientListener {

    private int currentTicketId;

    private String currentTicketType;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private final AssignedTicketsView assignedTicketsView;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    private static final String SUCCESSFUL_TICKET_FETCH_RESPONSE = "ticketrequestsuccess";

    private static final String SUCCESSFUL_TICKET_MESSAGES_FETCH_RESPONSE = "getticketmessagessuccess";

    private static final String INCOMING_MESSAGE_RESPONSE = "incomingmessage";

    public AssignedTicketController(AssignedTicketsView assignedTicketsView, Session session, ClientBootstrap clientBootstrap, int currentTicketId, String currentTicketType) {
        this.assignedTicketsView = assignedTicketsView;
        this.session = session;
        this.clientBootstrap = clientBootstrap;
        this.currentTicketId = currentTicketId;
        this.currentTicketType = currentTicketType;
    }

    public AssignedTicketsView initView() {
        submitWrappedFetchTicketCommand();
        clientBootstrap.getInitializer().getHandler().addListener(this);
        return assignedTicketsView;
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
            } else if (responseFromServerAsString.equalsIgnoreCase(INCOMING_MESSAGE_RESPONSE)) {
                processSingularMessageForViewRendering(responseFromServer);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processSingularMessageForViewRendering(JsonNode responseFromServer) {
        int ticketId = responseFromServer.get("ticketId").asInt();
        String ticketType = responseFromServer.get("ticketType").asText();
        String messageBody = responseFromServer.get("body").asText();
        int authorId = responseFromServer.get("authorId").asInt();

        if (ticketId == currentTicketId && ticketType.equalsIgnoreCase(currentTicketType)) {
            Platform.runLater(() -> assignedTicketsView.renderSingularMessageWidget(session.getSessionId(), authorId, messageBody));
        }
    }

    private void processMessagesForViewRendering(JsonNode responseFromServer) {
        String responseAsString = responseFromServer.toPrettyString();

        try {
            SuccessfulTicketMessagesFetch successfulTicketMessagesFetch = jsonMapper.readValue(responseAsString, SuccessfulTicketMessagesFetch.class);
            Platform.runLater(() -> assignedTicketsView.renderMessageWidgets(successfulTicketMessagesFetch.getMessages(), session.getSessionId()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void processTicketsForViewRendering(JsonNode responseFromServer) {
        String responseAsString = responseFromServer.toPrettyString();

        try {
            SuccessfulTicketListFetch successfulTicketListFetch = jsonMapper.readValue(responseAsString, SuccessfulTicketListFetch.class);
            Platform.runLater(() -> assignedTicketsView.renderTicketWidgets(successfulTicketListFetch.getUserTickets()));
            Platform.runLater(this::keepTrackOfActiveChat);
            Platform.runLater(this::listenForUserInput);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void keepTrackOfActiveChat() {
        for (AssignedTicketWidget ticketWidget : assignedTicketsView.getTicketWidgets()) {
            ticketWidget.setOnMouseClicked(e -> setActiveChat(ticketWidget.getTicketId(), ticketWidget.getTicketType()));
        }
    }

    private void listenForUserInput() {
        assignedTicketsView.getUserInputField().setOnKeyPressed(keyPressed -> {
            if (keyPressed.getCode() == KeyCode.ENTER) {
                submitMessageSendRequest(assignedTicketsView.getUserInputField().getText());
                assignedTicketsView.clearUserInputField();
            }
        });
    }

    private SubmitMessageRequest wrapSendMessageRequestAsCommand(String message) {
        Date dateOfMessageSubmission = new Date();
        String dateAsString = dateFormatter.format(dateOfMessageSubmission);

        return new SubmitMessageRequest.
                Builder()
                .withTicketId(currentTicketId)
                .withTicketType(currentTicketType)
                .withMessageBody(message)
                .withTimestamp(dateAsString)
                .withAuthorId(session.getSessionId())
                .build();
    }

    private void submitMessageSendRequest(String message) {
        SubmitMessageRequest messageRequest = wrapSendMessageRequestAsCommand(message);

        try {
            String requestAsString = jsonMapper.writeValueAsString(messageRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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