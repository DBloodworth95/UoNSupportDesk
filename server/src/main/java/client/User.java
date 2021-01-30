package client;

import account.AccessLevel;
import io.netty.channel.Channel;

public class User {

    private Channel channel;

    private final int userId;

    private final AccessLevel accessLevel;

    public User(Channel channel, int userId, AccessLevel accessLevel) {
        this.channel = channel;
        this.userId = userId;
        this.accessLevel = accessLevel;
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

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
}
