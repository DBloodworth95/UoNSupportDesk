import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

public class ClientBootstrap {
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    public static final int PORT = 8818;

    public static final String HOST = "localhost";

    public void initClient() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());

        try {
            ChannelFuture channelFuture = clientBootstrap.connect(HOST, PORT).sync();

            if (channelFuture.isSuccess()) {
                logger.info("Client connected successfully at port " + PORT);
            }
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.info("Connection to server failed.");
        } finally {
            logger.info("Closing client..");
            eventLoopGroup.shutdownGracefully();
        }
    }
}
