import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import service.LoginService;
import service.MessageService;
import service.TicketService;

public class ChannelHandler extends SimpleChannelInboundHandler<String> {

    private final ObjectMapper commandMapper = new ObjectMapper();

    private static final String LOGIN_COMMAND = "login";

    private static final String MESSAGE_COMMAND = "message";

    private static final String CREATE_ACADEMIC_TICKET_COMMAND = "academicticket";

    private static final String CREATE_TECHNICAL_TICKET_COMMAND = "technicalticket";

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
                LoginService loginService = new LoginService();
                String response = loginService.validate(commandFromClient);
                ctx.writeAndFlush(response);
            } else if (commandType.equalsIgnoreCase(MESSAGE_COMMAND)) {
                MessageService messageService = new MessageService();
                messageService.submit(commandFromClient);
            } else if (commandType.equalsIgnoreCase(CREATE_ACADEMIC_TICKET_COMMAND)) {
                TicketService ticketService = new TicketService();
                String response = ticketService.submitAcademicTicket(commandFromClient);
                ctx.writeAndFlush(response);
            } else if (commandType.equalsIgnoreCase(CREATE_TECHNICAL_TICKET_COMMAND)) {
                TicketService ticketService = new TicketService();
                String response = ticketService.submitTechnicalTicket(commandFromClient);
                ctx.writeAndFlush(response);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Inactive");
    }
}
