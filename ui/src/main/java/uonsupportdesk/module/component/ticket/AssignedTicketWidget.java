package uonsupportdesk.module.component.ticket;

import com.jfoenix.transitions.JFXFillTransition;
import javafx.animation.Animation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import uonsupportdesk.session.Session;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Objects;

public class AssignedTicketWidget extends VBox {

    private static final int GRAY_RGB_CODE = 211;

    private static final int WHITE_RGB_CODE = 255;

    private static final CornerRadii CURVED = new CornerRadii(5);

    private final Background widgetBackground;

    private final Background hoveredBackground;

    private final Background notificationBackground;

    private final int id;

    private final int participantId;

    private final String username;

    private final String issue;

    private final String ticketType;

    private final VBox profileImageBounds;

    private final Session session;

    private final JFXFillTransition widgetAnimation;

    private boolean isArchived;

    private Circle profileImageThumbnail;

    public AssignedTicketWidget(int id, int participantId, String username, String issue, String ticketType, byte[] profileImageAsBytes, Session session) {
        this.id = id;
        this.participantId = participantId;
        this.username = username;
        this.issue = issue;
        this.ticketType = ticketType;
        this.session = session;
        this.isArchived = false;
        this.getStylesheets().add(this.getClass().getResource("/themes/theme.css").toExternalForm());
        this.getStyleClass().add("assigned-ticket-widget");

        BackgroundFill widgetBackgroundFill = new BackgroundFill(Color.rgb(WHITE_RGB_CODE, WHITE_RGB_CODE, WHITE_RGB_CODE), CURVED, Insets.EMPTY);
        BackgroundFill hoveredBackgroundFill = new BackgroundFill(Color.rgb(GRAY_RGB_CODE, GRAY_RGB_CODE, GRAY_RGB_CODE), CURVED, Insets.EMPTY);
        BackgroundFill notificationFill = new BackgroundFill(Color.rgb(134, 184, 222), CURVED, Insets.EMPTY);
        widgetBackground = new Background(widgetBackgroundFill);
        hoveredBackground = new Background(hoveredBackgroundFill);
        notificationBackground = new Background(notificationFill);
        widgetAnimation = new JFXFillTransition(Duration.seconds(1));
        widgetAnimation.setRegion(this);
        widgetAnimation.setFromValue(Color.WHITE);
        widgetAnimation.setToValue(Color.rgb(134, 184, 222));
        widgetAnimation.setAutoReverse(true);
        widgetAnimation.setCycleCount(Animation.INDEFINITE);
        profileImageBounds = new VBox();
        profileImageThumbnail = new Circle(40, 40, 25);


        highlightOnHover();
        positionComponents();
        renderProfileThumbnail(profileImageThumbnail, profileImageAsBytes);
    }

    private void renderProfileThumbnail(Circle profileImageThumbnail, byte[] profileImageAsBytes) {
        Image profileImage;

        if (this.participantId == 0 || Arrays.equals(profileImageAsBytes, session.getProfilePicture())) {
            profileImage = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("icons/account-circle.png")));
        } else {
            profileImage = new Image(new ByteArrayInputStream(profileImageAsBytes));
        }

        profileImageThumbnail.setFill(new ImagePattern(profileImage));
        profileImageBounds.getChildren().add(profileImageThumbnail);
    }

    public void updateProfileThumbnail(byte[] profileImageAsBytes) {
        Image profileImage = new Image(new ByteArrayInputStream(profileImageAsBytes));
        profileImageThumbnail.setFill(new ImagePattern(profileImage));
    }

    private void highlightOnHover() {
        this.setOnMouseEntered(e -> this.setBackground(hoveredBackground));
        this.setOnMouseExited(e -> this.setBackground(widgetBackground));
    }

    private void positionComponents() {
        Label usernameLabel = new Label(username);
        Label issueCategoryLabel = new Label(issue);

        this.getChildren().addAll(usernameLabel, profileImageBounds, issueCategoryLabel);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.setSpacing(10);
        this.setPadding(new Insets(5, 0, 10, 0));


        profileImageBounds.setAlignment(Pos.BASELINE_LEFT);
    }

    public int getTicketId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getIssue() {
        return issue;
    }

    public String getTicketType() {
        return ticketType;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void archive() {
        this.isArchived = true;
    }

    public void turnOnNotificationMode() {
        if (!isNotificationPlaying()) {
            this.setBackground(notificationBackground);
            widgetAnimation.play();
        }
    }

    public void turnOffNotificationMode() {
        if (isNotificationPlaying()) {
            widgetAnimation.stop();
            this.setBackground(widgetBackground);
        }
    }

    public boolean isNotificationPlaying() {
        return widgetAnimation.getStatus().equals(Animation.Status.RUNNING);
    }
}
