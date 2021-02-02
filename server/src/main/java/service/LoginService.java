package service;

import account.AccessLevel;
import account.Account;
import client.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.LoginRequestAccepted;
import io.netty.channel.Channel;
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
        LoginRequestAccepted loginRequestAccepted = new LoginRequestAccepted("success", account.getUserId(), account.getEmail(), account.getName(),
                account.getAccessLevel(), account.getProfilePicture());
        String response = null;

        try {
            response = responseMapper.writeValueAsString(loginRequestAccepted);
        } catch (JsonProcessingException ignored) {

        }

        return response;
    }

    private String invalidCredentialsResponse() {
        return "{\"response\":\"invalidlogin\"}";
    }

    public User generateUser(String responseToCheck, Channel userChannel) {
        JsonNode jsonNode;
        User user = null;

        try {
            jsonNode = responseMapper.readTree(responseToCheck);
            String response = jsonNode.get("response").asText();
            int userId = jsonNode.get("userId").asInt();
            AccessLevel accessLevel;

            if (jsonNode.get("accessLevel").asText().equalsIgnoreCase("user")) {
                accessLevel = AccessLevel.USER;
            } else {
                accessLevel = AccessLevel.SUPPORT_TEAM;
            }

            if (response.equalsIgnoreCase("success")) {
                user = new User(userChannel, userId, accessLevel);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }
}
