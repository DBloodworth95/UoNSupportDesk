package com.uonsupportdesk.view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class CreateTicketFormView extends BorderPane {

    private final VBox headerContainer;

    private final GridPane middleContentContainer;

    private final VBox bottomContentContainer;

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
        middleContentContainer = new GridPane();
        bottomContentContainer = new VBox();
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
        middleContentContainer.setHgap(175);
        middleContentContainer.setVgap(10);
        middleContentContainer.setAlignment(Pos.CENTER_LEFT);

        GridPane.setColumnSpan(middleContentContainer, 3);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        col1.setHalignment(HPos.LEFT);
        col2.setHalignment(HPos.LEFT);
        col3.setHalignment(HPos.LEFT);
        col4.setHalignment(HPos.LEFT);
        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        RowConstraints r3 = new RowConstraints();
        RowConstraints r4 = new RowConstraints();
        r1.setValignment(VPos.CENTER);
        r2.setValignment(VPos.CENTER);
        r3.setValignment(VPos.CENTER);
        r4.setValignment(VPos.CENTER);
        middleContentContainer.getColumnConstraints().addAll(col1, col2, col3, col4);
        middleContentContainer.getRowConstraints().addAll(r1, r2, r3, r4);

        headerContainer.setPadding(new Insets(50, 0, 0, 40));
        middleContentContainer.setPadding(new Insets(0, 0, 10, 0));
        bottomContentContainer.setPadding(new Insets(20, 50, 0, 50));
        enquiryDescriptionLabel.setPadding(new Insets(50, 0, 0, 0));
        uploadLabel.setPadding(new Insets(0, 0, 40, 0));

        uploadButton.setMinWidth(200.00);
        createTicketButton.setMinWidth(200.00);

        VBox.setMargin(createTicketButton, new Insets(100, 0, 40, 600));
    }

    private void addContentToWindows() {
        setTop(headerContainer);
        setCenter(middleContentContainer);
        setBottom(bottomContentContainer);

        headerContainer.getChildren().addAll(headerLabel, subHeaderLabel);

        middleContentContainer.add(fullNameLabel, 0, 0);
        middleContentContainer.add(emailLabel, 1, 0);
        middleContentContainer.add(enquiryTypeLabel, 2, 0);
        middleContentContainer.add(fullNameTextField, 0, 1);
        middleContentContainer.add(emailTextField, 1, 1);
        middleContentContainer.add(enquiryTypeOptionBox, 2, 1);
        middleContentContainer.add(enquiryDescriptionLabel, 0, 2, 2, 1);
        middleContentContainer.add(enquiryDescriptionTextField, 0, 3, 2, 1);

        bottomContentContainer.getChildren().addAll(uploadLabel, uploadButton, createTicketButton);

        enquiryTypeOptionBox.getItems().addAll("Option 1", "Option 2", "Option 3");
        enquiryTypeOptionBox.getSelectionModel().select(0);
    }

    private void attachListeners() {

    }
}
