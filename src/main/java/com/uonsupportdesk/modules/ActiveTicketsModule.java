package com.uonsupportdesk.modules;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class ActiveTicketsModule extends WorkbenchModule {
    public ActiveTicketsModule() {
        super("Active Tickets", MaterialDesignIcon.TICKET);
    }

    @Override
    public Node activate() {
        return new Label("Active Tickets");
    }
}
