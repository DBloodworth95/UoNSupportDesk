package com.uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class AssignedTicketsModule extends WorkbenchModule {
    public AssignedTicketsModule() {
        super("Tickets assigned to you", MaterialDesignIcon.BOOK_OPEN);
    }

    @Override
    public Node activate() {
        return new Label("Tickets assigned to you");
    }
}
