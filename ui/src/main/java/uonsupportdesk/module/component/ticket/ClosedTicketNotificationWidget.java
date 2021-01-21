package uonsupportdesk.module.component.ticket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class ClosedTicketNotificationWidget extends VBox {

    private static final String NOTIFICATION_MESSAGE = "This ticket has been closed! This conversation" +
            " will migrate this ticket to your \"Archived Tickets\" section in 5 seconds.";

    private static final Color WIDGET_COLOUR = Color.rgb(128, 128, 128, 0.8);

    private Background widgetBackground;

    public ClosedTicketNotificationWidget() {
        loadWidgetBackgrounds();
        setupLabel();
    }

    private void loadWidgetBackgrounds() {
        widgetBackground = new Background(
                new BackgroundFill(WIDGET_COLOUR, new CornerRadii(5, 0, 5, 5, false), Insets.EMPTY));
    }

    private void setupLabel() {
        Label messageToDisplay = new Label(NOTIFICATION_MESSAGE);
        messageToDisplay.setPadding(new Insets(10));
        messageToDisplay.setWrapText(true);
        SVGPath orientationIndicator = new SVGPath();

        messageToDisplay.setBackground(widgetBackground);
        messageToDisplay.setAlignment(Pos.CENTER_RIGHT);
        orientationIndicator.setContent("M10 0 L0 10 L0 0 Z");
        orientationIndicator.setFill(WIDGET_COLOUR);

        HBox container = new HBox(messageToDisplay, orientationIndicator);
        container.maxWidthProperty().bind(widthProperty().multiply(1));

        getChildren().setAll(container);
        setAlignment(Pos.CENTER_RIGHT);
        container.setPadding(new Insets(20, 50, 0, 0));
    }
}
