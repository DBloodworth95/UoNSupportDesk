package com.uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class ArchiveTicketsModule extends WorkbenchModule {
    public ArchiveTicketsModule() {
        super("Archived Tickets", MaterialDesignIcon.DATABASE);
    }

    @Override
    public Node activate() {
        return new Label("Archived Tickets");
    }
}
