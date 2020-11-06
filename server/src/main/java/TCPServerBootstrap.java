import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public final class TCPServerBootstrap {
    private final Map<Integer, Channel> mapOfChannels = new ConcurrentHashMap<>();

    private static final int PORT = 8818;

    private static final Logger LOGGER = Logger.getLogger(TCPServerBootstrap.class.getName());

    public void initBootstrap() throws InterruptedException {
        LOGGER.info("Initializing server at port " + PORT + "..");

        EventLoopGroup connectionRequestHandler = new NioEventLoopGroup();
        EventLoopGroup establishedConnectionHandler = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(connectionRequestHandler, establishedConnectionHandler)
                .channel(NioServerSocketChannel.class)
                .childHandler(new TCPChannelInitializer(mapOfChannels))
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();

            if (channelFuture.isSuccess()) {
                LOGGER.info("Server initialized on port: " + PORT);
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            LOGGER.info("Server shutting down..");
            connectionRequestHandler.shutdownGracefully();
            establishedConnectionHandler.shutdownGracefully();
        }
    }
}
