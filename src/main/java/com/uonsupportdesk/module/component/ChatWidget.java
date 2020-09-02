package com.uonsupportdesk.module.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Objects;

public class ChatWidget extends VBox {

    private static final int GRAY_RGB_CODE = 211;

    private static final int WHITE_RGB_CODE = 255;

    private final Background widgetBackground;

    private final Background hoveredBackground;

    private final int id;

    private final String username;

    private final String issue;

    private final ImageView profileImage;

    private final VBox profileImageBounds;

    public ChatWidget(int id, String username, String issue, String profileImageSource) {
        this.id = id;
        this.username = username;
        this.issue = issue;
        this.profileImage = loadImage(profileImageSource);

        BackgroundFill widgetBackgroundFill = new BackgroundFill(Color.rgb(WHITE_RGB_CODE, WHITE_RGB_CODE, WHITE_RGB_CODE), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill hoveredBackgroundFill = new BackgroundFill(Color.rgb(GRAY_RGB_CODE, GRAY_RGB_CODE, GRAY_RGB_CODE), CornerRadii.EMPTY, Insets.EMPTY);
        widgetBackground = new Background(widgetBackgroundFill);
        hoveredBackground = new Background(hoveredBackgroundFill);
        profileImageBounds = new VBox();

        highlightOnHover();
        positionComponents();
    }

    private void highlightOnHover() {
        this.setOnMouseEntered(e -> {
            this.setBackground(hoveredBackground);
        });
        this.setOnMouseExited(e -> {
            this.setBackground(widgetBackground);
        });
    }

    private void positionComponents() {
        Label usernameLabel = new Label(username);
        Label issueCategoryLabel = new Label(issue);

        this.getChildren().addAll(usernameLabel, profileImageBounds, issueCategoryLabel);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setSpacing(10);
        this.setPadding(new Insets(5, 0, 10, 0));

        profileImageBounds.getChildren().add(profileImage);
        profileImageBounds.setAlignment(Pos.BASELINE_LEFT);
    }

    private ImageView loadImage(String imageSource) {
        Image image = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(imageSource)));
        return new ImageView(image);
    }

    public int getUserId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getIssue() {
        return issue;
    }
}
