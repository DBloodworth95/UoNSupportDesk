package uonsupportdesk.entrypoint;

import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.controller.EntryPointController;
import uonsupportdesk.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public final class EntryPoint extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        ClientBootstrap clientBootstrap = new ClientBootstrap();
        Thread thread = new Thread(clientBootstrap::initClient);
        LoginView loginView = new LoginView();
        EntryPointController entryPointController = new EntryPointController(loginView, clientBootstrap);
        thread.start();
        entryPointController.initView();

    }
}
