import client.User;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public final class TCPServerBootstrap {
    private final Map<Integer, User> mapOfChannels = new ConcurrentHashMap<>();

    private static final int PORT = 8818;

    private static final Logger LOGGER = Logger.getLogger(TCPServerBootstrap.class.getName());

    public void initBootstrap() throws InterruptedException {
        LOGGER.info("Initializing server at port " + PORT + "..");

        EventLoopGroup connectionRequestHandler = new NioEventLoopGroup(100); //Starting the connection handler with 100 threads.
        EventLoopGroup establishedConnectionHandler = new NioEventLoopGroup(); //Starting the event handler.

        ServerBootstrap serverBootstrap = new ServerBootstrap(); //Bootstrap the server.
        serverBootstrap.group(connectionRequestHandler, establishedConnectionHandler) //Attach the connection/event handlers.
                .channel(NioServerSocketChannel.class) //Attach the socket channel.
                .childHandler(new JSONTCPChannelInitializer(mapOfChannels)) //Attach the socket channel handler.
                .childOption(ChannelOption.SO_KEEPALIVE, true); //Keep the channel alive as TCP Stream is used.

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync(); //Binds the server to port 8818.

            if (channelFuture.isSuccess()) {
                LOGGER.info("Server initialized on port: " + PORT);
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            LOGGER.info("Server shutting down.."); //Handle server shutdown when requested.
            connectionRequestHandler.shutdownGracefully();
            establishedConnectionHandler.shutdownGracefully();
        }
    }
}
