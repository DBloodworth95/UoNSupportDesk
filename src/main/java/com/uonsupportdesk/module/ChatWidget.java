package com.uonsupportdesk.module;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ChatWidget extends VBox {

    private static final int CHAT_WIDGET_HEIGHT = 75;

    public ChatWidget(Pane contentPaneToSnapTo) {
        this.getStyleClass().add("chat-widget");
        this.prefHeightProperty().set(CHAT_WIDGET_HEIGHT);
        this.prefWidthProperty().bind(contentPaneToSnapTo.widthProperty());
        this.getChildren().add(new Label("Test"));
    }
}
