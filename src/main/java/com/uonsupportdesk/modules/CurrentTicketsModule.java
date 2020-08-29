package com.uonsupportdesk.modules;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class CurrentTicketsModule extends WorkbenchModule {
    public CurrentTicketsModule() {
        super("Current Tickets", MaterialDesignIcon.BOOK_OPEN);
    }

    @Override
    public Node activate() {
        return new Label("Current Tickets");
    }
}
