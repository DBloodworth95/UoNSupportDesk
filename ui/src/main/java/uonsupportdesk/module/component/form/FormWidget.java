package uonsupportdesk.module.component.form;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;

public class FormWidget extends VBox {

    private final Label titleLabel;

    private final VBox fileIconContainer;

    private final Image fileIconImage;

    private final ImageView fileIconImageView;

    public FormWidget(String title) {
        this.setSpacing(10);

        titleLabel = new Label(title);
        fileIconImage = new Image(getClass().getResourceAsStream("/icons/FormIcon.png"));
        fileIconImageView = new ImageView(fileIconImage);
        fileIconContainer = new VBox(fileIconImageView);

        fileIconImageView.setFitWidth(50);
        fileIconImageView.setFitHeight(50);

        titleLabel.getStyleClass().add("form-widget-header");

        positionComponents();
        addContentToWindow();
    }

    private void positionComponents() {
        this.setPadding(new Insets(20, 20, 20, 20));
        titleLabel.setPadding(new Insets(0, 0, 10, 0));
        fileIconContainer.setAlignment(Pos.CENTER_LEFT);
    }

    private void addContentToWindow() {
        getChildren().addAll(titleLabel, fileIconContainer);
    }

    public void addDownloadLinks(List<FormURL> downloadLinks) {
        for (FormURL formURL : downloadLinks) {
            getChildren().add(formURL);
        }
    }
}
