package uonsupportdesk.javaobjectclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javaobject.SubmitMessageRequest;
import utils.ObjectSerializer;

import java.time.Duration;
import java.time.Instant;

public class ClientInboundHandlerObject extends SimpleChannelInboundHandler<byte[]> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Instant start = Instant.now(); //Record the time before building the Java Object.

        SubmitMessageRequest submitMessageRequest = new SubmitMessageRequest("submitmessage", 1, "IT",
                "Hello World!", "2020-11-16 14:37:40.214", 1);

        byte[] requestToByteArray = ObjectSerializer.serialize(submitMessageRequest); //Serialise the Java Object.
        ctx.writeAndFlush(requestToByteArray);
        Instant end = Instant.now(); //Record the time after build.

        double timeToProcess = Duration.between(start, end).toMillis(); //Compare
        System.out.println("Took " + timeToProcess + "ms to serialise Java Object");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
        //System.out.println("Received something..");

        //MessageSubmitRequestAccepted messageSubmitRequestAccepted = (MessageSubmitRequestAccepted) ObjectDeserializer.deserialize(msg);

        //assert messageSubmitRequestAccepted != null;
        //System.out.println("Message accepted: " + messageSubmitRequestAccepted.getBody());
    }
}
