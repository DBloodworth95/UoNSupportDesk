package uonsupportdesk.module.component.ticket;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import uonsupportdesk.session.Session;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Objects;

public class AssignedTicketWidget extends VBox {

    private static final int GRAY_RGB_CODE = 211;

    private static final int WHITE_RGB_CODE = 255;

    private final Background widgetBackground;

    private final Background hoveredBackground;

    private final int id;

    private final String username;

    private final String issue;

    private final String ticketType;

    private final VBox profileImageBounds;

    private final Session session;

    private boolean isArchived;

    public AssignedTicketWidget(int id, String username, String issue, String ticketType, byte[] profileImageAsBytes, Session session) {
        this.id = id;
        this.username = username;
        this.issue = issue;
        this.ticketType = ticketType;
        this.session = session;
        this.isArchived = false;

        BackgroundFill widgetBackgroundFill = new BackgroundFill(Color.rgb(WHITE_RGB_CODE, WHITE_RGB_CODE, WHITE_RGB_CODE), CornerRadii.EMPTY, Insets.EMPTY);
        BackgroundFill hoveredBackgroundFill = new BackgroundFill(Color.rgb(GRAY_RGB_CODE, GRAY_RGB_CODE, GRAY_RGB_CODE), CornerRadii.EMPTY, Insets.EMPTY);
        widgetBackground = new Background(widgetBackgroundFill);
        hoveredBackground = new Background(hoveredBackgroundFill);
        profileImageBounds = new VBox();
        Circle profileImageThumbnail = new Circle(40, 40, 25);

        highlightOnHover();
        positionComponents();
        renderProfileThumbnail(profileImageThumbnail, profileImageAsBytes);
    }

    private void renderProfileThumbnail(Circle profileImageThumbnail, byte[] profileImageAsBytes) {
        Image profileImage;

        if (profileImageAsBytes == null || Arrays.equals(profileImageAsBytes, session.getProfilePicture())) {
            profileImage = new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("icons/account-circle.png")));
        } else {
            profileImage = new Image(new ByteArrayInputStream(profileImageAsBytes));
        }

        profileImageThumbnail.setFill(new ImagePattern(profileImage));
        profileImageBounds.getChildren().add(profileImageThumbnail);
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
}
