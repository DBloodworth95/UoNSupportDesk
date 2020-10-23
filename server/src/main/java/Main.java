public class Main {
    public static void main(String[] args) {
        try {
            TCPServerBootstrap TCPServerBootstrap = new TCPServerBootstrap();
            TCPServerBootstrap.initBootstrap();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
