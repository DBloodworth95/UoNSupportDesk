package com.uonsupportdesk.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class ActiveTicketsView extends BorderPane {

    private ScrollPane activeTicketsListScroll;
    private ScrollPane activeChatScroll;

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    public ActiveTicketsView() {
        activeTicketsListScroll = new ScrollPane();
        activeChatScroll = new ScrollPane();

        activeChatScroll.prefViewportHeightProperty().bind(this.heightProperty());
        activeChatScroll.prefViewportWidthProperty().bind(this.widthProperty());
        activeTicketsListScroll.prefViewportHeightProperty().bind(this.heightProperty());
        activeTicketsListScroll.prefViewportWidthProperty().set(ACTIVE_TICKET_LIST_WIDTH);

        this.setLeft(activeTicketsListScroll);
        this.setRight(activeChatScroll);
    }
}
