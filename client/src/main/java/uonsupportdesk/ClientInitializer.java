package uonsupportdesk;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.List;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    private final List<ClientListener> listeners;

    private ClientInboundHandler clientInboundHandler;

    public ClientInitializer(List<ClientListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        clientInboundHandler = new ClientInboundHandler(listeners);
        socketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2)) //Upstream
                .addLast(new StringDecoder()) //Upstream
                .addLast(new LengthFieldPrepender(2, false)) //Downstream
                .addLast(new StringEncoder()) //Downstream
                .addLast(clientInboundHandler);
    }

    public ClientInboundHandler getHandler() {
        return clientInboundHandler;
    }
}