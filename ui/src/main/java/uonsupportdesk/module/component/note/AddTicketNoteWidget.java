package uonsupportdesk.module.component.note;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddTicketNoteWidget extends BorderPane {
    private static final int WIDGET_WIDTH = 600;

    private static final int WIDGET_HEIGHT = 100;

    private final VBox componentContainer;

    private final TextField noteTextfield;

    private final JFXButton addNoteButton;

    public AddTicketNoteWidget() {
        this.getStylesheets().add("/themes/theme.css");
        this.prefWidthProperty().set(WIDGET_WIDTH);
        this.prefHeightProperty().set(WIDGET_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        componentContainer = new VBox();
        componentContainer.prefWidthProperty().set(200);

        noteTextfield = new TextField();
        addNoteButton = new JFXButton("Add Note");
        addNoteButton.getStyleClass().add("assigned-ticket-buttons");
    }

    private void positionComponents() {
        componentContainer.setPadding(new Insets(10, 10, 10, 10));
        componentContainer.setFillWidth(true);
        componentContainer.getChildren().addAll(noteTextfield, addNoteButton);
        componentContainer.setSpacing(25);
        componentContainer.setAlignment(Pos.CENTER);

        noteTextfield.prefWidthProperty().bind(componentContainer.widthProperty());

        this.setCenter(componentContainer);
    }

    public void open() {
        positionComponents();

        Scene scene = new Scene(this);
        Stage stage = new Stage();
        stage.setTitle("Add a Note");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public JFXButton getAddNoteButton() {
        return addNoteButton;
    }

    public String getTicketNoteBody() {
        return noteTextfield.getText() + "\n";
    }
}
