package uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class CreateTicketFormView extends BorderPane {

    private final VBox headerContainer;

    private final AnchorPane middleContentContainer;

    private final AnchorPane bottomContentContainer;

    private final HBox middleContentContainerTopHBox;

    private final HBox middlecontentContainerBottomHBox;

    private final VBox fullNameFormContainer;

    private final VBox emailFormContainer;

    private final VBox typeFormContainer;

    private final VBox enquiryFormContainer;

    private final Label headerLabel;

    private final Label subHeaderLabel;

    private final Label fullNameLabel;

    private final Label emailLabel;

    private final Label enquiryTypeLabel;

    private final Label enquiryDescriptionLabel;

    private final Label uploadLabel;

    private final TextField fullNameTextField;

    private final TextField emailTextField;

    private final TextField enquiryDescriptionTextField;

    private final ComboBox<String> enquiryTypeOptionBox;

    private final JFXButton uploadButton;

    private final JFXButton createTicketButton;

    public CreateTicketFormView() {
        this.getStyleClass().add("new-ticket-module");

        headerContainer = new VBox();
        middleContentContainerTopHBox = new HBox();
        middlecontentContainerBottomHBox = new HBox();
        fullNameFormContainer = new VBox();
        emailFormContainer = new VBox();
        typeFormContainer = new VBox();
        enquiryFormContainer = new VBox();
        middleContentContainer = new AnchorPane();
        bottomContentContainer = new AnchorPane();
        headerLabel = new Label("Create a Ticket");
        subHeaderLabel = new Label("Fill out the form below.");
        fullNameLabel = new Label("Full Name");
        emailLabel = new Label("Email");
        enquiryTypeLabel = new Label("Type of Enquiry");
        enquiryDescriptionLabel = new Label("Enquiry Description");
        uploadLabel = new Label("Upload Image/Document");
        fullNameTextField = new TextField();
        emailTextField = new TextField();
        enquiryDescriptionTextField = new TextField();
        enquiryTypeOptionBox = new ComboBox<>();
        uploadButton = new JFXButton("Upload");
        createTicketButton = new JFXButton("Create");

        this.setPadding(new Insets(50));

        positionComponents();
        addContentToWindows();
        headerLabel.getStyleClass().add("new-ticket-header-text");
        subHeaderLabel.getStyleClass().add("new-ticket-subheader-text");
        enquiryDescriptionLabel.getStyleClass().add("new-ticket-form-text");
        uploadLabel.getStyleClass().add("new-ticket-form-text");
        enquiryTypeLabel.getStyleClass().add("new-ticket-form-text");
        emailLabel.getStyleClass().add("new-ticket-form-text");
        fullNameLabel.getStyleClass().add("new-ticket-form-text");
        headerContainer.getStyleClass().add("new-ticket-containers-top");
        middleContentContainer.getStyleClass().add("new-ticket-containers-middle");
        bottomContentContainer.getStyleClass().add("new-ticket-containers-bottom");
        fullNameTextField.getStyleClass().add("new-ticket-textfield");
        enquiryDescriptionTextField.getStyleClass().add("new-ticket-textfield");
        emailTextField.getStyleClass().add("new-ticket-textfield");
        uploadButton.getStyleClass().add("new-ticket-upload-button");
        createTicketButton.getStyleClass().add("new-ticket-create-button");
    }

    private void positionComponents() {
        headerContainer.prefHeightProperty().bind(this.heightProperty().multiply(0.2));
        middleContentContainer.prefHeightProperty().bind(this.heightProperty().multiply(0.4));
        bottomContentContainer.prefHeightProperty().bind(this.heightProperty().multiply(0.25));

        headerContainer.setAlignment(Pos.TOP_LEFT);
        headerContainer.setSpacing(10);

        fullNameFormContainer.setAlignment(Pos.TOP_LEFT);
        emailFormContainer.setAlignment(Pos.TOP_LEFT);
        typeFormContainer.setAlignment(Pos.TOP_LEFT);
        enquiryFormContainer.setAlignment(Pos.TOP_LEFT);

        fullNameFormContainer.setSpacing(10);
        emailFormContainer.setSpacing(10);
        typeFormContainer.setSpacing(10);
        enquiryFormContainer.setSpacing(10);
        middleContentContainerTopHBox.setSpacing(50);

        AnchorPane.setTopAnchor(uploadLabel, 0.0);
        AnchorPane.setLeftAnchor(uploadLabel, 0.0);
        AnchorPane.setTopAnchor(uploadButton, 50.0);
        AnchorPane.setLeftAnchor(uploadButton, 0.0);
        AnchorPane.setBottomAnchor(createTicketButton, 50.0);
        AnchorPane.setRightAnchor(createTicketButton, 100.0);
        AnchorPane.setTopAnchor(middleContentContainerTopHBox, 20.0);
        AnchorPane.setLeftAnchor(middleContentContainerTopHBox, 0.0);
        AnchorPane.setBottomAnchor(middlecontentContainerBottomHBox, 50.0);
        AnchorPane.setLeftAnchor(middlecontentContainerBottomHBox, 0.0);

        headerContainer.setPadding(new Insets(50, 0, 0, 40));
        middleContentContainer.setPadding(new Insets(0, 0, 10, 0));
        bottomContentContainer.setPadding(new Insets(20, 50, 0, 50));
        uploadLabel.setPadding(new Insets(0, 0, 40, 0));

        uploadButton.setMinWidth(200.00);
        createTicketButton.setMinWidth(200.00);
        enquiryFormContainer.setMinWidth(250.00);
        enquiryDescriptionTextField.setMinWidth(200.00);

        VBox.setMargin(createTicketButton, new Insets(100, 0, 40, 600));
    }

    private void addContentToWindows() {
        setTop(headerContainer);
        setCenter(middleContentContainer);
        setBottom(bottomContentContainer);

        headerContainer.getChildren().addAll(headerLabel, subHeaderLabel);

        fullNameFormContainer.getChildren().addAll(fullNameLabel, fullNameTextField);
        emailFormContainer.getChildren().addAll(emailLabel, emailTextField);
        typeFormContainer.getChildren().addAll(enquiryTypeLabel, enquiryTypeOptionBox);
        enquiryFormContainer.getChildren().addAll(enquiryDescriptionLabel, enquiryDescriptionTextField);
        middleContentContainer.getChildren().addAll(middleContentContainerTopHBox, middlecontentContainerBottomHBox);
        middleContentContainerTopHBox.getChildren().addAll(fullNameFormContainer, emailFormContainer, typeFormContainer);
        middlecontentContainerBottomHBox.getChildren().addAll(enquiryFormContainer);

        bottomContentContainer.getChildren().addAll(uploadLabel, uploadButton, createTicketButton);

        enquiryTypeOptionBox.getItems().addAll("Option 1", "Option 2", "Option 3");
        enquiryTypeOptionBox.getSelectionModel().select(0);
    }

    public JFXButton getCreateTicketButton() {
        return createTicketButton;
    }
}
