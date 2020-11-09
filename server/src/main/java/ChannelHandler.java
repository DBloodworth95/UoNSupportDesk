import client.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import service.LoginService;
import service.MessageService;
import service.TicketService;

import java.util.Map;

public class ChannelHandler extends SimpleChannelInboundHandler<String> {
    private final ObjectMapper commandMapper = new ObjectMapper();

    private final Map<Integer, Channel> mapOfChannels;

    private final LoginService loginService = new LoginService();

    private final TicketService ticketService = new TicketService();

    private final MessageService messageService = new MessageService();

    private static final String LOGIN_COMMAND = "login";

    private static final String MESSAGE_COMMAND = "sendmessage";

    private static final String CREATE_ACADEMIC_TICKET_COMMAND = "academicticket";

    private static final String CREATE_TECHNICAL_TICKET_COMMAND = "technicalticket";

    private static final String GET_ALL_TICKETS = "getalltickets";

    private static final String GET_TICKET_MESSAGES_COMMAND = "fetchmessages";

    private static final String GET_UNASSIGNED_TICKETS_COMMAND = "fetchunassignedtickets";

    public static final AttributeKey<Integer> CHANNEL_ID = AttributeKey.valueOf("Channel IDs");

    public ChannelHandler(Map<Integer, Channel> mapOfChannels) {
        this.mapOfChannels = mapOfChannels;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println(msg);
        try {
            JsonNode commandFromClient = commandMapper.readTree(msg);
            String commandType = commandFromClient.get("command").asText();

            if (commandType.equalsIgnoreCase(LOGIN_COMMAND)) {
                String response = loginService.validate(commandFromClient);
                ctx.writeAndFlush(response);

                User newUser = loginService.generateUser(response);
                if (newUser != null) {
                    ctx.channel().attr(CHANNEL_ID).set(newUser.userId());
                    mapOfChannels.put(ctx.channel().attr(CHANNEL_ID).get(), ctx.channel());
                }
            } else if (commandType.equalsIgnoreCase(MESSAGE_COMMAND)) {
                String messageResponse = messageService.submitMessage(commandFromClient);
                int idOfMessageParticipant = messageService.getSendTo(commandFromClient);

                ctx.writeAndFlush(messageResponse);
                distributeMessageToParticipant(idOfMessageParticipant, messageResponse);
            } else if (commandType.equalsIgnoreCase(CREATE_ACADEMIC_TICKET_COMMAND)) {
                String ticketResponse = ticketService.submitAcademicTicket(commandFromClient);

                ctx.writeAndFlush(ticketResponse);
            } else if (commandType.equalsIgnoreCase(CREATE_TECHNICAL_TICKET_COMMAND)) {
                String ticketResponse = ticketService.submitTechnicalTicket(commandFromClient);

                ctx.writeAndFlush(ticketResponse);
            } else if (commandType.equalsIgnoreCase(GET_ALL_TICKETS)) {
                String response = ticketService.getUserTickets(commandFromClient);
                ctx.writeAndFlush(response);
            } else if (commandType.equalsIgnoreCase(GET_TICKET_MESSAGES_COMMAND)) {
                String response = messageService.getTicketMessages(commandFromClient);
                ctx.writeAndFlush(response);
            } else if (commandType.equalsIgnoreCase(GET_UNASSIGNED_TICKETS_COMMAND)) {
                String response = ticketService.getUnassignedTickets();
                ctx.writeAndFlush(response);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void distributeMessageToParticipant(int id, String response) {
        mapOfChannels.get(id).writeAndFlush(response);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Inactive");
    }
}
