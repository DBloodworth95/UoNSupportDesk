package uonsupportdesk.controller;

import com.dlsc.workbenchfx.Workbench;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.module.UserTicketsModule;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.CreateTicketFormView;

public class CreateTicketController implements ClientListener {

    private final CreateTicketFormView createTicketFormView;

    private final Session session;

    private final ClientBootstrap clientBootstrap;

    private final Workbench workbench;

    private final UserTicketsModule userTicketsModule;

    public CreateTicketController(CreateTicketFormView createTicketFormView, ClientBootstrap clientBootstrap, Session session, Workbench workbench, UserTicketsModule userTicketsModule) {
        this.createTicketFormView = createTicketFormView;
        this.clientBootstrap = clientBootstrap;
        this.session = session;
        this.workbench = workbench;
        this.userTicketsModule = userTicketsModule;
    }

    public CreateTicketFormView initView() {
        attachListeners();
        clientBootstrap.getInitializer().getHandler().addListener(this);
        return createTicketFormView;
    }

    private void attachListeners() {
        createTicketFormView.getCreateTicketButton().setOnAction(e -> createTicket());
        createTicketFormView.getEnquiryTypeComboBox().setOnAction(e -> createTicketFormView.loadAdditionalFields());
    }

    private void createTicket() {
        System.out.println("Create Pressed");
        workbench.openModule(userTicketsModule);
    }

    @Override
    public void processMessageFromClient(String msg) {

    }
}
