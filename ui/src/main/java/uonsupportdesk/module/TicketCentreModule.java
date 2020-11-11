package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.controller.TicketCentreController;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.TicketCentreView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class TicketCentreModule extends WorkbenchModule {
    private TicketCentreController ticketCentreController;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    private final AssignedTicketsModule assignedTicketsModule;

    public TicketCentreModule(ClientBootstrap clientBootstrap, Session session, AssignedTicketsModule assignedTicketsModule) {
        super("Ticket Centre", MaterialDesignIcon.TICKET);
        this.clientBootstrap = clientBootstrap;
        this.session = session;
        this.assignedTicketsModule = assignedTicketsModule;
    }

    @Override
    public Node activate() {
        if (Objects.isNull(ticketCentreController)) {
            TicketCentreView ticketCentreView = new TicketCentreView();
            ticketCentreController = new TicketCentreController(clientBootstrap, session, ticketCentreView, this.getWorkbench(), assignedTicketsModule);
        }
        return ticketCentreController.initView();
    }
}
