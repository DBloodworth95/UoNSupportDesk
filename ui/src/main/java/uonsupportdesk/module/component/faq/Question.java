package uonsupportdesk.module.component.faq;

import java.util.List;

public final class Question {

    private String topic;

    private List<String> keywords;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
