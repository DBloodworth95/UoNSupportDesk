package com.uonsupportdesk.view;

import com.uonsupportdesk.module.component.FaqWidget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.stream.DoubleStream;

public class FaqView extends BorderPane {

    private final VBox headerContainer;

    private final GridPane faqContentContainer;

    private final Label headerLabel;

    private final Label subheaderLabel;

    private final Label faqContentHeaderLabel;

    private final TextField faqTopicSearchTextField;

    private final FaqWidget generalWidget;

    private final FaqWidget financeWidget;

    private final FaqWidget accommodationWidget;

    private final FaqWidget courseWidget;

    private final FaqWidget securityWidget;

    private final FaqWidget otherWidget;

    public FaqView() {
        headerContainer = new VBox();
        faqContentContainer = new GridPane();
        headerLabel = new Label("How can we help you?");
        subheaderLabel = new Label("Feel free to browse the below topics");
        faqContentHeaderLabel = new Label("Frequently Asked Questions");
        faqTopicSearchTextField = new TextField();
        faqTopicSearchTextField.setPromptText("Search keywords here");
        generalWidget = new FaqWidget("General", "Question 1", "Question 2", "Question 3");
        financeWidget = new FaqWidget("Finance", "Question 1", "Question 2", "Question 3");
        accommodationWidget = new FaqWidget("Accommodation", "Question 1", "Question 2", "Question 3");
        courseWidget = new FaqWidget("Courses", "Question 1", "Question 2", "Question 3");
        securityWidget = new FaqWidget("Security", "Question 1", "Question 2", "Question 3");
        otherWidget = new FaqWidget("Other", "Question 1", "Question 2", "Question 3");

        headerContainer.getStyleClass().add("header-container");
        faqContentContainer.getStyleClass().add("faq-header-container");
        faqTopicSearchTextField.getStyleClass().add("faq-search-field");
        headerLabel.getStyleClass().add("faq-header");
        subheaderLabel.getStyleClass().add("faq-subheader");
        faqContentHeaderLabel.getStyleClass().add("faq-content-header");

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
        faqContentContainer.setPadding(new Insets(25, 20, 0, 20));

        faqTopicSearchTextField.setMinHeight(50);
        faqTopicSearchTextField.setMaxWidth(800);


        faqContentContainer.setVgap(75);
        faqContentContainer.getColumnConstraints().addAll(DoubleStream.of(50, 50, 50)
                .mapToObj(width -> {
                    ColumnConstraints constraints = new ColumnConstraints();
                    constraints.setPercentWidth(width);
                    constraints.setFillWidth(true);
                    return constraints;
                }).toArray(ColumnConstraints[]::new));
    }

    private void addContentToWindows() {
        faqContentContainer.add(faqContentHeaderLabel, 0, 0, 2, 1);
        faqContentContainer.add(generalWidget, 0, 1);
        faqContentContainer.add(financeWidget, 1, 1);
        faqContentContainer.add(accommodationWidget, 2, 1);
        faqContentContainer.add(courseWidget, 0, 2);
        faqContentContainer.add(securityWidget, 1, 2);
        faqContentContainer.add(otherWidget, 2, 2);
        headerContainer.getChildren().addAll(headerLabel, faqTopicSearchTextField, subheaderLabel);
    }

    private void attachListeners() {

    }
}
