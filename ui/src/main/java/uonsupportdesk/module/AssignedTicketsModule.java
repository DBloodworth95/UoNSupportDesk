package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.controller.AssignedTicketController;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.AssignedTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class AssignedTicketsModule extends WorkbenchModule {
    private AssignedTicketController assignedTicketsController;

    private int initialTicketId;

    private String currentTicketType;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    public AssignedTicketsModule(ClientBootstrap clientBootstrap, Session session) {
        super("Tickets Assigned to You", MaterialDesignIcon.BOOK_OPEN);
        this.clientBootstrap = clientBootstrap;
        this.session = session;
    }

    @Override
    public Node activate() {
        if (Objects.isNull(assignedTicketsController)) {
            AssignedTicketsView assignedTicketsView = new AssignedTicketsView();
            assignedTicketsController = new AssignedTicketController(assignedTicketsView, session, clientBootstrap, initialTicketId, currentTicketType);
        }
        return assignedTicketsController.initView();
    }
    @Override
    public void deactivate() {
        assignedTicketsController.removeListener();
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

    public void updateActiveChat(int ticketId, String ticketType) {
        if (!Objects.isNull(assignedTicketsController)) {
            assignedTicketsController.updateActiveChat(ticketId, ticketType);
        }
    }
}
