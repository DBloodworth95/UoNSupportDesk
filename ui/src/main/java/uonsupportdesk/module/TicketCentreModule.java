package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.view.TicketCentreView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class TicketCentreModule extends WorkbenchModule {
    private TicketCentreView ticketCentreView;

    public TicketCentreModule() {
        super("Ticket Centre", MaterialDesignIcon.TICKET);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(ticketCentreView)) {
            ticketCentreView = new TicketCentreView();
        }
        return ticketCentreView;
    }
}
