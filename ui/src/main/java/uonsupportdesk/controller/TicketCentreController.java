package uonsupportdesk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.FetchUnassignedTicketRequest;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.TicketCentreView;

public class TicketCentreController implements ClientListener {
    private final ClientBootstrap clientBootstrap;

    private final Session session;

    private final TicketCentreView ticketCentreView;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    public TicketCentreController(ClientBootstrap clientBootstrap, Session session, TicketCentreView ticketCentreView) {
        this.clientBootstrap = clientBootstrap;
        this.session = session;
        this.ticketCentreView = ticketCentreView;
    }

    public TicketCentreView initView() {
        submitWrappedUnassignedTicketRequest();
        clientBootstrap.getInitializer().getHandler().addListener(this);
        return ticketCentreView;
    }

    private void submitWrappedUnassignedTicketRequest() {
        try {
            FetchUnassignedTicketRequest fetchUnassignedTicketRequest = new FetchUnassignedTicketRequest();
            String requestAsString = jsonMapper.writeValueAsString(fetchUnassignedTicketRequest);
            clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processMessageFromClient(String msg) {

    }
}
