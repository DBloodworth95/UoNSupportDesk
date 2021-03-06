import account.AccessLevel;
import client.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import service.AccountService;
import service.LoginService;
import service.MessageService;
import service.TicketService;

import java.util.List;
import java.util.Map;

public class ChannelHandler extends SimpleChannelInboundHandler<String> {
    private final ObjectMapper commandMapper = new ObjectMapper();

    private final Map<Integer, User> mapOfChannels;

    private final LoginService loginService = new LoginService();

    private final TicketService ticketService = new TicketService();

    private final MessageService messageService = new MessageService();

    private final AccountService accountService = new AccountService();

    private static final String LOGIN_COMMAND = "login";

    private static final String MESSAGE_COMMAND = "sendmessage";

    private static final String FETCH_TICKET_NOTE_COMMAND = "fetchticketnote";

    private static final String CREATE_ACADEMIC_TICKET_COMMAND = "academicticket";

    private static final String CREATE_TECHNICAL_TICKET_COMMAND = "technicalticket";

    private static final String GET_ALL_TICKETS_COMMAND = "getalltickets";

    private static final String FETCH_ALL_ARCHIVED_TICKETS_COMMAND = "getallarchivetickets";

    private static final String GET_TICKET_MESSAGES_COMMAND = "fetchmessages";

    private static final String GET_UNASSIGNED_TICKETS_COMMAND = "fetchunassignedtickets";

    private static final String ASSIGN_TICKET_COMMAND = "assignticket";

    private static final String ADD_NOTE_COMMAND = "addnote";

    private static final String CLOSE_TICKET_COMMAND = "closeticket";

    private static final String TICKET_COMMAND_ERROR = "ticketrequestfailed";

    private static final String SUCCESSFUL_TICKET_CREATION_RESPONSE = "createticketsuccess";

    private static final String UPLOAD_IMAGE_REQUEST = "uploadimage";

    public static final AttributeKey<Integer> CHANNEL_ID = AttributeKey.valueOf("Channel IDs");

    public ChannelHandler(Map<Integer, User> mapOfChannels) {
        this.mapOfChannels = mapOfChannels;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            JsonNode commandFromClient = commandMapper.readTree(msg); //Read the incoming JSON client request
            String commandType = commandFromClient.get("command").asText(); //Read the command type from the "command" key/value mapping within the JSON request.

            if (commandType.equalsIgnoreCase(LOGIN_COMMAND)) { //If the command is the "Login" command
                String response = loginService.validate(commandFromClient); //Build a response from the LoginService after it has validated the login request
                ctx.writeAndFlush(response); //Write the response back to the client

                if (response.contains("success")) {
                    User newUser = loginService.generateUser(response, ctx.channel()); //Create a user instance in anticipation for a successful login
                    if (newUser != null) { //If the login was successful
                        ctx.channel().attr(CHANNEL_ID).set(newUser.userId()); //Register the clients channel with the server.
                        mapOfChannels.put(ctx.channel().attr(CHANNEL_ID).get(), newUser); //Add the channel to the server's list of active channels for future communications.
                    }
                }
            } else if (commandType.equalsIgnoreCase(MESSAGE_COMMAND)) {
                String messageResponse = messageService.submitMessage(commandFromClient);
                int idOfMessageParticipant = messageService.getSendTo(commandFromClient);

                ctx.writeAndFlush(messageResponse);
                distributeMessageToParticipant(idOfMessageParticipant, messageResponse);
            } else if (commandType.equalsIgnoreCase(CREATE_ACADEMIC_TICKET_COMMAND)) {
                String ticketResponse = ticketService.submitAcademicTicket(commandFromClient);

                ctx.writeAndFlush(ticketResponse);
                distributeIncomingTicketNotification(ticketResponse, commandFromClient);
            } else if (commandType.equalsIgnoreCase(CREATE_TECHNICAL_TICKET_COMMAND)) {
                String ticketResponse = ticketService.submitTechnicalTicket(commandFromClient);

                ctx.writeAndFlush(ticketResponse);
                distributeIncomingTicketNotification(ticketResponse, commandFromClient);
            } else if (commandType.equalsIgnoreCase(GET_ALL_TICKETS_COMMAND)) {
                List<String> responses = ticketService.getUserTickets(commandFromClient);
                responses.forEach(ctx::writeAndFlush);
            } else if (commandType.equalsIgnoreCase(GET_TICKET_MESSAGES_COMMAND)) {
                String response = messageService.getTicketMessages(commandFromClient);
                ctx.writeAndFlush(response);
            } else if (commandType.equalsIgnoreCase(GET_UNASSIGNED_TICKETS_COMMAND)) {
                String response = ticketService.getUnassignedTickets();
                ctx.writeAndFlush(response);
            } else if (commandType.equalsIgnoreCase(ASSIGN_TICKET_COMMAND)) {
                String response = ticketService.assignTicket(commandFromClient);
                int ticketAuthorId = ticketService.getAuthorId(commandFromClient);

                ctx.writeAndFlush(response);
                distributeMessageToParticipant(ticketAuthorId, response);
                if (!response.contains(TICKET_COMMAND_ERROR)) {
                    String responseForTicketCentreUpdate = ticketService.buildTicketCentreUpdateResponse(commandFromClient);
                    distributeTicketCentreUpdateMessage(responseForTicketCentreUpdate);
                }
            } else if (commandType.equalsIgnoreCase(FETCH_TICKET_NOTE_COMMAND)) {
                String response = ticketService.fetchTicketNote(commandFromClient);
                ctx.writeAndFlush(response);
            } else if (commandType.equalsIgnoreCase(ADD_NOTE_COMMAND)) {
                String response = ticketService.submitTicketNote(commandFromClient);
                ctx.writeAndFlush(response);
            } else if (commandType.equalsIgnoreCase(FETCH_ALL_ARCHIVED_TICKETS_COMMAND)) {
                List<String> responses = ticketService.getUserArchivedTickets(commandFromClient);
                responses.forEach(ctx::writeAndFlush);
            } else if (commandType.equalsIgnoreCase(CLOSE_TICKET_COMMAND)) {
                String response = ticketService.closeTicket(commandFromClient);
                int ticketAuthorId = ticketService.getAuthorId(commandFromClient);

                ctx.writeAndFlush(response);
                distributeMessageToParticipant(ticketAuthorId, response);
            } else if (commandType.equalsIgnoreCase(UPLOAD_IMAGE_REQUEST)) {
                String response = accountService.uploadImage(commandFromClient);
                ctx.writeAndFlush(response);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void distributeIncomingTicketNotification(String ticketResponse, JsonNode originalRequest) {
        if (ticketResponse.contains(SUCCESSFUL_TICKET_CREATION_RESPONSE)) {
            String notification = ticketService.buildNewTicketNotification(originalRequest, ticketResponse);

            distributeTicketCentreUpdateMessage(notification);
        }
    }

    private void distributeTicketCentreUpdateMessage(String responseForTicketCentreUpdate) {
        for (User user : mapOfChannels.values()) {
            if (user.getAccessLevel().equals(AccessLevel.SUPPORT_TEAM)) {
                mapOfChannels.get(user.userId()).getChannel().writeAndFlush(responseForTicketCentreUpdate);
            }
        }
    }

    private void distributeMessageToParticipant(int id, String response) {
        if (mapOfChannels.containsKey(id)) {
            mapOfChannels.get(id).getChannel().writeAndFlush(response);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Inactive");
    }
}
