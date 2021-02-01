import client.User;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Map;

public class JSONTCPChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Map<Integer, User> mapOfChannels;

    public JSONTCPChannelInitializer(Map<Integer, User> mapOfChannels) {
        this.mapOfChannels = mapOfChannels;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel
                .pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 2, 0, 2))
                .addLast(new StringDecoder())
                .addLast(new LengthFieldPrepender(2, false))
                .addLast(new StringEncoder())
                .addLast(new ChannelHandler(mapOfChannels));
    }
}
