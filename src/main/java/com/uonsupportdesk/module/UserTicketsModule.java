package com.uonsupportdesk.module;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.uonsupportdesk.controller.UserTicketsController;
import com.uonsupportdesk.view.UserTicketsView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;

import java.util.Objects;

public class UserTicketsModule extends WorkbenchModule {

    private UserTicketsView userTicketsView;

    private UserTicketsController userTicketsController;

    public UserTicketsModule() {
        super("My Active Tickets", MaterialDesignIcon.TICKET);
    }

    @Override
    public Node activate() {
        if (Objects.isNull(userTicketsController)) {
            userTicketsView = new UserTicketsView();
            userTicketsController = new UserTicketsController(userTicketsView);
            userTicketsController.initView();
        }
        return userTicketsView;
    }
}
