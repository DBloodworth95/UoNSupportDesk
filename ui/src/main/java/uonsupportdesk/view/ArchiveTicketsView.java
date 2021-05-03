package uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import uonsupportdesk.module.component.note.TicketNote;
import uonsupportdesk.module.component.note.TicketNoteWidget;
import uonsupportdesk.module.component.ticket.AssignedTicketWidget;
import uonsupportdesk.module.component.ticket.MessageWidget;
import uonsupportdesk.module.component.ticket.MessageWidgetOrientation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import uonsupportdesk.session.Session;
import uonsupportdesk.ticket.Message;
import uonsupportdesk.ticket.UserTicket;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArchiveTicketsView extends BorderPane {

    private final ScrollPane archivedTicketsListScroll;

    private final ScrollPane activeChatScroll;

    private final Pane archiveTicketsContent;

    private final Label noActiveTicketsLabel;

    private final Label noChatOpenLabel;

    private final Label talkingToLabel;

    private final VBox ticketsContainer;

    private final VBox currentChatContainer;

    private final VBox messageContainer;

    private final HBox userInputContainer;

    private final JFXButton viewNoteButton;

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    private static final int ACTIVE_CHAT_HEIGHT = 900;

    private static final int TALKING_TO_LABEL_SPACING = 20;

    private static final int USER_INPUT_CONTAINER_SPACING = 120;

    private static final int SCROLL_BAR_VIEW_BOTTOM = 1;

    private List<AssignedTicketWidget> ticketwidgets = new ArrayList<>();

    private final ObservableList<Node> messageList = FXCollections.observableArrayList();

    public ArchiveTicketsView() {
        this.getStyleClass().add("module-background");
        this.setPadding(new Insets(10, 10, 10, 10));
        archivedTicketsListScroll = new ScrollPane();
        archiveTicketsContent = new Pane();
        noActiveTicketsLabel = new Label("No tickets available");
        noChatOpenLabel = new Label("Select a ticket");
        talkingToLabel = new Label("Chat history with Bob");
        ticketsContainer = new VBox();
        messageContainer = new VBox();
        activeChatScroll = new ScrollPane(messageContainer);
        currentChatContainer = new VBox();
        userInputContainer = new HBox();
        viewNoteButton = new JFXButton("View Notes");

        archivedTicketsListScroll.getStylesheets().add(this.getClass().getResource("/themes/scrollbar.css").toExternalForm());
        activeChatScroll.getStylesheets().add(this.getClass().getResource("/themes/scrollbar.css").toExternalForm());

        archivedTicketsListScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        archivedTicketsListScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        activeChatScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        activeChatScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Bindings.bindContentBidirectional(messageList, messageContainer.getChildren());

        addContentToWindows();
        positionComponents();
        attachListeners();
    }

    private void positionComponents() {
        archivedTicketsListScroll.prefViewportHeightProperty().bind(this.heightProperty());
        archivedTicketsListScroll.prefViewportWidthProperty().set(ACTIVE_TICKET_LIST_WIDTH);
        currentChatContainer.prefHeightProperty().set(ACTIVE_CHAT_HEIGHT);
        activeChatScroll.prefViewportHeightProperty().bind(currentChatContainer.prefHeightProperty());
        archiveTicketsContent.prefWidthProperty().bind(archivedTicketsListScroll.widthProperty());
        ticketsContainer.prefWidthProperty().bind(archivedTicketsListScroll.widthProperty());

        messageContainer.prefWidthProperty().bind(activeChatScroll.widthProperty());

        this.setLeft(archivedTicketsListScroll);
        this.setCenter(currentChatContainer);
        this.setBottom(userInputContainer);
        activeChatScroll.setContent(messageContainer);

        currentChatContainer.setAlignment(Pos.BASELINE_CENTER);
        userInputContainer.setAlignment(Pos.BASELINE_RIGHT);
        noChatOpenLabel.setAlignment(Pos.BASELINE_CENTER);
        noActiveTicketsLabel.setAlignment(Pos.BASELINE_CENTER);
        messageContainer.setAlignment(Pos.BASELINE_CENTER);

        currentChatContainer.setPadding(new Insets(20, 0, 0, 0));
        userInputContainer.setPadding(new Insets(20, 0, 0, 0));

        userInputContainer.setSpacing(USER_INPUT_CONTAINER_SPACING);
        currentChatContainer.setSpacing(TALKING_TO_LABEL_SPACING);

        activeChatScroll.setVvalue(SCROLL_BAR_VIEW_BOTTOM);
    }

    private void addContentToWindows() {
        currentChatContainer.getChildren().addAll(talkingToLabel, activeChatScroll);
        archivedTicketsListScroll.setContent(archiveTicketsContent);
        archiveTicketsContent.getChildren().add(ticketsContainer);
        userInputContainer.getChildren().addAll(viewNoteButton);
    }

    public void clearTicketContainer() {
        ticketsContainer.getChildren().clear();
        ticketwidgets.clear();
    }

    public void renderTicketWidget(UserTicket userTicket, Session session) {
        AssignedTicketWidget ticketWidget = new AssignedTicketWidget(userTicket.getTicketId(), userTicket.getParticipantId(), userTicket.getAuthorName(),
                userTicket.getDescription(), userTicket.getTicketType(), userTicket.getProfilePictureOfParticipant(), session);

        ticketwidgets.add(ticketWidget);
        ticketsContainer.getChildren().add(ticketWidget);
    }

    public void renderMessageWidgets(List<Message> messages, int sessionId) {
        messageList.clear();
        sortMessagesInDescending(messages);

        for (Message message : messages) {
            if (message.getAuthorId() == sessionId) {
                messageList.add(new MessageWidget(sessionId, message.getMessage(), MessageWidgetOrientation.RIGHT));
            } else {
                messageList.add(new MessageWidget(sessionId, message.getMessage(), MessageWidgetOrientation.LEFT));
            }
        }
    }

    private void sortMessagesInDescending(List<Message> messages) {
        messages.sort(Comparator.comparing(Message::getStringToDateConversion));
    }

    private void attachListeners() {
    }

    public JFXButton getViewNoteButton() {
        return viewNoteButton;
    }

    public List<AssignedTicketWidget> getTicketWidgets() {
        return ticketwidgets;
    }

    public void clearMessageList() {
        messageList.clear();
    }

    public void openNoteWidget(TicketNote ticketNote) {
        TicketNoteWidget ticketNoteWidget = new TicketNoteWidget(ticketNote);
        ticketNoteWidget.open();
    }
}
