package uonsupportdesk.protoclient;

public class ProtoClientStarter {
    public static void main(String[] args) {
        ClientBootstrapProto clientBootstrap = new ClientBootstrapProto();
        Thread thread = new Thread(clientBootstrap::initClient);
        thread.start();
    }
}
