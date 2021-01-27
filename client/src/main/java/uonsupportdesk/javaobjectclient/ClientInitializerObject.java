package uonsupportdesk.javaobjectclient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

public class ClientInitializerObject extends ChannelInitializer<SocketChannel> {

    private ClientInboundHandlerObject clientInboundHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        clientInboundHandler = new ClientInboundHandlerObject();
        socketChannel
                .pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 2, 0, 2))
                .addLast(new ByteArrayDecoder())
                .addLast(new LengthFieldPrepender(2, false))
                .addLast(new ByteArrayEncoder())
                .addLast(clientInboundHandler);
    }

    public ClientInboundHandlerObject getClientInboundHandler() {
        return clientInboundHandler;
    }

}
