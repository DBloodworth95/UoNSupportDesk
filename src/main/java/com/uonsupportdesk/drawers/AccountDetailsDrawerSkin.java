package com.uonsupportdesk.drawers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class AccountDetailsDrawerSkin extends SkinBase<AccountDetailsDrawer> {

    private VBox drawerBox;
    private Button button;
    private BorderPane drawerPane;
    private ImageView defaultProfileImage;
    private HBox profileImageBounds;

    private static final String CHANGE_PROFILE_PICTURE_BUTTON_TEXT = "Change profile picture";

    public AccountDetailsDrawerSkin(AccountDetailsDrawer accountDetailsDrawer) {
        super(accountDetailsDrawer);
        buildDrawer();
    }

    private void buildDrawer() {
        profileImageBounds = new HBox();
        drawerPane = new BorderPane();
        drawerBox = new VBox();
        button = new Button(CHANGE_PROFILE_PICTURE_BUTTON_TEXT);

        defaultProfileImage = loadImage();
        profileImageBounds.getChildren().add(defaultProfileImage);
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
        drawerPane.setBottom(button);
        BorderPane.setMargin(profileImageBounds, new Insets(50, 0, 0, 0));
        BorderPane.setMargin(button, new Insets(100, 12, 100, 24));
        profileImageBounds.setAlignment(Pos.CENTER);
        drawerBox.setFillWidth(true);
    }
}
