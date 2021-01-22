package uonsupportdesk.module.component.ticket;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.*;
import javafx.util.Duration;


public class ClosedTicketNotificationWidget extends VBox {

    private final int ticketId;

    public ClosedTicketNotificationWidget(int ticketId) {
        this.ticketId = ticketId;
    }

    public void showNotification() {
        MessageWidget messageWidget = new MessageWidget(0, "Ticket " + ticketId + " Closed!", MessageWidgetOrientation.LEFT);
        messageWidget.configureAsNotification();
        this.getChildren().add(messageWidget);
        TranslateTransition openTransition = new TranslateTransition(new Duration(1000), messageWidget);
        openTransition.setToX(0);
        TranslateTransition closeTransition = new TranslateTransition(new Duration(1000), messageWidget);
        if (this.getTranslateX() != 0) {
            openTransition.play();
        }
        this.setOnMouseClicked(e -> {
            closeTransition.setToX(-(this.getWidth()));
            closeTransition.play();
            closeTransition.setOnFinished(finishWith -> this.getChildren().remove(messageWidget));
        });

    }
}
