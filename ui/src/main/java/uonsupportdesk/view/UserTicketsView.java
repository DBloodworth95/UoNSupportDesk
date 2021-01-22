package uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import uonsupportdesk.module.component.ticket.AssignedTicketWidget;
import uonsupportdesk.module.component.ticket.ClosedTicketNotificationWidget;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import uonsupportdesk.ticket.Message;
import uonsupportdesk.ticket.UserTicket;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserTicketsView extends BorderPane {

    private final List<AssignedTicketWidget> ticketWidgets = new ArrayList<>();

    private final ScrollPane activeTicketsListScroll;

    private final ScrollPane activeChatScroll;

    private final Pane activeTicketsContent;

    private final Label noActiveTicketsLabel;

    private final Label noChatOpenLabel;

    private final Label talkingToLabel;

    private final VBox ticketsContainer;

    private final VBox currentChatContainer;

    private final VBox messageContainer;

    private final HBox userInputContainer;

    private final TextField userInputField;

    private final JFXButton closeTicketButton;

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    private static final int ACTIVE_CHAT_HEIGHT = 900;

    private static final int TALKING_TO_LABEL_SPACING = 20;

    private static final int USER_INPUT_CONTAINER_SPACING = 120;

    private static final double SCROLL_BAR_VIEW_BOTTOM = 1.0f;

    private final ObservableList<Node> messageList = FXCollections.observableArrayList();

    public UserTicketsView() {
        this.getStyleClass().add("module-background");
        this.setPadding(new Insets(10));
        activeTicketsListScroll = new ScrollPane();
        activeTicketsContent = new Pane();
        noActiveTicketsLabel = new Label("No tickets available");
        noChatOpenLabel = new Label("Select a ticket");
        talkingToLabel = new Label("Currently talking to Bob");
        ticketsContainer = new VBox();
        messageContainer = new VBox();
        activeChatScroll = new ScrollPane(messageContainer);
        currentChatContainer = new VBox();
        userInputContainer = new HBox();
        userInputField = new TextField();
        closeTicketButton = new JFXButton("Close Ticket");

        closeTicketButton.getStyleClass().add("assigned-ticket-buttons");
        userInputField.getStyleClass().add("chats-user-input-field");
        activeTicketsListScroll.getStylesheets().add(this.getClass().getResource("/themes/scrollbar.css").toExternalForm());
        activeChatScroll.getStylesheets().add(this.getClass().getResource("/themes/scrollbar.css").toExternalForm());

        activeTicketsListScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        activeTicketsListScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        activeChatScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        activeChatScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Bindings.bindContentBidirectional(messageList, messageContainer.getChildren());

        userInputField.setMinHeight(50);

        addContentToWindows();
        positionComponents();
        attachListeners();
    }

    private void positionComponents() {
        activeTicketsListScroll.prefViewportHeightProperty().bind(this.heightProperty());
        activeTicketsListScroll.prefViewportWidthProperty().set(ACTIVE_TICKET_LIST_WIDTH);
        currentChatContainer.prefHeightProperty().set(ACTIVE_CHAT_HEIGHT);
        activeChatScroll.prefViewportHeightProperty().bind(currentChatContainer.prefHeightProperty());
        activeTicketsContent.prefWidthProperty().bind(activeTicketsListScroll.widthProperty());
        ticketsContainer.prefWidthProperty().bind(activeTicketsListScroll.widthProperty());
        userInputField.prefWidthProperty().bind(activeChatScroll.widthProperty());

        messageContainer.prefWidthProperty().bind(activeChatScroll.widthProperty());

        this.setLeft(activeTicketsListScroll);
        this.setCenter(currentChatContainer);
        this.setBottom(userInputContainer);
        activeChatScroll.setContent(messageContainer);

        currentChatContainer.setAlignment(Pos.BASELINE_CENTER);
        userInputContainer.setAlignment(Pos.CENTER_RIGHT);
        noChatOpenLabel.setAlignment(Pos.BASELINE_CENTER);
        noActiveTicketsLabel.setAlignment(Pos.BASELINE_CENTER);
        messageContainer.setAlignment(Pos.BASELINE_CENTER);

        currentChatContainer.setPadding(new Insets(0, 0, 0, 0));
        userInputContainer.setPadding(new Insets(20, 0, 0, 0));

        userInputContainer.setSpacing(USER_INPUT_CONTAINER_SPACING);
        currentChatContainer.setSpacing(TALKING_TO_LABEL_SPACING);
        activeChatScroll.setVvalue(SCROLL_BAR_VIEW_BOTTOM);
        activeChatScroll.vvalueProperty().bind(messageContainer.heightProperty());
    }

    private void addContentToWindows() {
        userInputContainer.getChildren().addAll(closeTicketButton, userInputField);
        currentChatContainer.getChildren().addAll(talkingToLabel, activeChatScroll);
        activeTicketsListScroll.setContent(activeTicketsContent);
        activeTicketsContent.getChildren().add(ticketsContainer);
    }

    private void clearTicketContainer() {
        ticketsContainer.getChildren().clear();
    }

    public void renderTicketWidgets(List<UserTicket> userTickets) {
        clearTicketContainer();

        for (UserTicket userTicket : userTickets) {
            AssignedTicketWidget ticketWidget = new AssignedTicketWidget(userTicket.getTicketId(), userTicket.getAuthorName(),
                    userTicket.getDescription(), userTicket.getTicketType(), "icons/account-circle.png");

            ticketsContainer.getChildren().add(ticketWidget);
            ticketWidgets.add(ticketWidget);
        }

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

    public void renderSingularMessageWidget(int sessionId, int authorId, String messageBody) {
        if (sessionId == authorId) {
            messageList.add(new MessageWidget(authorId, messageBody, MessageWidgetOrientation.RIGHT));
        } else {
            messageList.add(new MessageWidget(authorId, messageBody, MessageWidgetOrientation.LEFT));
        }
    }

    private void sortMessagesInDescending(List<Message> messages) {
        messages.sort(Comparator.comparing(Message::getStringToDateConversion));
    }

    public void attachListeners() {
    }

    public List<AssignedTicketWidget> getTicketWidgets() {
        return ticketWidgets;
    }

    public TextField getUserInputField() {
        return userInputField;
    }

    public void clearUserInputField() {
        userInputField.clear();
    }

    public void clearMessageList() {
        messageList.clear();
    }

    public void closeCurrentTicket(int ticketId, String ticketType) {
        userInputField.setEditable(false);
        closeTicketButton.setVisible(false);
        setTicketToArchived(ticketId, ticketType);
    }

    public void unlockChat() {
        closeTicketButton.setVisible(true);
        userInputField.setEditable(true);
    }

    public void notifyOfClosedTicket(int ticketId, String ticketType) {
        for (AssignedTicketWidget ticketWidget : ticketWidgets) {
            if (ticketWidget.getTicketId() == ticketId && ticketWidget.getTicketType().equalsIgnoreCase(ticketType)) {
                ClosedTicketNotificationWidget closedTicketNotificationWidget = new ClosedTicketNotificationWidget(ticketId);
                currentChatContainer.getChildren().add(closedTicketNotificationWidget);
                closedTicketNotificationWidget.showNotification();
            }
        }
        removeTicketWidget(ticketId, ticketType);
    }

    public void removeTicketWidget(int ticketId, String ticketType) {
        for (AssignedTicketWidget ticketWidget : ticketWidgets) {
            if (ticketWidget.getTicketId() == ticketId && ticketWidget.getTicketType().equalsIgnoreCase(ticketType)) {
                ticketsContainer.getChildren().remove(ticketWidget);
            }
        }
        ticketWidgets.removeIf(ticketWidget -> ticketWidget.getTicketId() == ticketId &&
                ticketWidget.getTicketType().equalsIgnoreCase(ticketType));
    }

    public void removeWidgetsIfArchived() {
        AssignedTicketWidget ticketWidgetToArchive = null;
        for (AssignedTicketWidget ticketWidget : ticketWidgets) {
            if (ticketWidget.isArchived()) {
                ticketsContainer.getChildren().remove(ticketWidget);
                ticketWidgetToArchive = ticketWidget;
            }
        }
        ticketWidgets.remove(ticketWidgetToArchive);
    }

    public void setTicketToArchived(int ticketId, String ticketType) {
        for (AssignedTicketWidget ticketWidget : ticketWidgets) {
            if (ticketWidget.getTicketId() == ticketId && ticketWidget.getTicketType().equalsIgnoreCase(ticketType)) {
                ticketWidget.archive();
            }
        }
    }
}
