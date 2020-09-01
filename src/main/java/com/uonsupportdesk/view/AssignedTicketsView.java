package com.uonsupportdesk.view;

import com.uonsupportdesk.module.component.ChatWidget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AssignedTicketsView extends BorderPane {

    private final ScrollPane activeTicketsListScroll;

    private final ScrollPane activeChatScroll;

    private final Pane activeTicketsContent;

    private final Label noActiveTicketsLabel;

    private final Label noChatOpenLabel;

    private final Label talkingToLabel;

    private final VBox ticketsContainer;

    private final VBox messageContainer;

    private final HBox userInputContainer;

    private final TextField userInputField;

    private final Button closeTicketButton;

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    private static final int ACTIVE_CHAT_HEIGHT = 900;

    private static final int TALKING_TO_LABEL_SPACING = 20;

    private static final int USER_INPUT_CONTAINER_SPACING = 120;

    public AssignedTicketsView() {
        activeTicketsListScroll = new ScrollPane();
        activeChatScroll = new ScrollPane();
        activeTicketsContent = new Pane();
        noActiveTicketsLabel = new Label("No tickets available");
        noChatOpenLabel = new Label("Select a ticket");
        talkingToLabel = new Label("Currently talking to Bob");
        ticketsContainer = new VBox();
        messageContainer = new VBox();
        userInputContainer = new HBox();
        userInputField = new TextField();
        closeTicketButton = new Button("Close Ticket");

        activeTicketsListScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        activeTicketsListScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        activeChatScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        activeChatScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        addContentToWindows();
        positionComponents();
        attachListeners();
    }

    private void positionComponents() {
        activeTicketsListScroll.prefViewportHeightProperty().bind(this.heightProperty());
        activeTicketsListScroll.prefViewportWidthProperty().set(ACTIVE_TICKET_LIST_WIDTH);
        messageContainer.prefHeightProperty().set(ACTIVE_CHAT_HEIGHT);
        activeChatScroll.prefViewportHeightProperty().bind(messageContainer.prefHeightProperty());
        activeTicketsContent.prefWidthProperty().bind(activeTicketsListScroll.widthProperty());
        ticketsContainer.prefWidthProperty().bind(activeTicketsListScroll.widthProperty());
        userInputField.prefWidthProperty().bind(activeChatScroll.widthProperty());

        this.setLeft(activeTicketsListScroll);
        this.setCenter(messageContainer);
        this.setBottom(userInputContainer);

        messageContainer.setAlignment(Pos.BASELINE_CENTER);
        userInputContainer.setAlignment(Pos.BASELINE_RIGHT);
        noChatOpenLabel.setAlignment(Pos.BASELINE_CENTER);
        noActiveTicketsLabel.setAlignment(Pos.BASELINE_CENTER);

        messageContainer.setPadding(new Insets(20, 0, 0, 0));

        userInputContainer.setSpacing(USER_INPUT_CONTAINER_SPACING);
        messageContainer.setSpacing(TALKING_TO_LABEL_SPACING);
    }

    private void addContentToWindows() {
        userInputContainer.getChildren().addAll(closeTicketButton, userInputField);
        messageContainer.getChildren().addAll(talkingToLabel, activeChatScroll);
        activeTicketsListScroll.setContent(activeTicketsContent);
        activeTicketsContent.getChildren().add(ticketsContainer);
        for (int i = 0; i < 30; i++)
            ticketsContainer.getChildren().add(new ChatWidget(i, String.valueOf(i), String.valueOf(i), "icons/account-circle.png"));
    }

    private void attachListeners() { }
}