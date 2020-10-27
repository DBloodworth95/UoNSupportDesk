package service;

import account.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.ValidatedLogin;
import repository.AccountRepository;

public final class LoginService implements Service {

    private final ObjectMapper responseMapper = new ObjectMapper();

    public String validate(JsonNode requestFromClient) {
        String username = requestFromClient.get("username").asText();
        String password = requestFromClient.get("password").asText();
        Account account = AccountRepository.find(username, password);
        if (account == null) return invalidCredentialsResponse();

        String response = generateSuccessResponse(account);
        if (response == null) return invalidCredentialsResponse();

        return response;
    }

    private String generateSuccessResponse(Account account) {
        ValidatedLogin validatedLogin = new ValidatedLogin("success", account.getEmail(), account.getName(), account.getAccessLevel());
        String response = null;

        try {
            response = responseMapper.writeValueAsString(validatedLogin);
        } catch (JsonProcessingException ignored) {

        }

        return response;
    }

    private String invalidCredentialsResponse() {
        return "{\"response\":\"invalidlogin\"}";
    }
}
