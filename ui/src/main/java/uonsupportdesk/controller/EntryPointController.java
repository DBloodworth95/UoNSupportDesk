package uonsupportdesk.controller;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.command.LoginRequest;
import uonsupportdesk.drawer.AccountDetailsDrawer;
import uonsupportdesk.module.*;
import uonsupportdesk.view.LoginView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class EntryPointController {

    private final LoginView loginView;

    private final ClientBootstrap clientBootstrap;

    private ToolbarItem accountToolbar;

    private static final String TITLE = "UoN Support Ticket System";

    public EntryPointController(LoginView loginView, ClientBootstrap clientBootstrap) {
        this.loginView = loginView;
        this.clientBootstrap = clientBootstrap;
    }

    public void initView() {
        Stage mainStage = new Stage();
        Scene mainScene = new Scene(loginView);
        mainStage.setScene(mainScene);
        mainStage.setTitle(TITLE);
        mainStage.setWidth(800);
        mainStage.setHeight(800);
        mainStage.show();
        attachLoginButtonListener();
    }

    private void attachLoginButtonListener() {
        JFXButton loginButton = loginView.getLoginButton();
        loginButton.setOnAction(e -> handleLoginButtonPressed());
    }

    private void handleLoginButtonPressed() {
        String emailTextfield = loginView.getEmailTextField().getText();
        String passwordField = loginView.getPasswordField().getText();
        Stage stage;

        stage = (Stage) loginView.getScene().getWindow();
        stage.setMaximized(true);
        stage.setResizable(true);

        ObjectMapper loginRequestMapper = new ObjectMapper();
        LoginRequest loginRequest = new LoginRequest("login", emailTextfield, passwordField);
        try {
            String requestAsString = loginRequestMapper.writeValueAsString(loginRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Workbench loadApplicationForSupportTeam() {
        accountToolbar = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT));
        Workbench workbench = Workbench.builder(
                new TicketCentreModule(),
                new AssignedTicketsModule(),
                new ArchiveTicketsModule())
                .toolbarLeft()
                .toolbarRight(accountToolbar)
                .build();

        initializeEventHandlers(workbench);

        workbench.getStylesheets().add(EntryPointController.class.getResource("/themes/theme.css").toExternalForm());

        return workbench;
    }

    private Workbench loadApplicationForRegularUser() {
        accountToolbar = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT));
        Workbench workbench = Workbench.builder(
                new CreateTicketModule(),
                new UserTicketsModule(),
                new ArchiveTicketsModule(),
                new FaqModule())
                .toolbarLeft()
                .toolbarRight(accountToolbar)
                .build();

        initializeEventHandlers(workbench);

        workbench.getStylesheets().add(EntryPointController.class.getResource("/themes/theme.css").toExternalForm());

        return workbench;
    }

    private void initializeEventHandlers(Workbench workbench) {
        accountToolbar.setOnClick(event -> workbench.showDrawer(new AccountDetailsDrawer(), Side.RIGHT));
    }
}
