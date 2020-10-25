package uonsupportdesk.entrypoint;

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
        LoginView loginView = new LoginView();
        EntryPointController entryPointController = new EntryPointController(loginView);
        entryPointController.initView();
    }
}
