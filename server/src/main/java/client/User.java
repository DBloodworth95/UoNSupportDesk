package client;

import io.netty.channel.Channel;

public class User {

    private Channel channel;

    private final int userId;

    public User(Channel channel, int userId) {
        this.channel = channel;
        this.userId = userId;
    }

    public Channel getChannel() {
        return channel;
    }

    public int userId() {
        return userId;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
