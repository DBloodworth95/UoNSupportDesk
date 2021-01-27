package objectserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.util.Map;

public class ObjectTCPChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Map<Integer, Channel> mapOfChannels;

    public ObjectTCPChannelInitializer(Map<Integer, Channel> mapOfChannels) {
        this.mapOfChannels = mapOfChannels;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel
                .pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 2, 0, 2))
                .addLast(new ByteArrayDecoder())
                .addLast(new LengthFieldPrepender(2, false))
                .addLast(new ByteArrayEncoder())
                .addLast(new ObjectChannelHandler());
    }
}
