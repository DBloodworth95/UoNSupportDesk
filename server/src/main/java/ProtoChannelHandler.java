import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protobuf.ProtoMessageBuffer;
import service.MessageService;

public class ProtoChannelHandler extends SimpleChannelInboundHandler<ProtoMessageBuffer.ProtoMessage> {

    private final MessageService messageService = new MessageService();

    private static final String MESSAGE_COMMAND = "sendmessage";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoMessageBuffer.ProtoMessage msg) {
        System.out.println("message recieved");
        if (msg.getCommand().equalsIgnoreCase(MESSAGE_COMMAND)) {
            ProtoMessageBuffer.ProtoMessage response = messageService.submitMessageFromProto(msg);
            ctx.writeAndFlush(response);
        }
    }
}
