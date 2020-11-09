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
    private final int ticketId;

    private final String name;

    private final String description;

    private final String ticketType;

    private final Label nameOfUserLabel;

    private final Label ticketReasonLabel;

    private final JFXButton assignTicketButton;

    private final Region nameToDescriptionSpacer;

    private final Region descriptionToAssignSpacer;

    public UnassignedTicketWidget(int ticketId, String name, String description, String ticketType, boolean isAlternate) {
        this.ticketId = ticketId;
        this.name = name;
        this.description = description;
        this.ticketType = ticketType;

        if (isAlternate) {
            this.getStyleClass().add("unassigned-ticket-widget");
        }

        nameOfUserLabel = new Label(name);
        ticketReasonLabel = new Label(description);
        assignTicketButton = new JFXButton("Assign");
        nameToDescriptionSpacer = new Region();
        descriptionToAssignSpacer = new Region();
        assignTicketButton.getStyleClass().add("unassigned-assign-ticket-button");

        HBox.setHgrow(nameToDescriptionSpacer, Priority.SOMETIMES);
        HBox.setHgrow(descriptionToAssignSpacer, Priority.SOMETIMES);

        defineComponentLayout();
        addComponents();
    }

    private void defineComponentLayout() {
        this.setPadding(new Insets(20, 50, 20, 50));
        this.setAlignment(Pos.CENTER_LEFT);
        nameOfUserLabel.setAlignment(Pos.CENTER_LEFT);
        ticketReasonLabel.setAlignment(Pos.CENTER_LEFT);
        nameOfUserLabel.setTextAlignment(TextAlignment.LEFT);
        ticketReasonLabel.setTextAlignment(TextAlignment.LEFT);

        nameOfUserLabel.setMinWidth(100);
        assignTicketButton.setMinWidth(100);
    }

    private void addComponents() {
        this.getChildren().addAll(nameOfUserLabel, nameToDescriptionSpacer,
                ticketReasonLabel, descriptionToAssignSpacer,
                assignTicketButton);
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTicketType() {
        return ticketType;
    }
}
