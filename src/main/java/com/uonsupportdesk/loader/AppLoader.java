package com.uonsupportdesk.loader;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.uonsupportdesk.drawer.AccountDetailsDrawer;
import com.uonsupportdesk.module.UnassignedTicketsModule;
import com.uonsupportdesk.module.ArchiveTicketsModule;
import com.uonsupportdesk.module.AssignedTicketsModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLoader extends Application {

    private static final int STAGE_WIDTH = 800;

    private static final int STAGE_HEIGHT = 800;

    private static final String TITLE = "UoN Support Ticket System";

    private ToolbarItem accountToolbar;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        Scene mainScene = new Scene(loadWorkbench());
        mainStage.setScene(mainScene);
        mainStage.setWidth(STAGE_WIDTH);
        mainStage.setHeight(STAGE_HEIGHT);
        mainStage.setTitle(TITLE);
        mainStage.show();
        mainStage.setResizable(false);
        mainStage.setMaximized(true);
    }

    private Workbench loadWorkbench() {
        accountToolbar = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT));
        Workbench workbench = Workbench.builder(
                new UnassignedTicketsModule(),
                new AssignedTicketsModule(),
                new ArchiveTicketsModule())
                .toolbarLeft()
                .toolbarRight(accountToolbar)
                .build();

        initializeEventHandlers(workbench);

        workbench.getStylesheets().add(AppLoader.class.getResource("/themes/theme.css").toExternalForm());

        return workbench;
    }

    private void initializeEventHandlers(Workbench workbench) {
        accountToolbar.setOnClick(event -> workbench.showDrawer(new AccountDetailsDrawer(), Side.RIGHT));
    }
}
