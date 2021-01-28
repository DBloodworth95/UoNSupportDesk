package uonsupportdesk.javaobjectclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javaobject.MessageSubmitRequestAccepted;
import javaobject.SubmitMessageRequest;
import utils.Deserializer;
import utils.Serializer;

public class ClientInboundHandlerObject extends SimpleChannelInboundHandler<byte[]> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        SubmitMessageRequest submitMessageRequest = new SubmitMessageRequest("submitmessage", 1, "IT", "Hello World!",
                "2020-11-16 14:37:40.214", 1);

        byte[] requestToByteArray = Serializer.serialize(submitMessageRequest);
        ctx.writeAndFlush(requestToByteArray);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
        System.out.println("Received something..");

        MessageSubmitRequestAccepted messageSubmitRequestAccepted = (MessageSubmitRequestAccepted) Deserializer.deserialize(msg);

        assert messageSubmitRequestAccepted != null;
        System.out.println("Message accepted: " + messageSubmitRequestAccepted.getBody());
    }
}
