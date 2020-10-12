package com.uonsupportdesk.view;

import com.uonsupportdesk.module.component.faq.FaqQuestion;
import com.uonsupportdesk.module.component.faq.FaqTopic;
import com.uonsupportdesk.module.component.faq.FaqWidget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class FaqView extends BorderPane {

    private final VBox headerContainer;

    private final GridPane faqContentContainer;

    private final Label headerLabel;

    private final Label subheaderLabel;

    private final Label faqContentHeaderLabel;

    private final Label searchResultsLabel;

    private final TextField faqTopicSearchTextField;

    private final FaqWidget generalWidget;

    private final FaqWidget financeWidget;

    private final FaqWidget accommodationWidget;

    private final FaqWidget courseWidget;

    private final FaqWidget securityWidget;

    private final FaqWidget otherWidget;

    private final List<FaqWidget> widgets = new ArrayList<>();

    public FaqView() {
        headerContainer = new VBox();
        faqContentContainer = new GridPane();
        headerLabel = new Label("How can we help you?");
        subheaderLabel = new Label("Feel free to browse the below topics");
        faqContentHeaderLabel = new Label("Frequently Asked Questions");
        searchResultsLabel = new Label("Search Results");
        faqTopicSearchTextField = new TextField();
        faqTopicSearchTextField.setPromptText("Search keywords here");
        generalWidget = new FaqWidget("General",
                new FaqQuestion("Question 1", FaqTopic.GENERAL),
                new FaqQuestion("Question 2", FaqTopic.GENERAL),
                new FaqQuestion("Question 3", FaqTopic.GENERAL));
        financeWidget = new FaqWidget("Finance",
                new FaqQuestion("Question 1", FaqTopic.FINANCE),
                new FaqQuestion("Question 2", FaqTopic.FINANCE),
                new FaqQuestion("Question 3", FaqTopic.FINANCE));
        accommodationWidget = new FaqWidget("Accommodation",
                new FaqQuestion("Question 1", FaqTopic.ACCOMMODATION),
                new FaqQuestion("Question 2", FaqTopic.ACCOMMODATION),
                new FaqQuestion("Question 3", FaqTopic.ACCOMMODATION));
        courseWidget = new FaqWidget("Courses",
                new FaqQuestion("Question 1", FaqTopic.COURSE),
                new FaqQuestion("Question 2", FaqTopic.COURSE),
                new FaqQuestion("Question 3", FaqTopic.COURSE));
        securityWidget = new FaqWidget("Security",
                new FaqQuestion("Question 1", FaqTopic.SECURITY),
                new FaqQuestion("Question 2", FaqTopic.SECURITY),
                new FaqQuestion("Question 3", FaqTopic.SECURITY));
        otherWidget = new FaqWidget("Other",
                new FaqQuestion("Question 1", FaqTopic.OTHER),
                new FaqQuestion("Question 2", FaqTopic.OTHER),
                new FaqQuestion("Question 3", FaqTopic.OTHER));
        widgets.add(generalWidget);
        widgets.add(financeWidget);
        widgets.add(accommodationWidget);
        widgets.add(courseWidget);
        widgets.add(securityWidget);
        widgets.add(otherWidget);

        headerContainer.getStyleClass().add("header-container");
        faqContentContainer.getStyleClass().add("faq-header-container");
        faqTopicSearchTextField.getStyleClass().add("faq-search-field");
        headerLabel.getStyleClass().add("faq-header");
        subheaderLabel.getStyleClass().add("faq-subheader");
        faqContentHeaderLabel.getStyleClass().add("faq-content-header");
        searchResultsLabel.getStyleClass().add("faq-content-header");

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
        faqTopicSearchTextField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                searchTopics(faqTopicSearchTextField.getText().toLowerCase());
            }
        });
    }

    private void searchTopics(String valueToSearch) {
        FaqWidget resultWidget = new FaqWidget();
        faqContentContainer.getChildren().clear();
        faqContentContainer.getChildren().removeAll();
        faqContentContainer.add(searchResultsLabel, 0, 0, 2, 1);
        faqContentContainer.add(resultWidget, 1, 1);
        for (FaqWidget faqWidget : widgets) {
            for (FaqQuestion faqQuestion : faqWidget.getQuestions()) {
                for (String keyWord : faqQuestion.getKeyWords()) {
                    if (valueToSearch.toLowerCase().contains(keyWord.toLowerCase())) {
                        resultWidget.addQuestion(faqQuestion.asString());
                    }
                }
            }
        }
    }
}
