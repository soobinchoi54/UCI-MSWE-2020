import java.io.*;
import java.net.Socket;

public class Client {

    static final int PORT = 4444;

    public static void main(String[] args) {

        String host;
        Socket connection;
        PrintWriter outgoing; // returns OutputStream for writing data from other end of the socket
        BufferedReader incoming; // call BufferedReader to return InputStream to read data from the socket
        String command;

        if (args.length == 0 || args.length > 2) {
            System.out.println("Usage:  java Client <server>");
            System.out.println("    or  java Client <server> <file>");
            return;
        }

        host = args[0];

        if (args.length == 1)
            command = "INDEX";
        else
            command = "GET " + args[1];

        try {
            connection = new Socket(host, PORT);
            incoming = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            outgoing = new PrintWriter(connection.getOutputStream());
            outgoing.println(command);
            outgoing.flush();
        } catch (Exception e) {
            System.out.println("      Error:  " + e);
            System.out.println("      Can't make connection to server at \"" + args[0]);
            return;
        }

        try {
            // If there is only 1 argument, retrieve file list
            if (args.length == 1) {
                System.out.println("      File list from " + args[0] + ":");
                while (true) {
                    String line = incoming.readLine();
                    if (line == null)
                        break;
                    System.out.println("      " + line);
                }
            } else {
                String message = incoming.readLine();
                if (!message.equalsIgnoreCase("OK")) {
                    System.out.println("      File does not exist");
                    System.out.println("      Server message: \n" + "   " + message);
                    return;
                }
                File file = new File(args[1]);
                if (file.exists()) {
                    System.out.println("      File with name already exists");
                    return;
                }
                PrintWriter fileOut = new PrintWriter(new FileWriter(args[1]));
                while (true) {
                    String line = incoming.readLine();
                    if (line == null)
                        break;
                    fileOut.println(line);
                }
                if (fileOut.checkError()) {
                    System.out.println("      Error");
                }
            }
        } catch (Exception ex) {
            System.out.println("      Error: " + ex);
        } finally {
            try {
                // Explicitly close the socket in a finally block to release resources the socket holds
                connection.close();
            } catch (IOException e) {
            }
        }
    }
}
