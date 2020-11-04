import client.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import service.LoginService;
import service.MessageService;
import service.TicketService;

import java.util.ArrayList;
import java.util.List;

public class ChannelHandler extends SimpleChannelInboundHandler<String> {

    private final ObjectMapper commandMapper = new ObjectMapper();

    private final List<User> users = new ArrayList<>();

    private final LoginService loginService = new LoginService();

    private final TicketService ticketService = new TicketService();

    private final MessageService messageService = new MessageService();

    private static final String LOGIN_COMMAND = "login";

    private static final String MESSAGE_COMMAND = "message";

    private static final String CREATE_ACADEMIC_TICKET_COMMAND = "academicticket";

    private static final String CREATE_TECHNICAL_TICKET_COMMAND = "technicalticket";

    private static final String GET_ALL_TICKETS = "getalltickets";

    private static final String GET_TICKET_MESSAGES_COMMAND = "fetchmessages";

    public static final AttributeKey<Integer> CHANNEL_ID = AttributeKey.valueOf("Channel IDs");

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

                User user = loginService.generateUser(response);
                if (user != null) {
                    ctx.channel().attr(CHANNEL_ID).set(user.userId());
                    user.setChannel(ctx.channel());
                    users.add(user);
                }
            } else if (commandType.equalsIgnoreCase(MESSAGE_COMMAND)) {
                //TODO HANDLING
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
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Inactive");
        users.removeIf(user -> ctx.channel().attr(CHANNEL_ID).get() == user.userId());
    }
}
