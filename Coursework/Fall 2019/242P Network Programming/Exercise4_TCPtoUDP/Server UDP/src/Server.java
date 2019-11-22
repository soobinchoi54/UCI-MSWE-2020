import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    private static File directory;

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("Usage:  java Server <directory>");
            return;
        }

        directory = new File(args[0]);

        if (!directory.exists()) {
            System.out.println("        This directory does not exist");
            return;
        }
        if (!directory.isDirectory()) {
            System.out.println("        This file is not a directory");
            return;
        }

        int port = 4444;

        try {
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("        Listening on port " + port);
            while (true) {
                String command;
                try {
                    byte[] buffer = new byte[1024];
                    String[] fileList = directory.list();

                    DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                    socket.receive(request);
                    InetAddress clientAddress = request.getAddress();
                    int clientPort = request.getPort();
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                    socket.send(response);
                    request = new DatagramPacket(buffer, buffer.length);
                    socket.receive(request);
                    command = new String(request.getData());
                    System.out.println("        Response from server: " + command);
                    if (command.replaceAll("\0", "").equalsIgnoreCase("index")) {
                        System.out.println("        Sending File List... ");
                        String fileName;
                        for (String s : fileList) {
                            fileName = s;
                            byte[] data = fileName.getBytes();
                            response = new DatagramPacket(data, data.length, clientAddress, clientPort);
                            socket.send(response);
                        }
                        System.out.println("OK      " + clientAddress
                                + " " + command);
                    } else if (command.toLowerCase().startsWith("get")) {
                        String fileName = command.substring(3).trim();
                        File file = new File(fileName);
                        if (file.exists()) {
                            // System.out.println(file.exists());
                            System.out.println("        Reading content of " + fileName);
                            BufferedReader fileIn = new BufferedReader(new FileReader(fileName));
                            String line;
                            while ((line = fileIn.readLine()) != null) {
                                buffer = line.getBytes();
                                DatagramPacket fileContent = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                                socket.send(fileContent);
                            }
                            fileIn.close();
                            System.out.println("OK      " + clientAddress
                                    + " " + command);
                        } else {
                            System.out.println("        " + fileName + " does not exist");
                            String err = "        File does not exist";
                            byte[] ebuff = err.getBytes();
                            DatagramPacket eMessage = new DatagramPacket(ebuff, ebuff.length, clientAddress, clientPort);
                            socket.send(eMessage);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("        ERROR");
                    e.printStackTrace();
                }
            }
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        }
    }
}
