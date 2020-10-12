package com.uonsupportdesk.module.component.faq;

import javafx.scene.control.Label;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FaqQuestion {

    private final FaqTopic faqTopic;

    private final Label faqLabel;

    private final String question;

    private final List<String> keyWords = new ArrayList<>();

    public FaqQuestion(String question, FaqTopic faqTopic) {
        this.question = question;
        this.faqTopic = faqTopic;
        this.faqLabel = new Label(question);
        loadKeyWordsFromJson();
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

    private void loadKeyWordsFromJson() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader jsonFileReader = new FileReader(new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource("keywords/keyword.json")).getFile()))) {
            Object topicsToReadInJson = jsonParser.parse(jsonFileReader);
            JSONArray topicsReadFromJson = (JSONArray) topicsToReadInJson;

            topicsReadFromJson.forEach(topic -> parseTopic((JSONObject) topic));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private void parseTopic(JSONObject topic) {
        JSONObject topicToParse = (JSONObject) topic.get("topic");
        String topicName = (String) topicToParse.get("name");

        if (faqTopic == FaqTopic.GENERAL && topicName.equalsIgnoreCase(FaqTopic.GENERAL.name())) {
            addKeyWordsToList(topicToParse);
        } else if (faqTopic == FaqTopic.FINANCE && topicName.equalsIgnoreCase(FaqTopic.FINANCE.name())) {
            addKeyWordsToList(topicToParse);
        } else if (faqTopic == FaqTopic.ACCOMMODATION && topicName.equalsIgnoreCase(FaqTopic.ACCOMMODATION.name())) {
            addKeyWordsToList(topicToParse);
        } else if (faqTopic == FaqTopic.COURSE && topicName.equalsIgnoreCase(FaqTopic.COURSE.name())) {
            addKeyWordsToList(topicToParse);
        } else if (faqTopic == FaqTopic.SECURITY && topicName.equalsIgnoreCase(FaqTopic.SECURITY.name())) {
            addKeyWordsToList(topicToParse);
        } else if (faqTopic == FaqTopic.OTHER && topicName.equalsIgnoreCase(FaqTopic.OTHER.name())) {
            addKeyWordsToList(topicToParse);
        }
    }

    private void addKeyWordsToList(JSONObject topic) {
        JSONArray topicKeyWords = (JSONArray) topic.get("keywords");
        for (Object topicKeyWord : topicKeyWords) {
            System.out.println(topicKeyWord.toString());
            keyWords.add(topicKeyWord.toString());
        }
    }
}
