package uonsupportdesk.module.component.note;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicketNoteWidget extends Pane {

    private static final int WIDGET_WIDTH = 400;

    private static final int WIDGET_HEIGHT = 400;

    private final TextArea noteContent;

    private final VBox noteBodyContainer;

    private final TicketNote ticketNote;

    public TicketNoteWidget(TicketNote ticketNote) {
        this.ticketNote = ticketNote;
        this.prefWidthProperty().set(WIDGET_WIDTH);
        this.prefHeightProperty().set(WIDGET_HEIGHT);

        noteContent = new TextArea(ticketNote.getBody());
        noteBodyContainer = new VBox(noteContent);
    }

    private void positionComponents() {
        noteBodyContainer.prefWidthProperty().set(WIDGET_WIDTH);
        noteBodyContainer.prefHeightProperty().set(WIDGET_HEIGHT);
        noteContent.prefHeightProperty().set(WIDGET_HEIGHT);
        noteContent.prefWidthProperty().set(WIDGET_WIDTH);

        noteContent.setEditable(true);
        this.getChildren().add(noteBodyContainer);
    }

    public void open() {
        positionComponents();

        Scene scene = new Scene(this);
        Stage stage = new Stage();
        stage.setTitle("Notes for Ticket: " + ticketNote.getTicketId());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
