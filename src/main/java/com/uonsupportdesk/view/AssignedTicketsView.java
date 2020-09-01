package com.uonsupportdesk.view;

import com.uonsupportdesk.module.component.ChatWidget;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AssignedTicketsView extends BorderPane {

    private final ScrollPane activeTicketsListScroll;
    private final ScrollPane activeChatScroll;
    private final Pane activeTicketsContent;
    private final Pane activeChatContent;
    private final Label noActiveTicketsLabel;
    private final Label noChatOpenLabel;
    private final VBox ticketsContainer;

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    public AssignedTicketsView() {
        activeTicketsListScroll = new ScrollPane();
        activeChatScroll = new ScrollPane();
        activeChatContent = new Pane();
        activeTicketsContent = new Pane();
        noActiveTicketsLabel = new Label("No tickets available");
        noChatOpenLabel = new Label("Select a ticket");
        ticketsContainer = new VBox();

        activeTicketsListScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        activeTicketsListScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        activeChatScroll.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        activeChatScroll.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        positionComponents();
        addContentToWindows();
        attachListeners();
    }

    private void positionComponents() {
        activeTicketsListScroll.prefViewportHeightProperty().bind(this.heightProperty());
        activeTicketsListScroll.prefViewportWidthProperty().set(ACTIVE_TICKET_LIST_WIDTH);
        activeTicketsContent.prefHeightProperty().bind(activeTicketsListScroll.heightProperty());
        activeTicketsContent.prefWidthProperty().bind(activeTicketsListScroll.widthProperty());
        ticketsContainer.prefWidthProperty().bind(activeTicketsListScroll.widthProperty());

        this.setLeft(activeTicketsListScroll);
        this.setCenter(activeChatScroll);

        activeChatContent.getChildren().add(noChatOpenLabel);
        noChatOpenLabel.setAlignment(Pos.BASELINE_CENTER);
        noActiveTicketsLabel.setAlignment(Pos.BASELINE_CENTER);
    }

    private void addContentToWindows() {
        activeChatScroll.setContent(activeChatContent);
        activeTicketsListScroll.setContent(activeTicketsContent);
        activeTicketsContent.getChildren().add(ticketsContainer);
        for (int i = 0; i < 3; i++)
            ticketsContainer.getChildren().add(new ChatWidget(activeTicketsContent, i, String.valueOf(i), String.valueOf(i), "icons/account-circle.png"));
    }

    private void attachListeners() {

    }
}
