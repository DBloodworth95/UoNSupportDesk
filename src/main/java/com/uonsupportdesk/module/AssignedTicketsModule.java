package com.uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.uonsupportdesk.view.AssignedTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class AssignedTicketsModule extends WorkbenchModule {
    private AssignedTicketsView assignedTicketsView;

    public AssignedTicketsModule() {
        super("Tickets assigned to you", MaterialDesignIcon.BOOK_OPEN);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(assignedTicketsView)) {
            assignedTicketsView = new AssignedTicketsView();
        }
        return assignedTicketsView;
    }
}
