package com.uonsupportdesk.view;

import com.uonsupportdesk.module.ChatWidget;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ActiveTicketsView extends BorderPane {

    private final ScrollPane activeTicketsListScroll;
    private final ScrollPane activeChatScroll;
    private final Pane activeTicketsContent;
    private final Pane activeChatContent;
    private final Label noActiveTicketsLabel;
    private final Label noChatOpenLabel;

    private static final int ACTIVE_TICKET_LIST_WIDTH = 300;

    public ActiveTicketsView() {
        activeTicketsListScroll = new ScrollPane();
        activeChatScroll = new ScrollPane();
        activeChatContent = new Pane();
        activeTicketsContent = new Pane();
        noActiveTicketsLabel = new Label("No tickets available");
        noChatOpenLabel = new Label("Select a ticket");

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

        this.setLeft(activeTicketsListScroll);
        this.setCenter(activeChatScroll);

        activeChatContent.getChildren().add(noChatOpenLabel);
        //activeTicketsContent.getChildren().add(noActiveTicketsLabel);
        noChatOpenLabel.setAlignment(Pos.BASELINE_CENTER);
        noActiveTicketsLabel.setAlignment(Pos.BASELINE_CENTER);
    }

    private void addContentToWindows() {
        activeChatScroll.setContent(activeChatContent);
        activeTicketsListScroll.setContent(activeTicketsContent);
        activeTicketsContent.getChildren().add(new ChatWidget(activeTicketsContent, "Test Name", "Test Issue"));
    }

    private void attachListeners() {

    }
}
