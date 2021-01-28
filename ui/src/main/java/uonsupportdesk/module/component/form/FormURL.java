package uonsupportdesk.module.component.form;

import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FormURL extends Hyperlink {

    private final String formName;

    private final String fileURL;

    public FormURL(String formName, String fileURL) {
        this.formName = formName;
        this.fileURL = fileURL;

        styleLabel();
        attachListeners();
    }

    private void styleLabel() {
        this.getStyleClass().add("form-widget-style");
        this.setText(formName);
    }

    private void attachListeners() {
        this.setOnMouseClicked(e -> saveFormLocally());
    }

    private void saveFormLocally() {
        File documentFile = Paths.get(ClassLoader.getSystemResource(fileURL).toString()).toFile();
        FileChooser fileSaver = new FileChooser();
        fileSaver.setTitle("Save Document");
        File destination = fileSaver.showSaveDialog(this.getScene().getWindow());
        if (destination != null) {
            try {
                Files.copy(documentFile.toPath(), destination.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
