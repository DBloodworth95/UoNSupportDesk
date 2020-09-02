package com.uonsupportdesk.module.component;

import javafx.scene.control.Label;

public class MessageWidget extends Label {

    private final int userId;

    private final String message;

    private static final String CSS_CLASS = "message-widget";

    public MessageWidget(int userId, String message) {
        this.userId = userId;
        this.message = message;
        this.getStyleClass().add(CSS_CLASS);
        this.setText(message);
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }
}
