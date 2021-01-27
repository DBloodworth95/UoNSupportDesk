package uonsupportdesk.protoclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protobuf.ProtoMessageBuffer;

public class ClientInboundHandlerProto extends SimpleChannelInboundHandler<ProtoMessageBuffer.ProtoMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ProtoMessageBuffer.ProtoMessage.Builder instantMessage = ProtoMessageBuffer.ProtoMessage.newBuilder()
                .setCommand("sendmessage")
                .setSubmitInstantMessageRequest(ProtoMessageBuffer.SubmitInstantMessageRequest.newBuilder()
                        .setTicketId(1)
                        .setAuthorId(1)
                        .setBody("Hello World!")
                        .setTicketType("Academic")
                        .setTimestamp("2020-11-16 14:37:40.214")
                        .build());
        ctx.writeAndFlush(instantMessage.build());
        System.out.println("active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoMessageBuffer.ProtoMessage msg) {
        System.out.println("Accepted: " + msg.getMessageSubmitRequestAccepted().getBody());
    }
}
