package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.controller.ArchiveTicketController;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.ArchiveTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class ArchiveTicketsModule extends WorkbenchModule {

    private ArchiveTicketController archiveTicketController;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    public ArchiveTicketsModule(ClientBootstrap clientBootstrap, Session session) {
        super("Archived Tickets", MaterialDesignIcon.DATABASE);
        this.clientBootstrap = clientBootstrap;
        this.session = session;
    }

    @Override
    public Node activate() {
        if (Objects.isNull(archiveTicketController)) {
            ArchiveTicketsView archiveTicketsView = new ArchiveTicketsView();
            archiveTicketController = new ArchiveTicketController(clientBootstrap, session, archiveTicketsView);
        }

        return archiveTicketController.initView();
    }

    @Override
    public void deactivate() {
        archiveTicketController.removeListener();
        super.deactivate();
    }

    @Override
    public boolean destroy() {
        return super.destroy();
    }
}
