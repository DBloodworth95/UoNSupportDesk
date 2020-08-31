package com.uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.uonsupportdesk.view.ActiveTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class ActiveTicketsModule extends WorkbenchModule {
    private ActiveTicketsView activeTicketsView;

    public ActiveTicketsModule() {
        super("Active Tickets", MaterialDesignIcon.TICKET);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(activeTicketsView)) {
            activeTicketsView = new ActiveTicketsView();
        }
        return activeTicketsView;
    }
}
