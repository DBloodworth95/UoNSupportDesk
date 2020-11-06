import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Map;

public class TCPChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Map<Integer, Channel> mapOfChannels;

    public TCPChannelInitializer(Map<Integer, Channel> mapOfChannels) {
        this.mapOfChannels = mapOfChannels;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel
                .pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Short.MAX_VALUE, 0, 2, 0, 2))
                .addLast(new StringDecoder())
                .addLast(new LengthFieldPrepender(2, false))
                .addLast(new StringEncoder())
                .addLast(new ChannelHandler(mapOfChannels));
    }
}
