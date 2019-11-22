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
            System.out.println("Usage:  java Client localhost");
            System.out.println("    or  java Client localhost <file>");
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
        PrintWriter fileOut = null;

        try {
            // If there is only 1 argument, retrieve file list
            if (args.length == 1) {
                System.out.println("      File list from " + args[0] + ":");
                System.out.println("      ----");
                while (true) {
                    String line = incoming.readLine();
                    if (line == null)
                        break;
                    System.out.println("      " + line);
                }
            } else {
                String message = incoming.readLine();
                File file = new File(args[1]);
                outgoing.println(file);
                if (!message.equalsIgnoreCase("OK")) {
                    System.out.println("      File does not exist");
                    return;
                } else if (file.exists()) {
                    System.out.println("      File with name already exists");
                }
//                fileOut = new PrintWriter(new FileWriter(args[1]));
//                System.out.println(fileOut);
                System.out.println("      File content:");
                while (true) {
                    String line = incoming.readLine();
                    if (line == null)
                        break;
                    System.out.println("      " + line);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}



