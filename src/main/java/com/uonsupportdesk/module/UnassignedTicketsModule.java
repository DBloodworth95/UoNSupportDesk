package com.uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class UnassignedTicketsModule extends WorkbenchModule {


    public UnassignedTicketsModule() {
        super("Unassigned Tickets", MaterialDesignIcon.TICKET);
    }

    @Override
    public Node activate() {
        return new Label("Unassigned tickets");
    }
}
