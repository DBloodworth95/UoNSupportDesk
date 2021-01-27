package uonsupportdesk;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protobuf.ProtoMessageBuffer;

public class ClientInboundHandlerProto extends SimpleChannelInboundHandler<ProtoMessageBuffer.ProtoMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoMessageBuffer.ProtoMessage msg) {
        System.out.println("Accepted: " + msg.getMessageSubmitRequestAccepted().getBody());
    }
}
