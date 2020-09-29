package com.uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


public class UnassignedTicketsView extends AnchorPane {

    private final ScrollPane listOfTicketsScrollPane;

    private final VBox listOfTicketsContainer;

    private final VBox sideBarContainer;

    private final VBox totalTicketsContainer;

    private final Label activeTicketsTitleLabel;

    private final Label totalTicketsLabel;

    private final JFXButton searchButton;

    private final JFXButton helpButton;

    private final JFXButton settingsButton;

    public UnassignedTicketsView() {
        Image settingsIcon = new Image(getClass().getResourceAsStream("/icons/settings_icon.png"));
        Image helpIcon = new Image(getClass().getResourceAsStream("/icons/help-icon.png"));
        ImageView helpIconView = new ImageView(helpIcon);
        ImageView settingsIconView = new ImageView(settingsIcon);

        listOfTicketsScrollPane = new ScrollPane();
        listOfTicketsContainer = new VBox();
        sideBarContainer = new VBox();
        totalTicketsContainer = new VBox();
        activeTicketsTitleLabel = new Label("Active Tickets");
        totalTicketsLabel = new Label("Total Tickets: ");
        searchButton = new JFXButton("Search");
        helpButton = new JFXButton("Help", helpIconView);
        settingsButton = new JFXButton("Settings", settingsIconView);

        settingsButton.setPrefWidth(200);
        settingsButton.setGraphicTextGap(5);
        helpButton.setGraphicTextGap(5);
        helpButton.setPrefWidth(200);

        totalTicketsLabel.setFont(new Font(20));
        activeTicketsTitleLabel.setFont(new Font(24));

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
        totalTicketsContainer.prefWidthProperty().bind(listOfTicketsScrollPane.widthProperty().subtract(200));

        getChildren().addAll(sideBarContainer, listOfTicketsScrollPane, totalTicketsContainer);
        setTopAnchor(sideBarContainer, 0.0);
        setTopAnchor(listOfTicketsScrollPane, 0.0);
        setLeftAnchor(sideBarContainer, 0.0);
        setRightAnchor(listOfTicketsScrollPane, 0.0);
        setRightAnchor(totalTicketsContainer, 100.0);
        setBottomAnchor(totalTicketsContainer, 0.0);
        setBottomAnchor(sideBarContainer, 0.0);

        sideBarContainer.setAlignment(Pos.CENTER);
        totalTicketsContainer.setAlignment(Pos.TOP_LEFT);

        VBox.setMargin(activeTicketsTitleLabel, new Insets(0, 0, 50, 0));
        VBox.setMargin(helpButton, new Insets(0, 0, 250, 0));
        VBox.setMargin(settingsButton, new Insets(400, 0, 0, 0));
        totalTicketsContainer.setPadding(new Insets(30, 0, 0, 0));
    }

    private void addContentToWindows() {
        sideBarContainer.getChildren().addAll(activeTicketsTitleLabel, helpButton, settingsButton);
        totalTicketsContainer.getChildren().add(totalTicketsLabel);
    }

    private void attachListeners() {

    }
}
