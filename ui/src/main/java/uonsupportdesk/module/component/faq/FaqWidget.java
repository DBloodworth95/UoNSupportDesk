package uonsupportdesk.module.component.faq;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class FaqWidget extends VBox {

    private Label titleLabel;

    private Label questionOneLabel;

    private Label questionTwoLabel;

    private Label questionThreeLabel;

    private final List<FaqQuestion> questions = new ArrayList<>();

    public FaqWidget(String title, FaqQuestion question1, FaqQuestion question2, FaqQuestion question3) {
        this.setSpacing(10);

        titleLabel = new Label(title);
        questionOneLabel = question1.asLabel();
        questionTwoLabel = question2.asLabel();
        questionThreeLabel = question3.asLabel();

        titleLabel.getStyleClass().add("faq-widget-header");
        questionOneLabel.getStyleClass().add("faq-widget-question");
        questionTwoLabel.getStyleClass().add("faq-widget-question");
        questionThreeLabel.getStyleClass().add("faq-widget-question");
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        positionComponents();
        addContentToWindow();
    }

    public FaqWidget() {
        this.setSpacing(30);
    }

    private void positionComponents() {
        this.setPadding(new Insets(20, 20, 20, 20));
        titleLabel.setPadding(new Insets(0, 0, 10, 0));
    }

    private void addContentToWindow() {
        getChildren().addAll(titleLabel, questionOneLabel, questionTwoLabel, questionThreeLabel);
    }

    public void addQuestion(String question) {
        Label labelToAdd = new Label(question);
        labelToAdd.getStyleClass().add("faq-widget-question");
        getChildren().addAll(labelToAdd);
    }

    public List<FaqQuestion> getQuestions() {
        return questions;
    }
}
