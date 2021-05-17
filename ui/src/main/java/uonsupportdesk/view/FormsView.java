package uonsupportdesk.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import uonsupportdesk.module.component.form.FormURL;
import uonsupportdesk.module.component.form.FormWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class FormsView extends ScrollPane {

    private final BorderPane rootPane;

    private final VBox headerContainer;

    private final GridPane formContentContainer;

    private final Label headerLabel;

    private final Label formContentHeaderLabel;

    private final Label subheaderLabel;

    private final Image universityLogoImage;

    private final ImageView universityLogoImageView;

    private final VBox logoContainer;

    private final FormWidget generalWidget;

    private final FormWidget financeWidget;

    private final FormWidget accommodationWidget;

    private final FormWidget courseWidget;

    private final FormWidget securityWidget;

    private final FormWidget otherWidget;

    private final List<FormWidget> widgets = new ArrayList<>();

    private final List<FormURL> generalLinks = new ArrayList<>();

    public FormsView() {
        headerContainer = new VBox();
        logoContainer = new VBox();
        formContentContainer = new GridPane();
        headerLabel = new Label("Welcome to the Document Repository");
        subheaderLabel = new Label("Feel free to browse and download any of the forms below");
        formContentHeaderLabel = new Label("Documents for Download");
        generalWidget = new FormWidget("General Documents");
        financeWidget = new FormWidget("Financial Documents");
        accommodationWidget = new FormWidget("Accommodation Documents");
        courseWidget = new FormWidget("Course Documents");
        securityWidget = new FormWidget("Security Documents");
        otherWidget = new FormWidget("Other Documents");
        rootPane = new BorderPane();

        universityLogoImage = new Image(getClass().getResourceAsStream("/icons/uon_logo.png"));
        universityLogoImageView = new ImageView(universityLogoImage);
        universityLogoImageView.setFitHeight(150);
        universityLogoImageView.setFitWidth(150);

        widgets.add(generalWidget);
        widgets.add(financeWidget);
        widgets.add(accommodationWidget);
        widgets.add(courseWidget);
        widgets.add(securityWidget);
        widgets.add(otherWidget);

        logoContainer.getChildren().add(universityLogoImageView);
        logoContainer.setAlignment(Pos.CENTER_LEFT);

        headerContainer.getStyleClass().add("header-container");
        formContentContainer.getStyleClass().add("faq-header-container");
        headerLabel.getStyleClass().add("faq-header");
        subheaderLabel.getStyleClass().add("faq-subheader");
        formContentHeaderLabel.getStyleClass().add("faq-content-header");

        positionComponents();
        addContentToWindows();
        attachListeners();
        addFormDownloadLinks();
    }

    private void positionComponents() {
        headerContainer.prefHeightProperty().bind(this.heightProperty().multiply(0.25));
        formContentContainer.prefHeightProperty().bind(this.heightProperty().multiply(0.75));
        rootPane.prefWidthProperty().bind(this.widthProperty());

        rootPane.setTop(headerContainer);
        rootPane.setBottom(formContentContainer);
        setContent(rootPane);

        headerContainer.setAlignment(Pos.CENTER);
        headerLabel.setPadding(new Insets(0, 0, 50, 0));
        subheaderLabel.setPadding(new Insets(0, 0, 20, 0));
        formContentHeaderLabel.setPadding(new Insets(25, 20, 0, 20));

        formContentContainer.setVgap(75);
        formContentContainer.getColumnConstraints().addAll(DoubleStream.of(50, 50, 50)
                .mapToObj(width -> {
                    ColumnConstraints constraints = new ColumnConstraints();
                    constraints.setPercentWidth(width);
                    constraints.setFillWidth(true);
                    return constraints;
                }).toArray(ColumnConstraints[]::new));

    }

    private void addContentToWindows() {
        formContentContainer.add(formContentHeaderLabel, 0, 0, 2, 1);
        formContentContainer.add(generalWidget, 0, 1);
        formContentContainer.add(financeWidget, 1, 1);
        formContentContainer.add(accommodationWidget, 2, 1);
        formContentContainer.add(courseWidget, 0, 2);
        formContentContainer.add(securityWidget, 1, 2);
        formContentContainer.add(otherWidget, 2, 2);
        headerContainer.getChildren().addAll(logoContainer, headerLabel, subheaderLabel);
    }

    private void attachListeners() {

    }

    private void addFormDownloadLinks() {
        generalLinks.add(new FormURL("Download Application Form", "forms/mycv.pdf"));

        generalWidget.addDownloadLinks(generalLinks);
    }
}