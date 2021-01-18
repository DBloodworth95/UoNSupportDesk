package uonsupportdesk.view;

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

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    private static final int ACTIVE_CHAT_HEIGHT = 900;

    private static final int TALKING_TO_LABEL_SPACING = 20;

    private static final int USER_INPUT_CONTAINER_SPACING = 120;

    private static final int SCROLL_BAR_VIEW_BOTTOM = 1;

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

        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.LEFT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.RIGHT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.LEFT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.RIGHT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.LEFT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.RIGHT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.LEFT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.RIGHT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.LEFT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.RIGHT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.LEFT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.RIGHT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.LEFT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.RIGHT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.LEFT));
        messageList.add(new MessageWidget(1, "TestTestTestTestTestTestTestTestTest", MessageWidgetOrientation.RIGHT));
    }

    private void attachListeners() {
    }
}
