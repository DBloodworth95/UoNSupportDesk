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

    private int initialConversationId;

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
            UserTicketsView userTicketsView = new UserTicketsView(initialTicketId, initialConversationId);
            userTicketsController = new UserTicketsController(userTicketsView, session, clientBootstrap);
        }
        return userTicketsController.initView();
    }

    public void setInitialTicketId(int initialTicketId) {
        this.initialTicketId = initialTicketId;
    }

    public void setInitialConversationId(int initialConversationId) {
        this.initialConversationId = initialConversationId;
    }
}
