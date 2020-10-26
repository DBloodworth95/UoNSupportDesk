package service;

import com.fasterxml.jackson.databind.JsonNode;

public interface Service {
    void submit(JsonNode jsonNode);
}
