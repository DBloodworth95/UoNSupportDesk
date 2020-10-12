package com.uonsupportdesk.module.component.faq;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FaqWidget extends VBox {

    private final Label titleLabel;

    private final Label questionOneLabel;

    private final Label questionTwoLabel;

    private final Label questionThreeLabel;

    public FaqWidget(String title, String question1, String question2, String question3) {
        this.setSpacing(10);

        titleLabel = new Label(title);
        questionOneLabel = new Label(question1);
        questionTwoLabel = new Label(question2);
        questionThreeLabel = new Label(question3);

        titleLabel.getStyleClass().add("faq-widget-header");
        questionOneLabel.getStyleClass().add("faq-widget-question");
        questionTwoLabel.getStyleClass().add("faq-widget-question");
        questionThreeLabel.getStyleClass().add("faq-widget-question");

        positionComponents();
        addContentToWindow();
    }

    private void positionComponents() {
        titleLabel.setPadding(new Insets(0, 0, 10, 0));
    }

    private void addContentToWindow() {
        getChildren().addAll(titleLabel, questionOneLabel, questionTwoLabel, questionThreeLabel);
    }
}
