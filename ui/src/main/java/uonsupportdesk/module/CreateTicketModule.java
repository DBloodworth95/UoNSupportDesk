package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.view.CreateTicketFormView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class CreateTicketModule extends WorkbenchModule {

    private CreateTicketFormView createTicketFormView;

    public CreateTicketModule() {
        super("Open a ticket", MaterialDesignIcon.BOOK_PLUS);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(createTicketFormView)) {
            createTicketFormView = new CreateTicketFormView();
        }
        return createTicketFormView;
    }
}
