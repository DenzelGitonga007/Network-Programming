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

            
            while (true) {
                // Accept a new client connection
                Socket clientSocket = serverSocket.accept();
                
                // Get input and output streams for the client socket
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                // Receive a message from the client
                String message = in.readLine();
                System.out.println("Client says: " + message);
                
                // Send a response back to the client
                out.println("Hello client!");
                
                // Close the client socket
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
