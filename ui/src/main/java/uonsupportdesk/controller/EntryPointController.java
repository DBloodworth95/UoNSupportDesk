package uonsupportdesk.controller;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.LoginRequest;
import uonsupportdesk.drawer.AccountDetailsDrawer;
import uonsupportdesk.module.*;
import uonsupportdesk.session.AccessLevel;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.LoginView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class EntryPointController implements ClientListener {

    private ToolbarItem accountToolbar;

    private ToolbarItem logoutToolbar;

    private Session session;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private final LoginView loginView;

    private final ClientBootstrap clientBootstrap;

    private ArchiveTicketsModule archiveTicketsModule;

    private AssignedTicketsModule assignedTicketsModule;

    private CreateTicketModule createTicketModule;

    private FaqModule faqModule;

    private TicketCentreModule ticketCentreModule;

    private UserTicketsModule userTicketsModule;

    private FormsModule formsModule;

    private static final String TITLE = "UoN Support Ticket System";

    private static final String SUCCESSFUL_LOGIN = "success";

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
        clientBootstrap.getInitializer().getHandler().addListener(this);
    }

    private void attachLoginButtonListener() {
        JFXButton loginButton = loginView.getLoginButton();
        loginButton.setOnAction(e -> handleLoginButtonPressed());
    }

    private void handleLoginButtonPressed() {
        String emailTextField = loginView.getEmailTextField().getText();
        String passwordField = loginView.getPasswordField().getText();
        LoginRequest loginRequest = new LoginRequest("login", emailTextField, passwordField);

        try {
            String requestAsString = jsonMapper.writeValueAsString(loginRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private Workbench loadMainMenuForSupportTeam() {
        accountToolbar = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT));
        logoutToolbar = new ToolbarItem("Logout", new MaterialDesignIconView(MaterialDesignIcon.POWER));
        loadModules();

        Workbench workbench = Workbench.builder(
                ticketCentreModule,
                assignedTicketsModule,
                archiveTicketsModule,
                formsModule)
                .toolbarLeft()
                .toolbarRight(accountToolbar, logoutToolbar)
                .build();

        initializeEventHandlers(workbench);

        workbench.getStylesheets().add(EntryPointController.class.getResource("/themes/theme.css").toExternalForm());

        return workbench;
    }

    private Workbench loadMainMenuForRegularUser() {
        accountToolbar = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT));
        logoutToolbar = new ToolbarItem("Logout", new MaterialDesignIconView(MaterialDesignIcon.POWER));
        loadModules();

        Workbench workbench = Workbench.builder(
                createTicketModule,
                userTicketsModule,
                archiveTicketsModule,
                faqModule,
                formsModule)
                .toolbarLeft()
                .toolbarRight(accountToolbar, logoutToolbar)
                .build();

        initializeEventHandlers(workbench);

        workbench.getStylesheets().add(EntryPointController.class.getResource("/themes/theme.css").toExternalForm());

        return workbench;
    }

    private void initializeEventHandlers(Workbench workbench) {
        accountToolbar.setOnClick(event -> workbench.showDrawer(new AccountDetailsDrawer(session, clientBootstrap), Side.RIGHT));
        logoutToolbar.setOnClick(event -> handleLogout(workbench));
    }

    @Override
    public void processMessageFromClient(String msg) {
        try {
            JsonNode responseFromServer = jsonMapper.readTree(msg);
            String responseFromServerAsString = responseFromServer.get("response").asText();
            if (responseFromServerAsString.equalsIgnoreCase(SUCCESSFUL_LOGIN)) {
                int id = responseFromServer.get("userId").asInt();
                String email = responseFromServer.get("email").asText();
                String name = responseFromServer.get("name").asText();
                String accessLevel = responseFromServer.get("accessLevel").asText();
                byte[] imageAsStream = responseFromServer.get("profilePicture").binaryValue();

                session = new Session(id, email, name, AccessLevel.fromString(accessLevel), imageAsStream);
                loadMainMenu(session);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMainMenu(Session session) {
        Stage stage;
        stage = (Stage) loginView.getScene().getWindow();
        Platform.runLater(() -> stage.setMaximized(true));
        Platform.runLater(() -> stage.setResizable(true));

        if (session.getAccessLevel().equals(AccessLevel.SUPPORT_TEAM)) {
            loginView.getScene().setRoot(loadMainMenuForSupportTeam());
        } else {
            loginView.getScene().setRoot(loadMainMenuForRegularUser());
        }
    }

    private void loadModules() {
        userTicketsModule = new UserTicketsModule(clientBootstrap, session);
        createTicketModule = new CreateTicketModule(clientBootstrap, session, userTicketsModule);
        archiveTicketsModule = new ArchiveTicketsModule(clientBootstrap, session);
        assignedTicketsModule = new AssignedTicketsModule(clientBootstrap, session);
        ticketCentreModule = new TicketCentreModule(clientBootstrap, session, assignedTicketsModule);
        faqModule = new FaqModule();
        formsModule = new FormsModule();
    }

    private void handleLogout(Workbench workbench) {
        workbench.getScene().setRoot(loginView);
    }
}
