package uonsupportdesk.controller;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
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
        loginView.getLoginButton().setOnAction(e -> handleLoginButtonPressed());

        loginView.getPasswordField().setOnKeyPressed(keyPressed -> {
            if (keyPressed.getCode().equals(KeyCode.ENTER)) {
                loginView.getLoginButton().fire();
            }
        });
    }

    private void handleLoginButtonPressed() {
        loginView.getInvalidAttemptLabel().setVisible(false);

        if (!loginFormEmpty()) {
            String emailTextField = loginView.getEmailTextField().getText(); //Fetch the text within the email field.
            String passwordField = loginView.getPasswordField().getText(); //Fetch the password within the password field.
            LoginRequest loginRequest = new LoginRequest("login", emailTextField, passwordField); //Create a LoginRequest which will be written as a packet.

            try {
                String requestAsString = jsonMapper.writeValueAsString(loginRequest); //Serialise the LoginRequest Object into JSON Format.
                clientBootstrap.getChannel().channel().writeAndFlush(requestAsString); //Write the login request using the channel's pipeline to the server
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    private Workbench loadMainMenuForSupportTeam() {
        accountToolbar = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT)); //Create toolbar to store account details.
        logoutToolbar = new ToolbarItem("Logout", new MaterialDesignIconView(MaterialDesignIcon.POWER)); //Create a toolbar for users to logout.
        loadModules();
        //Build the Workbench which contains each UI Module.
        Workbench workbench = Workbench.builder(
                ticketCentreModule,
                assignedTicketsModule,
                archiveTicketsModule,
                formsModule)
                .toolbarLeft()
                .toolbarRight(accountToolbar, logoutToolbar)
                .build();
        //Starts event listeners for components such as buttons.
        initializeEventHandlers(workbench);
        //Set the UI styling using the appropriate CSS file.
        workbench.getStylesheets().add(EntryPointController.class.getResource("/themes/theme.css").toExternalForm());
        return workbench;
    }

    private Workbench loadMainMenuForRegularUser() {
        accountToolbar = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT));//Create toolbar to store account details.
        logoutToolbar = new ToolbarItem("Logout", new MaterialDesignIconView(MaterialDesignIcon.POWER)); //Create a toolbar for users to logout.
        loadModules();
        //Build the Workbench which contains each UI Module.
        Workbench workbench = Workbench.builder(
                createTicketModule,
                userTicketsModule,
                archiveTicketsModule,
                faqModule,
                formsModule)
                .toolbarLeft()
                .toolbarRight(accountToolbar, logoutToolbar)
                .build();

        //Starts event listeners for components such as buttons.
        initializeEventHandlers(workbench);
        //Set the UI styling using the appropriate CSS file.
        workbench.getStylesheets().add(EntryPointController.class.getResource("/themes/theme.css").toExternalForm());
        return workbench;
    }

    private void initializeEventHandlers(Workbench workbench) {
        accountToolbar.setOnClick(event -> workbench.showDrawer(new AccountDetailsDrawer(session, clientBootstrap), Side.RIGHT));
        logoutToolbar.setOnClick(event -> handleLogout(workbench));
    }

    @Override
    public void processMessageFromClient(String msg) {
        loginView.getInvalidAttemptLabel().setVisible(false);
        try {
            JsonNode responseFromServer = jsonMapper.readTree(msg); //Deserialise the message from the server into JSON
            String responseFromServerAsString = responseFromServer.get("response").asText(); //Retrieve the response code from the message.
            if (responseFromServerAsString.equalsIgnoreCase(SUCCESSFUL_LOGIN)) { //If the response is a "successful login"
                //Retrieve the relevent information from the response in order to create a Session.
                int id = responseFromServer.get("userId").asInt();
                String email = responseFromServer.get("email").asText();
                String name = responseFromServer.get("name").asText();
                String accessLevel = responseFromServer.get("accessLevel").asText();
                byte[] imageAsStream = responseFromServer.get("profilePicture").binaryValue();

                session = new Session(id, email, name, AccessLevel.fromString(accessLevel), imageAsStream); //Create a Session to keep track of the user
                loadMainMenu(session); //Load the main menu
            } else {
                Platform.runLater(this::notifyOfInvalidLoginAttempt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyOfInvalidLoginAttempt() {
        loginView.getInvalidAttemptLabel().setText("Invalid Username or Password!");
        loginView.getInvalidAttemptLabel().setVisible(true);
    }

    private void loadMainMenu(Session session) {
        Stage stage;
        stage = (Stage) loginView.getScene().getWindow(); //Get the users current view of the UI.
        Platform.runLater(() -> stage.setMaximized(true)); //Set the window to maximised after the Client thread has finished its task.
        Platform.runLater(() -> stage.setResizable(true)); //Set the window to be resizable after the Client thread has finished its task.

        if (session.getAccessLevel().equals(AccessLevel.SUPPORT_TEAM)) { //If the sessions access level is Support Team
            loginView.getScene().setRoot(loadMainMenuForSupportTeam()); //Load the Support team's main menu
        } else {
            loginView.getScene().setRoot(loadMainMenuForRegularUser()); //Otherwise, load the regular users main menu.
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

    private boolean loginFormEmpty() {
        boolean isEmpty = false;

        if (loginView.getEmailTextField().getText().isEmpty()) {
            isEmpty = true;
            loginView.getInvalidAttemptLabel().setText("Please enter a email!");
            loginView.getInvalidAttemptLabel().setVisible(true);
        }
        if (loginView.getPasswordField().getText().isEmpty()) {
            isEmpty = true;
            loginView.getInvalidAttemptLabel().setText("Please enter a password!");
            loginView.getInvalidAttemptLabel().setVisible(true);
        }
        if (loginView.getEmailTextField().getText().isEmpty() && loginView.getPasswordField().getText().isEmpty()) {
            isEmpty = true;
            loginView.getInvalidAttemptLabel().setText("Please enter a username and password!");
            loginView.getInvalidAttemptLabel().setVisible(true);
        }

        return isEmpty;
    }

    private void handleLogout(Workbench workbench) {
        workbench.getScene().setRoot(loginView);
    }
}
