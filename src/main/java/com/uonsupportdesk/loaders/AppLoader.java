package com.uonsupportdesk.loaders;

import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.uonsupportdesk.drawers.AccountDetailsDrawer;
import com.uonsupportdesk.modules.ActiveTicketsModule;
import com.uonsupportdesk.modules.ArchiveTicketsModule;
import com.uonsupportdesk.modules.CurrentTicketsModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class AppLoader extends Application {

    private static final int STAGE_WIDTH = 800;

    private static final int STAGE_HEIGHT = 800;

    private static final String TITLE = "UoN Support Ticket System";

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
    }

    private Workbench loadWorkbench() {
        Region region = new Region();
        region.setPrefSize(200, 300);

        ToolbarItem accountButton = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT));
        Workbench workbench = Workbench.builder(
                new ActiveTicketsModule(),
                new CurrentTicketsModule(),
                new ArchiveTicketsModule())
                .toolbarLeft()
                .toolbarRight(accountButton)
                .build();
        accountButton.setOnClick(event -> workbench.showDrawer(new AccountDetailsDrawer(), Side.RIGHT));


        return workbench;
    }
}
