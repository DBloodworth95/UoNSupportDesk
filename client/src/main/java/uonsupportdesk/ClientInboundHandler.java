package uonsupportdesk;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class ClientInboundHandler extends SimpleChannelInboundHandler<String> {

    private final List<ClientListener> listeners;

    public ClientInboundHandler(List<ClientListener> listeners) {
        this.listeners = listeners;
    }

    public void addListener(ClientListener clientListener) {
        listeners.add(clientListener);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        //ctx.writeAndFlush(msg);
        for (ClientListener listener : listeners) {
            listener.process(msg);
        }
        System.out.println(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Inactive");
    }
}
