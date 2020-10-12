package com.uonsupportdesk.module.component.faq;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class FaqQuestion {

    private final Label faqLabel;

    private final String question;

    private final List<String> keyWords = new ArrayList<>();

    public FaqQuestion(String question) {
        this.question = question;
        this.faqLabel = new Label(question);
    }

    public String asString() {
        return question;
    }

    public Label asLabel() {
        return faqLabel;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }
}
