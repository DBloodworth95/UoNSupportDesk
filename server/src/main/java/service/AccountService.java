package service;

import account.ProfilePicture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import command.PictureUploadRequestAccepted;
import repository.AccountRepository;

import java.io.IOException;

public class AccountService implements Service {

    private final ObjectMapper responseMapper = new ObjectMapper();

    public String uploadImage(JsonNode requestFromClient) {
        ProfilePicture profilePicture = null;
        try {
            int userId = requestFromClient.get("userId").asInt();
            byte[] imageAsBytes = requestFromClient.get("imageAsStream").binaryValue();

            profilePicture = AccountRepository.submitProfilePicture(userId, imageAsBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (profilePicture == null) return invalidImageUploadResponse();

        String response = generateSuccessImageUploadResponse(profilePicture);
        if (response == null) return invalidImageUploadResponse();

        return response;
    }

    private String generateSuccessImageUploadResponse(ProfilePicture profilePicture) {
        PictureUploadRequestAccepted pictureUploadRequestAccepted = new PictureUploadRequestAccepted(profilePicture.getPicture());
        String response = null;

        try {
            response = responseMapper.writeValueAsString(pictureUploadRequestAccepted);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String invalidImageUploadResponse() {
        return "uploadimagefailed";
    }

}
