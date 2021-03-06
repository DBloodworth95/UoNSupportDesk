package uonsupportdesk.module.component.ticket;

import com.vdurmont.emoji.EmojiParser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.nio.charset.StandardCharsets;

public class MessageWidget extends VBox {

    public final MessageWidgetOrientation direction;

    private final int userId;

    private final String message;

    private TextFlow messageToDisplay;

    private SVGPath orientationIndicator;

    private static final Color SENDER_COLOUR = Color.rgb(70, 163, 221, 0.8);

    private static final Color RECEIVER_COLOUR = Color.rgb(25, 124, 163, 0.25);

    private static final Color TICKET_CLOSE_WIDGET_COLOUR = Color.rgb(255, 0, 0, 0.8);

    private static final Color TICKET_CLOSED_NOTIFICATION_COLOUR = Color.rgb(200, 200, 200);

    private static Background SENDER_BACKGROUND;

    private static Background RECEIVER_BACKGROUND;

    private static Background TICKET_CLOSE_WIDGET_BACKGROUND;

    private static Background TICKET_CLOSED_NOTIFICATION_BACKGROUND;

    public MessageWidget(int userId, String message, MessageWidgetOrientation direction) {
        this.userId = userId;
        this.message = message;
        this.direction = direction;
        loadWidgetBackgrounds();
        setupLabel();
    }

    private void loadWidgetBackgrounds() {
        SENDER_BACKGROUND = new Background(
                new BackgroundFill(SENDER_COLOUR, new CornerRadii(5, 0, 5, 5, false), Insets.EMPTY));
        RECEIVER_BACKGROUND = new Background(
                new BackgroundFill(RECEIVER_COLOUR, new CornerRadii(0, 5, 5, 5, false), Insets.EMPTY));
        TICKET_CLOSE_WIDGET_BACKGROUND = new Background(
                new BackgroundFill(TICKET_CLOSE_WIDGET_COLOUR, new CornerRadii(0, 5, 5, 5, false), Insets.EMPTY));
        TICKET_CLOSED_NOTIFICATION_BACKGROUND = new Background(
                new BackgroundFill(TICKET_CLOSED_NOTIFICATION_COLOUR, new CornerRadii(0, 5, 5, 5, false), Insets.EMPTY));
    }

    private void setupLabel() {
        this.getStylesheets().add(this.getClass().getResource("/themes/theme.css").toExternalForm());
        String prepareMessage = new String(message.getBytes(StandardCharsets.UTF_8));
        Text messageText = new Text(EmojiParser.parseToUnicode(MessageFormatter.formatMessage(prepareMessage)));
        messageToDisplay = new TextFlow();
        messageToDisplay.getStyleClass().add("message-widget-font");
        messageToDisplay.setPadding(new Insets(10));
        messageToDisplay.getChildren().add(messageText);
        orientationIndicator = new SVGPath();

        if (direction == MessageWidgetOrientation.LEFT) {
            configureReceiverMessage();
        } else if (direction == MessageWidgetOrientation.RIGHT) {
            configureSenderMessage();
        } else if (direction == MessageWidgetOrientation.CENTRE) {
            configureAsConversationNotification();
        }
    }

    public void configureAsNotification() {
        messageToDisplay.setBackground(TICKET_CLOSE_WIDGET_BACKGROUND);
        messageToDisplay.textAlignmentProperty().setValue(TextAlignment.LEFT);
        orientationIndicator.setContent("M10 0 L0 0 L0 0 Z");
        orientationIndicator.setFill(TICKET_CLOSE_WIDGET_COLOUR);

        HBox container = new HBox(messageToDisplay, orientationIndicator);
        container.maxWidthProperty().bind(widthProperty().multiply(1));

        getChildren().setAll(container);
        setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(0, 0, 0, 0));
    }

    public void configureAsConversationNotification() {
        messageToDisplay.setBackground(TICKET_CLOSED_NOTIFICATION_BACKGROUND);
        messageToDisplay.textAlignmentProperty().setValue(TextAlignment.LEFT);
        orientationIndicator.setContent("M10 0 L0 0 L0 0 Z");
        orientationIndicator.setFill(TICKET_CLOSED_NOTIFICATION_COLOUR);

        HBox container = new HBox(messageToDisplay, orientationIndicator);
        container.maxWidthProperty().bind(widthProperty().multiply(1));

        getChildren().setAll(container);
        setAlignment(Pos.BOTTOM_CENTER);
        container.setPadding(new Insets(0, 0, 0, 0));
    }

    private void configureSenderMessage() {
        messageToDisplay.setBackground(SENDER_BACKGROUND);
        messageToDisplay.textAlignmentProperty().setValue(TextAlignment.LEFT);
        orientationIndicator.setContent("M10 0 L0 10 L0 0 Z");
        orientationIndicator.setFill(SENDER_COLOUR);

        HBox container = new HBox(messageToDisplay, orientationIndicator);
        container.maxWidthProperty().bind(widthProperty().multiply(0.3));

        getChildren().setAll(container);
        setAlignment(Pos.CENTER_RIGHT);
        container.setPadding(new Insets(20, 50, 0, 0));
    }

    private void configureReceiverMessage() {
        messageToDisplay.setBackground(RECEIVER_BACKGROUND);
        messageToDisplay.textAlignmentProperty().setValue(TextAlignment.LEFT);
        orientationIndicator.setContent("M10 0 L0 0 L0 10 Z");
        orientationIndicator.setFill(RECEIVER_COLOUR);

        HBox container = new HBox(messageToDisplay, orientationIndicator);
        container.maxWidthProperty().bind(widthProperty().multiply(0.3));

        getChildren().setAll(container);
        setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(20, 0, 0, 50));
    }

    public int getUserId() {
        return userId;
    }
}
