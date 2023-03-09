import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Chat server started on port 1234");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

            Thread thread = new Thread(new ClientHandler(clientSocket));
            thread.start();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
    
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
    
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
    
                out.println("Welcome to the chat server!");
    
                while (true) {
                    // read input from client
                    String input = in.readLine();
                    if (input == null) {
                        break;
                    }
                    System.out.println("Received message from " + clientSocket.getInetAddress().getHostAddress() + ": " + input);
    
                    // broadcast message to all connected clients
                    for (Thread t : Thread.getAllStackTraces().keySet()) {
                        if (t.getState() == Thread.State.RUNNABLE && t.getName().startsWith("Thread-") && !t.getName().equals(Thread.currentThread().getName())) {
                            ClientHandler handler = (ClientHandler) t.getUncaughtExceptionHandler();
                            handler.out.println(clientSocket.getInetAddress().getHostAddress() + ": " + input);
                        }
                    }
    
                    // read input from server console
                    Scanner consoleIn = new Scanner(System.in);
                    if (consoleIn.hasNextLine()) {
                        String consoleInput = consoleIn.nextLine();
                        // send input to client
                        out.println("Server: " + consoleInput);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }
}
