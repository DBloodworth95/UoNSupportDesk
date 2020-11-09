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

    public TicketCentreModule(ClientBootstrap clientBootstrap, Session session) {
        super("Ticket Centre", MaterialDesignIcon.TICKET);
        this.clientBootstrap = clientBootstrap;
        this.session = session;
    }

    @Override
    public Node activate() {
        if (Objects.isNull(ticketCentreController)) {
            TicketCentreView ticketCentreView = new TicketCentreView();
            ticketCentreController = new TicketCentreController(clientBootstrap, session, ticketCentreView);
        }
        return ticketCentreController.initView();
    }
}
