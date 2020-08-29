import com.dlsc.workbenchfx.Workbench;
import com.dlsc.workbenchfx.view.controls.NavigationDrawer;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
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

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage mainStage) {
        Scene mainScene = new Scene(loadWorkbench());
        mainStage.setScene(mainScene);
        mainStage.setWidth(STAGE_WIDTH);
        mainStage.setHeight(STAGE_HEIGHT);
        mainStage.show();
    }

    private Workbench loadWorkbench() {
        Region region = new Region();
        region.setPrefSize(200, 300);

        ToolbarItem accountButton = new ToolbarItem("Account", new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT));
        Workbench workbench = Workbench.builder()
                .toolbarRight(accountButton)
                .build();
        accountButton.setOnClick(event -> workbench.showDrawer(new AccountDetailsDrawer(), Side.RIGHT));

        return workbench;
    }
}
