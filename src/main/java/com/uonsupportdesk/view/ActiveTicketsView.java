package com.uonsupportdesk.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ActiveTicketsView extends BorderPane {

    private ScrollPane activeTicketsListScroll;
    private ScrollPane activeChatScroll;
    private Pane activeTicketsContent;
    private Pane activeChatContent;
    private Label noActiveTicketsLabel;
    private Label noChatOpenLabel;

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    public ActiveTicketsView() {
        activeTicketsListScroll = new ScrollPane();
        activeChatScroll = new ScrollPane();
        activeChatContent = new Pane();
        activeTicketsContent = new Pane();
        noActiveTicketsLabel = new Label("No tickets available");
        noChatOpenLabel = new Label("Select a ticket");

        activeChatScroll.prefViewportHeightProperty().bind(this.heightProperty());
        activeChatScroll.prefViewportWidthProperty().bind(this.widthProperty());
        activeTicketsListScroll.prefViewportHeightProperty().bind(this.heightProperty());
        activeTicketsListScroll.prefViewportWidthProperty().set(ACTIVE_TICKET_LIST_WIDTH);

        this.setLeft(activeTicketsListScroll);
        this.setRight(activeChatScroll);

        activeChatScroll.setContent(activeChatContent);
        activeTicketsListScroll.setContent(activeTicketsContent);

        activeChatContent.getChildren().add(noChatOpenLabel);
        activeTicketsContent.getChildren().add(noActiveTicketsLabel);

        noChatOpenLabel.setAlignment(Pos.BASELINE_CENTER);
        noActiveTicketsLabel.setAlignment(Pos.BASELINE_CENTER);
    }
}
