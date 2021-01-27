import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import protobuf.ProtoMessageBuffer;

import java.util.Map;

public class ProtoTCPChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Map<Integer, Channel> mapOfChannels;

    public ProtoTCPChannelInitializer(Map<Integer, Channel> mapOfChannels) {
        this.mapOfChannels = mapOfChannels;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel
                .pipeline()
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(ProtoMessageBuffer.ProtoMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(new ProtoChannelHandler());
    }
}

