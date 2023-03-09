// # The server program that listens to the client message
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            // Create a server socket to listen for incoming connections
            // ServerSocket serverSocket = new ServerSocket(1234);

            // To allow the server to take requests from another client in another machine
            ServerSocket serverSocket = new ServerSocket(5000); // 5000 is the port

            // The code for the client
            // Socket socket = new Socket("server-address", 5000);

            // To accept request from client
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                // Create a new thread to handle the client connection
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
