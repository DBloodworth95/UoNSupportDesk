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

    public void removeListener(ClientListener clientListener) {
        System.out.println(clientListener + " listener to be removed");
        listeners.remove(clientListener);
        System.out.println(listeners);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //This method is invoked when the client initially connects to the server successfully.
        System.out.println(ctx.channel().remoteAddress() + " Channel Active");
    }

    @Override //This method is invoked whenever a message is received from the Server.
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        for (ClientListener listener : listeners) {
            listener.processMessageFromClient(msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " Channel Inactive");
    }
}
