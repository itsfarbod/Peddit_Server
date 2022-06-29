import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static private boolean isServerUp = true;

    public static void setIsServerUp(boolean isServerUp) {
        Server.isServerUp = isServerUp;
    }

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(8585);
            System.out.println("Server Socket Created on port " + serverSocket.getLocalPort());
        } catch (IOException e) {
            System.out.println("Failed to create server!");
            serverSocket = null;
        }

        while(isServerUp) {
            try{
                Socket socket = serverSocket.accept();
                System.out.println("Connected to client with ip " + socket.getInetAddress().toString());
                new MainRequestHandler(socket).start();
            }catch (IOException e) {
                System.out.println("Failed to connect to client!");
            }
        }
    }
}
