import java.io.*;
import java.net.Socket;

public class Client {

    static final int PORT = 4444;

    public static void main(String[] args) {

        String computer;
        Socket connection;
        PrintWriter outgoing;
        BufferedReader incoming;
        String command;

        if (args.length == 0 || args.length > 2) {
            System.out.println("Usage:  java Client <server>");
            System.out.println("    or  java Client <server> <file>");
            return;
        }

        computer = args[0];

        if (args.length == 1)
            command = "INDEX";
        else
            command = "GET " + args[1];

        try {
            connection = new Socket(computer, PORT);
            incoming = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            outgoing = new PrintWriter(connection.getOutputStream());
            outgoing.println(command);
            outgoing.flush();
        } catch (Exception e) {
            System.out.println("      Can't make connection to server at \"" + args[0]);
            System.out.println("      Error:  " + e);
            return;
        }

        try {
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
                    System.out.println("      File not found");
                    System.out.println("      Server message: \n" + "   " + message);
                    return;
                }
                PrintWriter fileOut;
                File file = new File(args[1]);
                if (file.exists()) {
                    System.out.println("      File with name already exists");
                    return;
                }
                fileOut = new PrintWriter(new FileWriter(args[1]));
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
        } catch (Exception e) {
            System.out.println("      Error: " + e);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
            }
        }
    }
}
