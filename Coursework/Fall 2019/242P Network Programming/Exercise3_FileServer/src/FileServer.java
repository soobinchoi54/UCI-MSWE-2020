import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FileServer {

    static final int LISTENING_PORT = 3210;

    public static void main(String[] args) {
        File directory;
        ServerSocket listener;
        Socket connection;

        if (args.length == 0) {
            System.out.println("No directory read.");
            return;
        }

        directory = new File(args[0]);
        if (!directory.exists()) {
            System.out.println("This directory does not exist.");
            return;
        }
        if (!directory.isDirectory()) {
            System.out.println("This is not a directory.");
            return;
        }

        try {
            listener = new ServerSocket(LISTENING_PORT);
            System.out.println("Listening on: Port " + LISTENING_PORT);
            while (true) {
                connection = listener.accept();
                handleConnection(directory, connection);
            }
        } catch (Exception e) {
            System.out.println("Server terminated");
            System.out.println("Error: " + e);
            return;
        }
    }

    private static void handleConnection(File directory, Socket connection) {
        Scanner clientCommand;       // For reading data from the client.
        PrintWriter output;   // For transmitting data to the client.
        String command = "Command not read";
        try {
            clientCommand = new Scanner(connection.getInputStream());
            output = new PrintWriter(connection.getOutputStream());
            command = clientCommand.nextLine();
            if (command.equalsIgnoreCase("index")) {
                returnIndex(directory, output);
            } else if (command.toLowerCase().startsWith("get")) {
                String fileName = command.substring(3).trim();
                returnFileName(fileName, directory, output);
            } else {
                output.println("Error unsupported command");
                output.flush();
            }
            System.out.println("Ok    " + connection.getInetAddress()
                    + " " + command);
        } catch (Exception e) {
            System.out.println("Error " + connection.getInetAddress()
                    + " " + command + " " + e);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
            }
        }
    }

    private static void returnIndex(File directory, PrintWriter outgoing) throws Exception {
        String[] fileList = directory.list();
        for (int i = 0; i < fileList.length; i++)
            outgoing.println(fileList[i]);
        outgoing.flush();
        outgoing.close();
        if (outgoing.checkError())
            throw new Exception("Error while transmitting data.");
    }

    private static void returnFileName(String fileName, File directory, PrintWriter outgoing)
            throws Exception {
        File file = new File(directory, fileName);
        if ((!file.exists()) || file.isDirectory()) {
            outgoing.println("Error");
        } else {
            outgoing.println("Ok");
            BufferedReader fileIn = new BufferedReader(new FileReader(file));
            while (true) {
                String line = fileIn.readLine();
                if (line == null)
                    break;
                outgoing.println(line);
            }
        }
        outgoing.flush();
        outgoing.close();
        if (outgoing.checkError())
            throw new Exception("Error while transmitting data.");
    }
}
