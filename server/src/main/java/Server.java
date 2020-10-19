import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private static final int PORT = 8818;

    private static final int SINGLE_THREAD = 1;

    private final List<ConnectionHelper> connections = new ArrayList<>();

    private final ExecutorService connectionHelperService;

    public Server(boolean isMultiThreaded) {
        if (isMultiThreaded)
            this.connectionHelperService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        else
            this.connectionHelperService = Executors.newFixedThreadPool(SINGLE_THREAD);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + serverSocket.getLocalPort() + "..");
            System.out.println("Accepting client connections..");
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
