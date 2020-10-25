package uonsupportdesk;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

public class ClientBootstrap {
    private static final Logger LOGGER = Logger.getLogger(ClientBootstrap.class.getName());

    private static final int PORT = 8818;

    private static final String HOST = "localhost";

    public void initClient() {
        LOGGER.info("Initializing client at port " + PORT);

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());

        try {
            ChannelFuture channelFuture = clientBootstrap.connect(HOST, PORT).sync();

            if (channelFuture.isSuccess()) {
                LOGGER.info("Client connected successfully at port " + PORT);
            }
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.info("Connection to server failed.");
        } finally {
            LOGGER.info("Closing client..");
            eventLoopGroup.shutdownGracefully();
        }
    }
}
