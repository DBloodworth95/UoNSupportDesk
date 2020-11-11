package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.controller.UserTicketsController;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.UserTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class UserTicketsModule extends WorkbenchModule {

    private UserTicketsController userTicketsController;

    private int initialTicketId;

    private String currentTicketType;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    public UserTicketsModule(ClientBootstrap clientBootstrap, Session session) {
        super("My Active Tickets", MaterialDesignIcon.TICKET);
        this.clientBootstrap = clientBootstrap;
        this.session = session;
    }

    @Override
    public Node activate() {
        if (Objects.isNull(userTicketsController)) {
            UserTicketsView userTicketsView = new UserTicketsView();
            userTicketsController = new UserTicketsController(userTicketsView, session, clientBootstrap, initialTicketId, currentTicketType);
        }
        return userTicketsController.initView();
    }

    @Override
    public void deactivate() {
        userTicketsController.removeListener();
        super.deactivate();
    }

    @Override
    public boolean destroy() {
        return super.destroy();
    }

    public void setInitialTicketId(int initialTicketId) {
        this.initialTicketId = initialTicketId;
    }

    public void setInitialTicketType(String currentTicketType) {
        this.currentTicketType = currentTicketType;
    }

    public int getInitialTicketId() {
        return initialTicketId;
    }

    public String getCurrentTicketType() {
        return currentTicketType;
    }
}
