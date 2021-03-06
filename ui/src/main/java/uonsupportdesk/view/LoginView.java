package uonsupportdesk.view;

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
import javafx.scene.paint.Color;

public final class LoginView extends AnchorPane {

    private final VBox leftSideContainer;

    private final VBox rightSideContainer;

    private final Image universityLogoImage;

    private final Image universityLogoImageRightSide;

    private final ImageView universityLogoImageViewRightSide;

    private final ImageView universityLogoImageView;

    private final Label welcomeLabel;

    private final Label uonSupportLabel;

    private final Label welcomeBackLabel;

    private final Label signInLabel;

    private final Label emailLabel;

    private final Label invalidAttemptLabel;

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
        welcomeBackLabel = new Label("Welcome back,");
        signInLabel = new Label("sign in to continue.");
        welcomeLabel = new Label("Welcome to");
        uonSupportLabel = new Label("UoNSupport");
        emailLabel = new Label("Email:");
        passwordLabel = new Label("Password:");
        forgottenPasswordLabel = new Label("Forgotten password?");
        invalidAttemptLabel = new Label();
        loginButton = new JFXButton("Login");
        emailTextField = new TextField();
        passwordField = new PasswordField();
        universityLogoImage = new Image(getClass().getResourceAsStream("/icons/uon_logo.png"));
        universityLogoImageView = new ImageView(universityLogoImage);
        universityLogoImageRightSide = new Image(getClass().getResourceAsStream("/icons/uon_logo_full.png"));
        universityLogoImageViewRightSide = new ImageView(universityLogoImageRightSide);

        universityLogoImageView.setFitWidth(LOGO_WIDTH_LEFT_SIDE);
        universityLogoImageView.setFitHeight(LOGO_HEIGHT_LEFT_SIDE);

        emailTextField.setMaxWidth(350);
        passwordField.setMaxWidth(350);

        this.getStylesheets().add("/themes/theme.css");
        rightSideContainer.getStyleClass().add("login-right-container");
        leftSideContainer.getStyleClass().add("login-left-container");
        welcomeLabel.getStyleClass().add("login-left-container-text-welcome");
        uonSupportLabel.getStyleClass().add("login-left-container-text-welcome-uonsupport");
        emailTextField.getStyleClass().add("login-textfield");
        passwordField.getStyleClass().add("login-textfield");
        loginButton.getStyleClass().add("login-button");
        welcomeBackLabel.getStyleClass().add("login-right-container-text-welcome");
        forgottenPasswordLabel.getStyleClass().add("login-right-container-forgot-password");
        invalidAttemptLabel.setTextFill(Color.RED);
        invalidAttemptLabel.setVisible(false);

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
        rightSideContainer.setAlignment(Pos.BASELINE_CENTER);
        rightSideContainer.setSpacing(35.00);

        rightSideContainer.setPadding(new Insets(50, 0, 0, 0));
        emailLabel.setPadding(new Insets(0, 330, 25, 0));
        passwordLabel.setPadding(new Insets(0, 300, 25, 0));
        forgottenPasswordLabel.setPadding(new Insets(0, 0, 0, 250));
        loginButton.setPadding(new Insets(10, 100, 10, 100));
    }

    private void addContentToWindows() {
        getChildren().addAll(leftSideContainer, rightSideContainer);
        leftSideContainer.getChildren().addAll(universityLogoImageView, welcomeLabel, uonSupportLabel);
        rightSideContainer.getChildren().addAll(universityLogoImageViewRightSide, welcomeBackLabel, signInLabel, emailLabel, emailTextField, passwordLabel, passwordField, invalidAttemptLabel, forgottenPasswordLabel, loginButton);
    }

    public JFXButton getLoginButton() {
        return loginButton;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Label getInvalidAttemptLabel() {
        return invalidAttemptLabel;
    }
}
