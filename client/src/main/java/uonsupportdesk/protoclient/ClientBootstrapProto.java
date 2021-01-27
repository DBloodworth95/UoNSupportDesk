package uonsupportdesk.protoclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import uonsupportdesk.ClientBootstrap;
import uonsupportdesk.ClientListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClientBootstrapProto {
    private static final Logger LOGGER = Logger.getLogger(ClientBootstrap.class.getName());

    private static final int PORT = 8818;

    private static final String HOST = "localhost";

    private ChannelFuture channelFuture;

    private final List<ClientListener> listeners = new ArrayList<>();

    private final ClientInitializerProto clientInitializer = new ClientInitializerProto();

    public void initClient() {
        LOGGER.info("Initializing client at port " + PORT);

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(clientInitializer);

        try {
            channelFuture = clientBootstrap.connect(HOST, PORT).sync();

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

    public ChannelFuture getChannel() {
        return channelFuture;
    }

}
