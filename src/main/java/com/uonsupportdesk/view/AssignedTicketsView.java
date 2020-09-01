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

    private final VBox ticketsContainer;

    private final VBox messageContainer;

    private final HBox userInputContainer;

    private final TextField userInputField;

    private final Button closeTicketButton;

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    private static final int ACTIVE_CHAT_HEIGHT = 900;

    public AssignedTicketsView() {
        activeTicketsListScroll = new ScrollPane();
        activeChatScroll = new ScrollPane();
        activeTicketsContent = new Pane();
        noActiveTicketsLabel = new Label("No tickets available");
        noChatOpenLabel = new Label("Select a ticket");
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
        setCssStyles();
        layoutChildren();
        System.out.println(messageContainer.getWidth());
    }

    private void positionComponents() {
        activeTicketsListScroll.prefViewportHeightProperty().bind(this.heightProperty());
        activeTicketsListScroll.prefViewportWidthProperty().set(ACTIVE_TICKET_LIST_WIDTH);
        messageContainer.prefHeightProperty().set(ACTIVE_CHAT_HEIGHT);
        activeChatScroll.prefViewportHeightProperty().bind(messageContainer.prefHeightProperty());
        activeTicketsContent.prefHeightProperty().bind(activeTicketsListScroll.heightProperty());
        activeTicketsContent.prefWidthProperty().bind(activeTicketsListScroll.widthProperty());
        ticketsContainer.prefWidthProperty().bind(activeTicketsListScroll.widthProperty());


        this.setLeft(activeTicketsListScroll);
        this.setCenter(messageContainer);
        this.setBottom(userInputContainer);
        messageContainer.setPadding(new Insets(50, 0, 0, 0));

        userInputField.setPrefWidth(1530);
        userInputField.setMaxWidth(1530);
        userInputContainer.setAlignment(Pos.BASELINE_RIGHT);
        userInputContainer.setSpacing(125);
        noChatOpenLabel.setAlignment(Pos.BASELINE_CENTER);
        noActiveTicketsLabel.setAlignment(Pos.BASELINE_CENTER);
    }

    private void addContentToWindows() {
        userInputContainer.getChildren().addAll(closeTicketButton, userInputField);
        messageContainer.getChildren().add(activeChatScroll);
        activeTicketsListScroll.setContent(activeTicketsContent);
        activeTicketsContent.getChildren().add(ticketsContainer);
        for (int i = 0; i < 3; i++)
            ticketsContainer.getChildren().add(new ChatWidget(activeTicketsContent, i, String.valueOf(i), String.valueOf(i), "icons/account-circle.png"));
    }

    private void setCssStyles() {
        userInputContainer.getStyleClass().add("user-input-container");
    }

    private void attachListeners() {

    }
}
