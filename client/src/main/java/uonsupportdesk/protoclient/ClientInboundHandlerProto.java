package uonsupportdesk.protoclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protobuf.ProtoMessageBuffer;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class ClientInboundHandlerProto extends SimpleChannelInboundHandler<ProtoMessageBuffer.ProtoMessage> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Channel Active");

        Instant start = Instant.now(); //Record the time before building the Google ProtoBuffer.
        ProtoMessageBuffer.ProtoMessage.Builder instantMessage = ProtoMessageBuffer.ProtoMessage.newBuilder()
                .setCommand("sendmessage")
                .setSubmitInstantMessageRequest(ProtoMessageBuffer.SubmitInstantMessageRequest.newBuilder()
                        .setTicketId(1)
                        .setAuthorId(1)
                        .setBody("Hello World!")
                        .setTicketType("Academic")
                        .setTimestamp("2020-11-16 14:37:40.214")
                        .build()); //Serialise the Google ProtoBuffer.
        ctx.writeAndFlush(instantMessage.build());
        instantMessage.getCommandBytes().toByteArray();
        Instant end = Instant.now(); //Record the time after build.

        double timeToProcess = Duration.between(start, end).toMillis(); //Compare
        System.out.println("Took " + timeToProcess + "ms to serialise Google ProtoBuffer");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoMessageBuffer.ProtoMessage msg) {
        //System.out.println("Accepted: " + msg.getMessageSubmitRequestAccepted().getBody());
    }
}
