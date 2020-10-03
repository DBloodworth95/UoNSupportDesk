package com.uonsupportdesk.module.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class MessageWidget extends VBox {

    public final WidgetOrientation direction;

    private final int userId;

    private final String message;

    private Label messageToDisplay;

    private SVGPath orientationIndicator;

    private static final Color SENDER_COLOUR = Color.rgb(70, 163, 221, 0.8);

    private static final Color RECEIVER_COLOUR = Color.rgb(25, 124, 163, 0.25);

    private static Background SENDER_BACKGROUND;

    private static Background RECEIVER_BACKGROUND;

    public MessageWidget(int userId, String message, WidgetOrientation direction) {
        this.userId = userId;
        this.message = message;
        this.direction = direction;
        loadWidgetBackgrounds();
        setupLabel();
    }

    private void loadWidgetBackgrounds() {
        SENDER_BACKGROUND = new Background(
                new BackgroundFill(SENDER_COLOUR, new CornerRadii(5, 0, 5, 5, false), Insets.EMPTY));
        RECEIVER_BACKGROUND = new Background(
                new BackgroundFill(RECEIVER_COLOUR, new CornerRadii(0, 5, 5, 5, false), Insets.EMPTY));
    }

    private void setupLabel() {
        messageToDisplay = new Label(message);
        messageToDisplay.setPadding(new Insets(10));
        messageToDisplay.setWrapText(true);
        orientationIndicator = new SVGPath();

        if (direction == WidgetOrientation.LEFT) {
            configureReceiverMessage();
        } else {
            configureSenderMessage();
        }
    }

    private void configureSenderMessage() {
        messageToDisplay.setBackground(SENDER_BACKGROUND);
        messageToDisplay.setAlignment(Pos.CENTER_RIGHT);
        orientationIndicator.setContent("M10 0 L0 10 L0 0 Z");
        orientationIndicator.setFill(SENDER_COLOUR);

        HBox container = new HBox(messageToDisplay, orientationIndicator);
        container.maxWidthProperty().bind(widthProperty().multiply(0.3));

        getChildren().setAll(container);
        setAlignment(Pos.CENTER_RIGHT);
        container.setPadding(new Insets(20, 50, 0, 0));
    }

    private void configureReceiverMessage() {
        messageToDisplay.setBackground(RECEIVER_BACKGROUND);
        messageToDisplay.setAlignment(Pos.CENTER_LEFT);
        orientationIndicator.setContent("M10 0 L0 0 L0 10 Z");
        orientationIndicator.setFill(RECEIVER_COLOUR);

        HBox container = new HBox(messageToDisplay, orientationIndicator);
        container.maxWidthProperty().bind(widthProperty().multiply(0.3));

        getChildren().setAll(container);
        setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(20, 0, 0, 50));
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
