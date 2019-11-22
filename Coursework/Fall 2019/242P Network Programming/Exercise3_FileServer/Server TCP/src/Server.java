import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    static final int PORT = 4444;

    public static void main(String[] args) {

        File directory;
        ServerSocket listener;
        Socket connection;

        if (args.length == 0) {
            System.out.println("Usage:  java Server <directory>");
            return;
        }

        directory = new File(args[0]);

        if (!directory.exists()) {
            System.out.println("      This directory does not exist");
            return;
        }
        if (!directory.isDirectory()) {
            System.out.println("      This file is not a directory");
            return;
        }

        try {
            listener = new ServerSocket(PORT);
            System.out.println("      Listening on port " + PORT);
            while (true) {
                connection = listener.accept();
                Scanner incoming;
                PrintWriter outgoing;
                String command = "      Failed to read command";
                try {
                    incoming = new Scanner(connection.getInputStream());
                    outgoing = new PrintWriter(connection.getOutputStream());
                    command = incoming.nextLine();

                    if (command.equalsIgnoreCase("index")) {
                        sendFileList(directory, outgoing);
                    } else if (command.toLowerCase().startsWith("get")) {
                        String fileName = command;
                        System.out.println("OK    " + connection.getInetAddress()
                                + " " + command);
                        sendFileContent(fileName, directory, outgoing);
                    } else {
                        outgoing.println("ERROR command error");
                        outgoing.flush();
                    }
                    System.out.println("OK    " + connection.getInetAddress()
                            + " " + command);
                } catch (Exception ex) {
                    System.out.println("ERROR " + connection.getInetAddress()
                            + " " + command + " " + ex);
                } finally {
                    try {
                        // Explicitly close the socket in a finally block to release resources the socket holds
                        connection.close();
                    } catch (IOException ex) {
                        System.out.println("      Error:  " + ex);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("      Error:  " + ex);
            return;
        }

    }

    private static void sendFileList(File directory, PrintWriter outgoing) throws Exception {
        String[] fileList = directory.list();
        for (int i = 0; i < fileList.length; i++)
            outgoing.println(fileList[i]);
        outgoing.flush();
        outgoing.close();
        if (outgoing.checkError())
            throw new Exception("      Error while sending data");
    }

    private static void sendFileContent(String fileName, File directory, PrintWriter outgoing)
            throws Exception {
        File file = new File(directory, fileName);
        if ((!file.exists()) || file.isDirectory()) {
            outgoing.println("ERROR");
            System.out.println("ERROR");
        } else {
            outgoing.println("OK");
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
            throw new Exception("      Error while sending data");
    }
}
