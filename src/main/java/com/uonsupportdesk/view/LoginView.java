package com.uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class LoginView extends AnchorPane {

    private final VBox leftSideContainer;

    private final VBox rightSideContainer;

    private final Image universityLogoImage;

    private final ImageView universityLogoImageView;

    private final Label welcomeLabel;

    private final Label uonSupportLabel;

    private final Label welcomeBackLabel;

    private final Label emailLabel;

    private final Label passwordLabel;

    private final TextField emailTextField;

    private final PasswordField passwordField;

    private final Label forgottenPasswordLabel;

    private final JFXButton loginButton;

    private static final int LOGO_WIDTH_LEFT_SIDE = 200;

    private static final int LOGO_HEIGHT_LEFT_SIDE = 200;

    public LoginView() {
        leftSideContainer = new VBox();
        rightSideContainer = new VBox();
        welcomeBackLabel = new Label("Welcome back, sign in to continue.");
        welcomeLabel = new Label("Welcome to");
        uonSupportLabel = new Label("UoNSupport");
        emailLabel = new Label("Email:");
        passwordLabel = new Label("Password:");
        forgottenPasswordLabel = new Label("Forgotten password?");
        loginButton = new JFXButton("Login");
        emailTextField = new TextField();
        passwordField = new PasswordField();
        universityLogoImage = new Image(getClass().getResourceAsStream("/icons/uon_logo.png"));
        universityLogoImageView = new ImageView(universityLogoImage);
        universityLogoImageView.setFitWidth(LOGO_WIDTH_LEFT_SIDE);
        universityLogoImageView.setFitHeight(LOGO_HEIGHT_LEFT_SIDE);

        this.getStylesheets().add("/themes/theme.css");
        rightSideContainer.getStyleClass().add("login-right-container");
        leftSideContainer.getStyleClass().add("login-left-container");
        welcomeLabel.getStyleClass().add("login-left-container-text-welcome");
        uonSupportLabel.getStyleClass().add("login-left-container-text-welcome-uonsupport");

        positionComponents();
        addContentToWindows();
    }

    private void positionComponents() {
        leftSideContainer.prefHeightProperty().bind(this.heightProperty());
        leftSideContainer.prefWidthProperty().bind(this.widthProperty().divide(2));
        rightSideContainer.prefHeightProperty().bind(this.heightProperty());
        rightSideContainer.prefWidthProperty().bind(this.widthProperty().divide(2));

        setLeftAnchor(leftSideContainer, 0.0);
        setTopAnchor(leftSideContainer, 0.0);
        setRightAnchor(rightSideContainer, 0.0);
        setTopAnchor(rightSideContainer, 0.0);

        leftSideContainer.setAlignment(Pos.CENTER);
        rightSideContainer.setAlignment(Pos.CENTER);

        welcomeLabel.setPadding(new Insets(50, 0, 0, 0));
    }

    private void addContentToWindows() {
        getChildren().addAll(leftSideContainer, rightSideContainer);
        leftSideContainer.getChildren().addAll(universityLogoImageView, welcomeLabel, uonSupportLabel);
    }

    private void attachListeners() {

    }
}
