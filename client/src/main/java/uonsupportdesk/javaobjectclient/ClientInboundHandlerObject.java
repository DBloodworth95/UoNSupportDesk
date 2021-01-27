package uonsupportdesk.javaobjectclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javaobject.SubmitMessageRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientInboundHandlerObject extends SimpleChannelInboundHandler<byte[]> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        SubmitMessageRequest submitMessageRequest = new SubmitMessageRequest("submitmessage", 1, "IT", "Hello World!",
                "2020-11-16 14:37:40.214", 1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(submitMessageRequest);
            objectOutputStream.flush();
            ctx.writeAndFlush(byteArrayOutputStream.toByteArray());
            System.out.println("Sent something..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
        System.out.println("Received something..");
    }
}
