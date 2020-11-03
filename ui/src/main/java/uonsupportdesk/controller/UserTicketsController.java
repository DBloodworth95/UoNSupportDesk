package uonsupportdesk.controller;

import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.UserTicketsView;

public class UserTicketsController {

    private final UserTicketsView userTicketsView;

    private final ClientBootstrap clientBootstrap;

    private final Session session;

    public UserTicketsController(UserTicketsView userTicketsView, Session session, ClientBootstrap clientBootstrap) {
        this.userTicketsView = userTicketsView;
        this.session = session;
        this.clientBootstrap = clientBootstrap;
    }

    public UserTicketsView initView() {
        userTicketsView.attachListeners();
        return userTicketsView;
    }
}
