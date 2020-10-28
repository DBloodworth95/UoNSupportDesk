package uonsupportdesk.controller;

import com.dlsc.workbenchfx.Workbench;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.AcademicTicketRequest;
import uonsupportdesk.module.UserTicketsModule;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.CreateTicketFormView;

public class CreateTicketController implements ClientListener {

    private final ObjectMapper jsonMapper = new ObjectMapper();

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
        if (createTicketFormView.isAcademicTicket()) {
            AcademicTicketRequest academicTicketRequest = new AcademicTicketRequest.Builder(session.getSessionId())
                    .academicTicketCommand()
                    .withFullName(createTicketFormView.getFullNameTextFieldValue())
                    .withEmail(createTicketFormView.getEmailTextFieldValue())
                    .withEnquiryType(createTicketFormView.getEnquiryTypeOptionBoxValue())
                    .withDescription(createTicketFormView.getEnquiryDescriptionTextFieldValue())
                    .onPathway(createTicketFormView.getPathwayComboBoxValueValue())
                    .onYear(createTicketFormView.getYearComboBoxValue())
                    .build();

            try {
                String requestAsString = jsonMapper.writeValueAsString(academicTicketRequest);
                System.out.println(requestAsString);
                clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Create Pressed");
        workbench.openModule(userTicketsModule);
    }

    @Override
    public void processMessageFromClient(String msg) {

    }
}
