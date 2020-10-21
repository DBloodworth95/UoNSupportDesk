public class Main {
    public static void main(String[] args) {
        try {
            NettyServerBootstrap nettyServerBootstrap = new NettyServerBootstrap();
            nettyServerBootstrap.initBootstrap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
