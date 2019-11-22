import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    public static void main(String[] args) throws IOException {

        int port = 4444;
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket sendPacket;
        String command;

        if (args.length == 0 || args.length > 2) {
            System.out.println("Usage:  java Client <server>");
            System.out.println("    or  java Client <server> <file>");
            return;
        }

        InetAddress address = InetAddress.getByName(args[0]);
        System.out.println(address);
        if (args.length == 1) {
            command = "INDEX";
            System.out.println("        Retrieving file list...");

        } else {
            command = "GET " + args[1];
            System.out.println("        Retrieving file content...");
        }


        try {
            byte[] data = new byte[1024];
            sendPacket = new DatagramPacket(data, data.length, address, port);
            socket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(data, data.length);
            socket.receive(receivePacket);
            try {
                if (args.length == 1) {
                    data = command.getBytes();
                    sendPacket = new DatagramPacket(data, data.length, address, port);
                    socket.send(sendPacket);
//                    String requestResult = new String(sendPacket.getData(), 0, sendPacket.getLength());
//                    System.out.println("HERE IS " + requestResult);
                    System.out.println("        File list from " + args[0] + ":");
                    System.out.println("        ---");
                    int i = 0;
                    while (true) {
                        byte[] fileName = new byte[1024];
                        receivePacket = new DatagramPacket(fileName, fileName.length);
                        socket.receive(receivePacket);
                        String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println("        " + i + ". " + result);
                        i++;
                    }
                } else if (args[1] != null) {
                    File file = new File(args[1]);
                    data = command.getBytes();
                    sendPacket = new DatagramPacket(data, data.length, address, port);
                    socket.send(sendPacket);
                    //String requestResult = new String(sendPacket.getData(), 0, sendPacket.getLength());
                    //System.out.println("HERE IS " + requestResult);
                    if (file.exists()) {
                        System.out.println("        File with name already exists");
                        while (true) {
                            byte[] content = new byte[1024];
                            receivePacket = new DatagramPacket(content, content.length);
                            socket.receive(receivePacket);
                            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
                            System.out.println("        " + result);
                        }
                    } else {
                        byte[] eMessage = new byte[1024];
                        receivePacket = new DatagramPacket(eMessage, eMessage.length);
                        socket.receive(receivePacket);
                        String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println(msg);
                    }
                }
                socket.close();
                return;
            } catch (Exception e) {
                System.out.println("        ERROR");
                return;
            }
        } catch (Exception e) {
            System.out.println("      Error:  " + e);
            System.out.println("      Can't make connection to server at \"" + args[0]);
        }
        return;

    }

}
