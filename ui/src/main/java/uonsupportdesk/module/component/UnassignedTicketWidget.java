package uonsupportdesk.module.component;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

public class UnassignedTicketWidget extends HBox {

    private final String ticketType;

    private final Label ticketIDLabel;

    private final Label nameOfUserLabel;

    private final Label ticketReasonLabel;

    private final JFXButton assignTicketButton;

    private final Region idToNameSpacer;

    private final Region nameToDescriptionSpacer;

    private final Region descriptionToAssignSpacer;

    public UnassignedTicketWidget(int ticketId, String nameOfUser, String ticketReason, String ticketType, boolean isAlternate) {
        this.ticketType = ticketType;
        if (isAlternate)
            this.getStyleClass().add("unassigned-ticket-widget");

        ticketIDLabel = new Label(String.valueOf(ticketId));
        nameOfUserLabel = new Label(nameOfUser);
        ticketReasonLabel = new Label(ticketReason);
        assignTicketButton = new JFXButton("Assign");
        idToNameSpacer = new Region();
        nameToDescriptionSpacer = new Region();
        descriptionToAssignSpacer = new Region();
        assignTicketButton.getStyleClass().add("unassigned-assign-ticket-button");

        HBox.setHgrow(idToNameSpacer, Priority.SOMETIMES);
        HBox.setHgrow(nameToDescriptionSpacer, Priority.SOMETIMES);
        HBox.setHgrow(descriptionToAssignSpacer, Priority.SOMETIMES);

        defineComponentLayout();
        addComponents();
    }

    private void defineComponentLayout() {
        this.setPadding(new Insets(20, 50, 20, 50));
        this.setAlignment(Pos.CENTER_LEFT);
        ticketIDLabel.setTextAlignment(TextAlignment.LEFT);
        nameOfUserLabel.setTextAlignment(TextAlignment.LEFT);
        ticketReasonLabel.setTextAlignment(TextAlignment.LEFT);
        nameOfUserLabel.setMinWidth(100);
        ticketReasonLabel.setMinWidth(100);
    }

    private void addComponents() {
        this.getChildren().addAll(nameOfUserLabel, nameToDescriptionSpacer,
                ticketReasonLabel, descriptionToAssignSpacer,
                assignTicketButton);
    }

    public String getTicketType() {
        return ticketType;
    }
}
