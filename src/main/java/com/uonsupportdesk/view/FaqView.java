package com.uonsupportdesk.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class FaqView extends BorderPane {

    private final VBox headerContainer;

    private final GridPane faqContentContainer;

    private final Label headerLabel;

    private final Label subheaderLabel;

    private final Label faqContentHeaderLabel;

    private final TextField faqTopicSearchTextField;

    public FaqView() {
        headerContainer = new VBox();
        faqContentContainer = new GridPane();
        headerLabel = new Label("How can we help you?");
        subheaderLabel = new Label("Feel free to browse the below topics");
        faqContentHeaderLabel = new Label("Frequently Asked Questions");
        faqTopicSearchTextField = new TextField();
        faqTopicSearchTextField.setPromptText("Search keywords here");

        headerContainer.getStyleClass().add("header-container");
        faqContentContainer.getStyleClass().add("faq-header-container");
        faqTopicSearchTextField.getStyleClass().add("faq-search-field");
        headerLabel.getStyleClass().add("faq-header");
        subheaderLabel.getStyleClass().add("faq-subheader");

        positionComponents();
        addContentToWindows();
        attachListeners();
    }

    private void positionComponents() {
        headerContainer.prefHeightProperty().bind(this.heightProperty().multiply(0.25));
        faqContentContainer.prefHeightProperty().bind(this.heightProperty().multiply(0.75));

        setTop(headerContainer);
        setBottom(faqContentContainer);

        headerContainer.setAlignment(Pos.CENTER);
        headerContainer.setSpacing(50);
        headerLabel.setPadding(new Insets(20, 0, 0, 0));
        subheaderLabel.setPadding(new Insets(0, 0, 20, 0));

        faqTopicSearchTextField.setMinHeight(50);
        faqTopicSearchTextField.setMaxWidth(800);
    }

    private void addContentToWindows() {
        headerContainer.getChildren().addAll(headerLabel, faqTopicSearchTextField, subheaderLabel);
    }

    private void attachListeners() {

    }
}
