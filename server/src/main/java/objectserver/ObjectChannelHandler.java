package objectserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javaobject.SubmitMessageRequest;
import service.MessageService;

import java.io.*;

public class ObjectChannelHandler extends SimpleChannelInboundHandler<byte[]> {

    private final MessageService messageService = new MessageService();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) {
        System.out.println("Received something..");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(msg);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            SubmitMessageRequest submitMessageRequest = (SubmitMessageRequest) objectInputStream.readObject();
            if (submitMessageRequest.getTicketId() == 1) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(submitMessageRequest);
                    objectOutputStream.flush();
                    ctx.writeAndFlush(byteArrayOutputStream.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
