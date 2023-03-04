// # The program that sends message to the server
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 1234);
            
            // Get input and output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            // Send a message to the server
            out.println("Hello server!");
            
            // Receive a response from the server
            String response = in.readLine();
            System.out.println("Server says: " + response);
            
            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
