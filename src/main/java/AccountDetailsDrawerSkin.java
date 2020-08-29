import com.dlsc.workbenchfx.view.controls.NavigationDrawer;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AccountDetailsDrawerSkin extends SkinBase<AccountDetailsDrawer> {

    private VBox drawerBox;
    private Button button;
    private BorderPane borderPane;

    public AccountDetailsDrawerSkin(AccountDetailsDrawer accountDetailsDrawer) {
        super(accountDetailsDrawer);
        buildDrawer();
    }

    private void buildDrawer() {
        borderPane = new BorderPane();
        drawerBox = new VBox();
        button = new Button("Test");

        drawerBox.getChildren().addAll(borderPane);
        borderPane.setTop(button);
        drawerBox.setFillWidth(true);
        getChildren().add(drawerBox);
    }
}
