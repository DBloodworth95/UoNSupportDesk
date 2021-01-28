package objectserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import service.MessageService;

public class ObjectChannelHandler extends SimpleChannelInboundHandler<byte[]> {

    private final MessageService messageService = new MessageService();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
        System.out.println("Received something..");

        byte[] response = messageService.submitMessageFromJavaObject(msg);
        ctx.writeAndFlush(response);
    }
}
