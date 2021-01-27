package uonsupportdesk.javaobjectclient;

public class ObjectClientStarter {
    public static void main(String[] args) {
        ClientBootstrapObject clientBootstrapObject = new ClientBootstrapObject();
        Thread thread = new Thread(clientBootstrapObject::initClient);
        thread.start();
    }
}
