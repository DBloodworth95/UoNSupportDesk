package uonsupportdesk.drawer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.command.UploadProfilePictureRequest;
import uonsupportdesk.session.Session;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class AccountDetailsDrawerSkin extends SkinBase<AccountDetailsDrawer> {

    private VBox drawerBox;

    private Label staffNameLabel;

    private Label staffEmailLabel;

    private JFXButton changePictureButton;

    private BorderPane drawerPane;

    private ImageView defaultProfileImage;

    private VBox profileImageBounds;

    private final Session session;

    private final ClientBootstrap clientBootstrap;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private static final String CHANGE_PROFILE_PICTURE_BUTTON_TEXT = "Change profile picture";

    public AccountDetailsDrawerSkin(AccountDetailsDrawer accountDetailsDrawer, Session session, ClientBootstrap clientBootstrap) {
        super(accountDetailsDrawer);
        this.session = session;
        this.clientBootstrap = clientBootstrap;
        buildDrawer();
    }

    private void buildDrawer() {
        profileImageBounds = new VBox();
        drawerPane = new BorderPane();
        drawerBox = new VBox();
        staffEmailLabel = new Label("Email: " + session.getEmail());
        staffNameLabel = new Label("Name: " + session.getName());
        changePictureButton = new JFXButton(CHANGE_PROFILE_PICTURE_BUTTON_TEXT);

        changePictureButton.getStyleClass().add("change-profile-button");
        defaultProfileImage = new ImageView(loadImage(null));
        profileImageBounds.getChildren().addAll(defaultProfileImage, staffNameLabel, staffEmailLabel);
        drawerBox.getChildren().addAll(drawerPane, profileImageBounds);
        getChildren().add(drawerBox);

        positionComponents();
        attachListeners();
    }

    private Image loadImage(byte[] imageAsBytes) {
        Image image;

        if (imageAsBytes == null) {
            image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("icons/account-circle.png")));
        } else {
            image = new Image(new ByteArrayInputStream(imageAsBytes));
        }

        return image;
    }

    private void positionComponents() {
        drawerPane.setCenter(profileImageBounds);
        drawerPane.setBottom(changePictureButton);

        BorderPane.setMargin(profileImageBounds, new Insets(25, 0, 0, 0));
        BorderPane.setMargin(changePictureButton, new Insets(50, 12, 50, 24));
        staffNameLabel.setPadding(new Insets(250, 0, 50, 0));
        profileImageBounds.setAlignment(Pos.CENTER);

        drawerBox.setFillWidth(true);
    }

    private void attachListeners() {
        changePictureButton.setOnAction(onClick -> changeProfilePicture());
    }

    private void changeProfilePicture() {
        FileChooser profilePictureChooser = new FileChooser();
        File profilePictureImage = profilePictureChooser.showOpenDialog(this.drawerPane.getScene().getWindow());
        String newProfilePicturePath = profilePictureImage.getAbsolutePath();

        profilePictureChooser.setTitle("Choose a photo to upload!");
        try {
            byte[] fileAsBytes = Files.readAllBytes(Paths.get(newProfilePicturePath));

            UploadProfilePictureRequest uploadProfilePictureRequest = new UploadProfilePictureRequest(session.getSessionId(), fileAsBytes);
            processProfilePictureChangeRequest(uploadProfilePictureRequest, fileAsBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processProfilePictureChangeRequest(UploadProfilePictureRequest uploadProfilePictureRequest, byte[] fileAsBytes) {
        try {
            String requestAsString = jsonMapper.writeValueAsString(uploadProfilePictureRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
            System.out.println(requestAsString);

            defaultProfileImage.setImage(loadImage(fileAsBytes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
