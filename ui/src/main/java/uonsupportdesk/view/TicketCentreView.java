package uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import uonsupportdesk.module.component.ticket.UnassignedTicketWidget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import uonsupportdesk.ticket.UnassignedTicket;

import java.util.ArrayList;
import java.util.List;

public class TicketCentreView extends AnchorPane {

    private final ScrollPane listOfTicketsScrollPane;

    private final VBox listOfTicketsContainer;

    private final VBox sideBarContainer;

    private final VBox totalTicketsContainer;

    private final Label activeTicketsTitleLabel;

    private final Label totalTicketsLabel;

    private final JFXButton searchButton;

    private final JFXButton helpButton;

    private final JFXButton settingsButton;

    private final ObservableList<Node> ticketsList = FXCollections.observableArrayList();

    private final List<UnassignedTicketWidget> unassignedTicketWidgets = new ArrayList<>();

    public TicketCentreView() {
        Image settingsIcon = new Image(getClass().getResourceAsStream("/icons/settings_icon.png"));
        Image helpIcon = new Image(getClass().getResourceAsStream("/icons/help-icon.png"));
        ImageView helpIconView = new ImageView(helpIcon);
        ImageView settingsIconView = new ImageView(settingsIcon);

        listOfTicketsScrollPane = new ScrollPane();
        listOfTicketsContainer = new VBox();
        sideBarContainer = new VBox();
        totalTicketsContainer = new VBox();
        activeTicketsTitleLabel = new Label("Ticket Centre");
        totalTicketsLabel = new Label("Total Tickets: 50");
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
        listOfTicketsContainer.getStyleClass().add("unassigned-tickets-container");
        listOfTicketsScrollPane.getStylesheets().add(this.getClass().getResource("/themes/scrollbar.css").toExternalForm());

        listOfTicketsScrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        listOfTicketsScrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        Bindings.bindContentBidirectional(ticketsList, listOfTicketsContainer.getChildren());

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
        listOfTicketsContainer.prefWidthProperty().bind(totalTicketsContainer.prefWidthProperty());
        listOfTicketsContainer.prefHeightProperty().bind(listOfTicketsScrollPane.heightProperty());

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
        listOfTicketsScrollPane.setPadding(new Insets(30, 0, 10, 100));
        totalTicketsContainer.setPadding(new Insets(30, 0, 0, 0));
    }

    private void addContentToWindows() {
        getChildren().addAll(sideBarContainer, listOfTicketsScrollPane, totalTicketsContainer);
        sideBarContainer.getChildren().addAll(activeTicketsTitleLabel, helpButton, settingsButton);
        totalTicketsContainer.getChildren().add(totalTicketsLabel);
        listOfTicketsScrollPane.setContent(listOfTicketsContainer);
    }

    private void attachListeners() {

    }

    public void renderMessageWidgets(List<UnassignedTicket> unassignedTickets) {
        ticketsList.clear();

        for (int i = 0; i < unassignedTickets.size(); i++) {
            int id = unassignedTickets.get(i).getTicketId();
            String name = unassignedTickets.get(i).getName();
            String ticketType = unassignedTickets.get(i).getTicketType();
            String description = unassignedTickets.get(i).getDescription();
            UnassignedTicketWidget unassignedTicketWidget;

            if (i % 2 == 0) {
                unassignedTicketWidget = new UnassignedTicketWidget(id, name, description, ticketType, true);
            } else {
                unassignedTicketWidget = new UnassignedTicketWidget(id, name, description, ticketType, false);
            }
            unassignedTicketWidgets.add(unassignedTicketWidget);
            ticketsList.add(unassignedTicketWidget);
        }
    }

    public List<UnassignedTicketWidget> getTicketWidgets() {
        return unassignedTicketWidgets;
    }

    public void removeMessageWidget(int ticketIdToRemove, String ticketTypeToRemove) {
        UnassignedTicketWidget unassignedTicketWidgetToRemove = null;

        for (UnassignedTicketWidget unassignedTicketWidget : getTicketWidgets()) {
            if (unassignedTicketWidget.getTicketId() == ticketIdToRemove &&
                    unassignedTicketWidget.getTicketType().equalsIgnoreCase(ticketTypeToRemove)) {
                unassignedTicketWidgetToRemove = unassignedTicketWidget;
            }
        }

        unassignedTicketWidgets.remove(unassignedTicketWidgetToRemove);
        ticketsList.remove(unassignedTicketWidgetToRemove);
        listOfTicketsContainer.getChildren().remove(unassignedTicketWidgetToRemove);
    }
}
