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
        String username = requestFromClient.get("username").asText(); //Retrieve the "Username" value from the login request "Username" Key.
        String password = requestFromClient.get("password").asText(); //Retrieve the "Password" value from the login request "Password" Key.
        Account account = AccountRepository.find(username, password); //Retrieve the Account data from the Users Database.
        if (account == null)
            return invalidCredentialsResponse(); //If no account can be found, build an "Invalid Login" Response

        String response = generateSuccessResponse(account); //Attempt to build a "Login Success" response.
        if (response == null)
            return invalidCredentialsResponse(); //If the response build fails, build an "Invalid Login" Response.

        return response; //Return the response.
    }

    private String generateSuccessResponse(Account account) {
        LoginRequestAccepted loginRequestAccepted = new LoginRequestAccepted("success", account.getUserId(), account.getEmail(), account.getName(),
                account.getAccessLevel(), account.getProfilePicture()); //Creates a "Login Success" Response from the Account details.
        String response = null;

        try {
            response = responseMapper.writeValueAsString(loginRequestAccepted); //Serialise the response.
        } catch (JsonProcessingException ignored) {

        }

        return response; //Return the response to validate();
    }

    private String invalidCredentialsResponse() {
        return "{\"response\":\"invalidlogin\"}"; //Return an "Invalid Login" Response to validate();
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
