public class Main {
    public static void main(String[] args) {
        Server server = new Server(true);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
