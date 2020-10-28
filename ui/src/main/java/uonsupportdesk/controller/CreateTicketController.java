package uonsupportdesk.controller;

import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.CreateTicketFormView;

public class CreateTicketController implements ClientListener {

    private final CreateTicketFormView createTicketFormView;

    private final Session session;

    private final ClientBootstrap clientBootstrap;

    public CreateTicketController(CreateTicketFormView createTicketFormView, ClientBootstrap clientBootstrap, Session session) {
        this.createTicketFormView = createTicketFormView;
        this.clientBootstrap = clientBootstrap;
        this.session = session;
    }

    public CreateTicketFormView initView() {
        return createTicketFormView;
    }

    @Override
    public void process(String msg) {

    }
}
