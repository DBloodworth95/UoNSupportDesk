package com.uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class UnassignedTicketsView extends BorderPane {

    private final ScrollPane listOfTicketsScrollPane;

    private final VBox listOfTicketsContainer;

    private final VBox sideBarContainer;

    private final VBox totalTicketsContainer;

    private final Label activeTicketsTitleLabel;

    private final JFXButton searchButton;

    private final JFXButton helpButton;

    private final JFXButton settingsButton;

    public UnassignedTicketsView() {
        listOfTicketsScrollPane = new ScrollPane();
        listOfTicketsContainer = new VBox();
        sideBarContainer = new VBox();
        totalTicketsContainer = new VBox();
        activeTicketsTitleLabel = new Label("Active Tickets");
        searchButton = new JFXButton("Search");
        helpButton = new JFXButton("Help");
        settingsButton = new JFXButton("Settings");

        settingsButton.getStyleClass().add("unassigned-ticket-buttons");
        searchButton.getStyleClass().add("unassigned-ticket-buttons");
        helpButton.getStyleClass().add("unassigned-ticket-buttons");
        listOfTicketsScrollPane.getStylesheets().add(this.getClass().getResource("/themes/scrollbar.css").toExternalForm());

        listOfTicketsScrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        listOfTicketsScrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    private void positionComponents() {

    }

    private void addContentToWindows() {

    }

    private void attachListeners() {

    }
}
