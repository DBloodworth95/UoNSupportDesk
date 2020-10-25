package uonsupportdesk.drawer;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class AccountDetailsDrawerSkin extends SkinBase<AccountDetailsDrawer> {

    private VBox drawerBox;
    private Label staffNameLabel;
    private Label staffEmailLabel;
    private JFXButton changePictureButton;
    private BorderPane drawerPane;
    private ImageView defaultProfileImage;
    private VBox profileImageBounds;

    private static final String CHANGE_PROFILE_PICTURE_BUTTON_TEXT = "Change profile picture";

    public AccountDetailsDrawerSkin(AccountDetailsDrawer accountDetailsDrawer) {
        super(accountDetailsDrawer);
        buildDrawer();
    }

    private void buildDrawer() {
        profileImageBounds = new VBox();
        drawerPane = new BorderPane();
        drawerBox = new VBox();
        staffEmailLabel = new Label("Email");
        staffNameLabel = new Label("Name");
        changePictureButton = new JFXButton(CHANGE_PROFILE_PICTURE_BUTTON_TEXT);

        changePictureButton.getStyleClass().add("change-profile-button");
        defaultProfileImage = loadImage();
        profileImageBounds.getChildren().addAll(defaultProfileImage, staffNameLabel, staffEmailLabel);
        drawerBox.getChildren().addAll(drawerPane, profileImageBounds);
        getChildren().add(drawerBox);

        positionComponents();
    }

    private ImageView loadImage() {
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("icons/account-circle.png")));
        return new ImageView(image);
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
}
