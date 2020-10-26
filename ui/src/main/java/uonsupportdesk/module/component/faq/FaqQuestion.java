package uonsupportdesk.module.component.faq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FaqQuestion {

    private final FaqTopic faqTopic;

    private final Label faqLabel;

    private final String question;

    private final List<String> keyWords = new ArrayList<>();

    private static final String PATH_TO_KEYWORDS = "keywords/keyword.json";

    public FaqQuestion(String question, FaqTopic faqTopic) {
        this.question = question;
        this.faqTopic = faqTopic;
        this.faqLabel = new Label(question);
        try {
            fetchQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private List<Question> loadKeyWordsFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(keywordsFile(), new TypeReference<>() {
        });
    }

    private void fetchQuestion() throws IOException {
        for (Question question : loadKeyWordsFromJson()) {
            if (faqTopic == FaqTopic.GENERAL && question.getTopic().equalsIgnoreCase(FaqTopic.GENERAL.name())) {
                addKeyWordsToList(question.getKeywords());
            } else if (faqTopic == FaqTopic.FINANCE && question.getTopic().equalsIgnoreCase(FaqTopic.FINANCE.name())) {
                addKeyWordsToList(question.getKeywords());
            } else if (faqTopic == FaqTopic.ACCOMMODATION && question.getTopic().equalsIgnoreCase(FaqTopic.ACCOMMODATION.name())) {
                addKeyWordsToList(question.getKeywords());
            } else if (faqTopic == FaqTopic.COURSE && question.getTopic().equalsIgnoreCase(FaqTopic.COURSE.name())) {
                addKeyWordsToList(question.getKeywords());
            } else if (faqTopic == FaqTopic.SECURITY && question.getTopic().equalsIgnoreCase(FaqTopic.SECURITY.name())) {
                addKeyWordsToList(question.getKeywords());
            } else if (faqTopic == FaqTopic.OTHER && question.getTopic().equalsIgnoreCase(FaqTopic.OTHER.name())) {
                addKeyWordsToList(question.getKeywords());
            }
        }
    }

    private void addKeyWordsToList(List<String> questions) {
        keyWords.addAll(questions);
    }

    private File keywordsFile() {
        return new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(PATH_TO_KEYWORDS)).getFile());
    }
}
