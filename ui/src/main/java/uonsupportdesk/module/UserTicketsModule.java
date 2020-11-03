package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.controller.UserTicketsController;
import uonsupportdesk.view.UserTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class UserTicketsModule extends WorkbenchModule {

    private UserTicketsController userTicketsController;

    private int initialTicketId;

    private int initialConversationId;

    public UserTicketsModule() {
        super("My Active Tickets", MaterialDesignIcon.TICKET);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(userTicketsController)) {
            UserTicketsView userTicketsView = new UserTicketsView(initialTicketId, initialConversationId);
            userTicketsController = new UserTicketsController(userTicketsView);
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
