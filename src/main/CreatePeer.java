package main;

import Util.Constant;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class CreatePeer {

    private byte recvByte[] = new byte[Constant.messageSize];

    static boolean isEntryPoint = false;

    public static void main(String[] args) {

        CreatePeer createPeer=new CreatePeer();
        String line=null;
        BufferedReader br=null;
        BufferedReader is=null;
        PrintWriter os=null;

        if (args.length>0) {
            String ipAddress=args[0];

            try {
                InetAddress IP = InetAddress.getLocalHost();
                ipAddress=IP.getHostAddress();
            }
            catch (UnknownHostException e)
            {
                ipAddress="localhost";
            }
            createPeer.initialSetUp(ipAddress);
            Node peerNode = new Node(ipAddress);
            peerNode.run();
        }
    }

    private void initialSetUp(String serverIP) {

        try {
            Socket socket = new Socket(serverIP, Constant.SERVER_PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            dataInputStream.read(recvByte, 0, recvByte.length);

            String message = new String(Arrays.copyOfRange(recvByte, 0, Constant.messageSize)).trim();
            String messageArray[] = message.split(" ");


            if (messageArray[0].equals("isServer")) {
                System.out.println("Here");
                isEntryPoint = true;

            } else {
                System.out.println("in else if not entry point message value is:"+messageArray[1]);
            }
            socket.close();



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
