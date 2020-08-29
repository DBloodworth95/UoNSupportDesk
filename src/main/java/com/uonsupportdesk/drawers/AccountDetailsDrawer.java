package com.uonsupportdesk.drawers;

import com.dlsc.workbenchfx.view.controls.NavigationDrawer;
import javafx.scene.control.Skin;

public class AccountDetailsDrawer extends NavigationDrawer {

    private static final int DRAWER_HEIGHT = 800;

    private static final int DRAWER_WIDTH = 200;

    public AccountDetailsDrawer() {
        super();
        setPrefSize(DRAWER_WIDTH, DRAWER_HEIGHT);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AccountDetailsDrawerSkin(this);
    }
}
