package com.uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.uonsupportdesk.view.UnassignedTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class UnassignedTicketsModule extends WorkbenchModule {
    private UnassignedTicketsView unassignedTicketsView;

    public UnassignedTicketsModule() {
        super("Unassigned Tickets", MaterialDesignIcon.TICKET);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(unassignedTicketsView)) {
            unassignedTicketsView = new UnassignedTicketsView();
        }
        return unassignedTicketsView;
    }
}
