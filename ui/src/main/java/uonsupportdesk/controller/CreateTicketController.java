package uonsupportdesk.controller;

import com.dlsc.workbenchfx.Workbench;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;
import uonsupportdesk.command.AcademicTicketRequest;
import uonsupportdesk.command.Command;
import uonsupportdesk.command.TechnicalTicketRequest;
import uonsupportdesk.module.UserTicketsModule;
import uonsupportdesk.session.Session;
import uonsupportdesk.view.CreateTicketFormView;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketController implements ClientListener {

    private final ObjectMapper jsonMapper = new ObjectMapper();

    private final CreateTicketFormView createTicketFormView;

    private final Session session;

    private final ClientBootstrap clientBootstrap;

    private final Workbench workbench;

    private final UserTicketsModule userTicketsModule;

    private static final String SUCCESSFUL_TICKET_SUBMISSION = "createticketsuccess";

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

    public void removeListener() {
        clientBootstrap.getInitializer().getHandler().removeListener(this);
    }

    private void attachListeners() {
        createTicketFormView.getCreateTicketButton().setOnAction(e -> submitWrappedTicketToServer());
        createTicketFormView.getEnquiryTypeComboBox().setOnAction(e -> createTicketFormView.loadAdditionalFields());
    }

    private Command wrapTicketAsCommand() {
        Command ticketRequest = null;

        if (createTicketFormView.isAcademicTicket()) {
            ticketRequest = new AcademicTicketRequest.Builder(session.getSessionId())
                    .academicTicketCommand()
                    .withFullName(createTicketFormView.getFullNameTextFieldValue())
                    .withEmail(createTicketFormView.getEmailTextFieldValue())
                    .withEnquiryType(createTicketFormView.getEnquiryTypeOptionBoxValue())
                    .withDescription(createTicketFormView.getEnquiryDescriptionTextFieldValue())
                    .onPathway(createTicketFormView.getPathwayComboBoxValueValue())
                    .onYear(createTicketFormView.getYearComboBoxValue())
                    .build();
        } else if (createTicketFormView.isTechTicket()) {
            ticketRequest = new TechnicalTicketRequest.Builder(session.getSessionId())
                    .technicalTicketCommand()
                    .withFullName(createTicketFormView.getFullNameTextFieldValue())
                    .withEmail(createTicketFormView.getEmailTextFieldValue())
                    .withEnquiryType(createTicketFormView.getEnquiryTypeOptionBoxValue())
                    .withDescription(createTicketFormView.getEnquiryDescriptionTextFieldValue())
                    .build();
        }

        return ticketRequest;
    }

    private void submitWrappedTicketToServer() {
        clearInputValidation();

        if (!inputFieldsEmpty()) {
            Command wrappedTicket = wrapTicketAsCommand();
            try {
                String requestAsString = jsonMapper.writeValueAsString(wrappedTicket);
                System.out.println(requestAsString);
                clientBootstrap.getChannel().channel().writeAndFlush(requestAsString);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            clearInputValidation();
        }
    }

    private void clearInputValidation() {
        createTicketFormView.getEmptyFullNameLabel().setVisible(false);
        createTicketFormView.getEmptyEmailLabel().setVisible(false);
        createTicketFormView.getEmptyEnquiryLabel().setVisible(false);
        createTicketFormView.getEmptyDescriptionLabel().setVisible(false);
    }

    private boolean inputFieldsEmpty() {
        boolean isEmpty = false;

        if (createTicketFormView.getFullNameTextFieldValue().isEmpty()) {
            createTicketFormView.getEmptyFullNameLabel().setVisible(true);
            isEmpty = true;
        }
        if (createTicketFormView.getEmailTextFieldValue().isEmpty()) {
            createTicketFormView.getEmptyEmailLabel().setVisible(true);
            isEmpty = true;
        }
        if (createTicketFormView.getEnquiryTypeComboBox().getSelectionModel().getSelectedIndex() == 0) {
            createTicketFormView.getEmptyEnquiryLabel().setVisible(true);
            isEmpty = true;
        }
        if (createTicketFormView.getEnquiryDescriptionTextFieldValue().isEmpty()) {
            createTicketFormView.getEmptyDescriptionLabel().setVisible(true);
            isEmpty = true;
        }

        return isEmpty;
    }

    @Override
    public void processMessageFromClient(String msg) {
        try {
            JsonNode responseFromServer = jsonMapper.readTree(msg);
            String responseFromServerAsString = responseFromServer.get("response").asText();

            if (responseFromServerAsString.equalsIgnoreCase(SUCCESSFUL_TICKET_SUBMISSION)) {
                int initialTicketId = responseFromServer.get("ticketId").asInt();
                String ticketType = responseFromServer.get("enquiryType").asText();

                userTicketsModule.setInitialTicketId(initialTicketId);
                userTicketsModule.setInitialTicketType(ticketType);

                Platform.runLater(() -> userTicketsModule.updateActiveChat(initialTicketId, ticketType));
                Platform.runLater(() -> workbench.openModule(userTicketsModule));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
