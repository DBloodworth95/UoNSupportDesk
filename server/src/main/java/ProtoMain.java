public class ProtoMain {
    public static void main(String[] args) {
        try {
            TCPServerBootstrapProto TCPServerBootstrap = new TCPServerBootstrapProto();
            TCPServerBootstrap.initBootstrap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
