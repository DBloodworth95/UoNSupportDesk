package com.uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class UnassignedTicketsView extends AnchorPane {

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
        sideBarContainer.getStyleClass().add("unassigned-ticket-sidebar");
        totalTicketsContainer.getStyleClass().add("unassigned-ticket-lower-pane");
        listOfTicketsScrollPane.getStylesheets().add(this.getClass().getResource("/themes/scrollbar.css").toExternalForm());

        listOfTicketsScrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        listOfTicketsScrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        positionComponents();
        addContentToWindows();
        attachListeners();
    }

    private void positionComponents() {
        listOfTicketsScrollPane.prefViewportHeightProperty().bind(this.heightProperty().multiply(0.8));
        sideBarContainer.prefHeightProperty().bind(this.heightProperty());
        sideBarContainer.prefWidthProperty().bind(this.widthProperty().multiply(0.2));
        listOfTicketsScrollPane.prefWidthProperty().bind(this.widthProperty().subtract(sideBarContainer.widthProperty()));
        totalTicketsContainer.prefHeightProperty().bind(this.heightProperty().subtract(listOfTicketsScrollPane.heightProperty()));
        totalTicketsContainer.prefWidthProperty().bind(listOfTicketsScrollPane.widthProperty());

        getChildren().addAll(sideBarContainer, listOfTicketsScrollPane, totalTicketsContainer);
        setTopAnchor(sideBarContainer, 0.0);
        setTopAnchor(listOfTicketsScrollPane, 0.0);
        setLeftAnchor(sideBarContainer, 0.0);
        setRightAnchor(listOfTicketsScrollPane, 0.0);
        setRightAnchor(totalTicketsContainer, 0.0);
        setBottomAnchor(totalTicketsContainer, 0.0);
        setBottomAnchor(sideBarContainer, 0.0);
    }

    private void addContentToWindows() {

    }

    private void attachListeners() {

    }
}
