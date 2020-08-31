package com.uonsupportdesk.module;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ChatWidget extends VBox {

    private static final int CHAT_WIDGET_HEIGHT = 75;

    private static final int GRAY_RGB_CODE = 211;

    private static final int WHITE_RGB_CODE = 255;

    private final Background widgetBackground;

    private final Background hoveredBackground;

    public ChatWidget(Pane contentPaneToSnapTo) {
        this.getStyleClass().add("chat-widget");
        this.prefHeightProperty().set(CHAT_WIDGET_HEIGHT);
        this.prefWidthProperty().bind(contentPaneToSnapTo.widthProperty());
        this.getChildren().add(new Label("Test"));

        BackgroundFill widgetBackgroundFill = new BackgroundFill(Color.rgb(WHITE_RGB_CODE, WHITE_RGB_CODE, WHITE_RGB_CODE), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill hoveredBackgroundFill = new BackgroundFill(Color.rgb(GRAY_RGB_CODE, GRAY_RGB_CODE, GRAY_RGB_CODE), CornerRadii.EMPTY, Insets.EMPTY);
        widgetBackground = new Background(widgetBackgroundFill);
        hoveredBackground = new Background(hoveredBackgroundFill);

        highlightOnHover();
    }

    private void highlightOnHover() {
        this.setOnMouseEntered(e -> {
            this.setBackground(hoveredBackground);
        });
        this.setOnMouseExited(e -> {
            this.setBackground(widgetBackground);
        });
    }
}
