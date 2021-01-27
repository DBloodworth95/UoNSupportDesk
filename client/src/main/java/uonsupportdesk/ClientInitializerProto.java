package uonsupportdesk;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import protobuf.ProtoMessageBuffer;

public class ClientInitializerProto extends ChannelInitializer<SocketChannel> {

    private ClientInboundHandlerProto clientInboundHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        clientInboundHandler = new ClientInboundHandlerProto();
        socketChannel.pipeline()
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(ProtoMessageBuffer.ProtoMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(clientInboundHandler);
    }

    public ClientInboundHandlerProto getClientInboundHandler() {
        return clientInboundHandler;
    }
}
