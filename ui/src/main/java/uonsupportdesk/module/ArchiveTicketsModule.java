package uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import uonsupportdesk.view.ArchiveTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class ArchiveTicketsModule extends WorkbenchModule {
    private ArchiveTicketsView archiveTicketsView;

    public ArchiveTicketsModule() {
        super("Archived Tickets", MaterialDesignIcon.DATABASE);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(archiveTicketsView)) {
            archiveTicketsView = new ArchiveTicketsView();
        }
        return archiveTicketsView;
    }
}
