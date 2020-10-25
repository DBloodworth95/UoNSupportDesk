package uonsupportdesk.controller;

import uonsupportdesk.view.UserTicketsView;

public class UserTicketsController {

    private final UserTicketsView userTicketsView;

    public UserTicketsController(UserTicketsView userTicketsView) {
        this.userTicketsView = userTicketsView;
    }

    public UserTicketsView initView() {
        userTicketsView.attachListeners();
        return userTicketsView;
    }
}
