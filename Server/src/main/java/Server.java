import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private static final int PORT = 8818;

    private final List<ConnectionHelper> connections = new ArrayList<>();

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started...Accepting client connections.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Inbound connection request from " + clientSocket);
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connections.add(connectionHelper);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ConnectionHelper> getConnections() {
        return connections;
    }
}
