package uonsupportdesk.module.component;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class UnassignedTicketWidget extends HBox {

    private final Label ticketIDLabel;

    private final Label nameOfUserLabel;

    private final Label ticketReasonLabel;

    private final JFXButton assignTicketButton;

    public UnassignedTicketWidget(int ticketId, String nameOfUser, String ticketReason, boolean isAlternate) {
        if (isAlternate)
        this.getStyleClass().add("unassigned-ticket-widget");

        ticketIDLabel = new Label(String.valueOf(ticketId));
        nameOfUserLabel = new Label(nameOfUser);
        ticketReasonLabel = new Label(ticketReason);
        assignTicketButton = new JFXButton("Assign");
        assignTicketButton.getStyleClass().add("unassigned-assign-ticket-button");

        defineComponentLayout();
        addComponents();
    }

    private void defineComponentLayout() {
        this.setPadding(new Insets(20, 0, 20, 0));
        ticketIDLabel.setWrapText(true);
        nameOfUserLabel.setWrapText(true);
        ticketReasonLabel.setWrapText(true);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(150);
    }

    private void addComponents() {
        this.getChildren().addAll(ticketIDLabel, nameOfUserLabel, ticketReasonLabel, assignTicketButton);
    }
}
