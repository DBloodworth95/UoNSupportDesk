package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.controller.CreateTicketController;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.CreateTicketFormView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class CreateTicketModule extends WorkbenchModule {

    private CreateTicketController createTicketController;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    public CreateTicketModule(ClientBootstrap clientBootstrap, Session session) {
        super("Open a ticket", MaterialDesignIcon.BOOK_PLUS);
        this.clientBootstrap = clientBootstrap;
        this.session = session;
    }

    @Override
    public Node activate() {
        if (Objects.isNull(createTicketController)) {
            CreateTicketFormView createTicketFormView = new CreateTicketFormView();
            createTicketController = new CreateTicketController(createTicketFormView, clientBootstrap, session);
        }
        return createTicketController.initView();
    }
}
