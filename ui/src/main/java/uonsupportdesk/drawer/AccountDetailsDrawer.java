package uonsupportdesk.drawer;

import com.dlsc.workbenchfx.view.controls.NavigationDrawer;
import javafx.scene.control.Skin;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.session.Session;

public class AccountDetailsDrawer extends NavigationDrawer {

    private static final int DRAWER_HEIGHT = 800;

    private static final int DRAWER_WIDTH = 200;

    private final Session session;

    private final ClientBootstrap clientBootstrap;

    public AccountDetailsDrawer(Session session, ClientBootstrap clientBootstrap) {
        super();
        this.session = session;
        this.clientBootstrap = clientBootstrap;
        setPrefSize(DRAWER_WIDTH, DRAWER_HEIGHT);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AccountDetailsDrawerSkin(this, session, clientBootstrap);
    }
}
