package objectserver;

public class ObjectMain {
    public static void main(String[] args) {
        try {
            TcpServerBootstrapObject tcpServerBootstrapObject = new TcpServerBootstrapObject();
            tcpServerBootstrapObject.initBootstrap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
