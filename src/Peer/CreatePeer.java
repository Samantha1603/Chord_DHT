package Peer;

import LookUp.PeerLookUp;
import Util.Constant;
import Util.Util;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class CreatePeer {

    private byte recvByte[] = new byte[Constant.messageSize];

    static int id;
    static String centralServerIP;
    static String nodeIP;
    static boolean isFirstNode;


    public static void main(String args[])
    {
        CreatePeer createPeer=new CreatePeer();
        if(args.length>0)
            centralServerIP=args[0];
        else
            centralServerIP="192.168.56.1";
        System.out.println(centralServerIP);
        createPeer.startPeerSocket();

        PeerDetails newPeer = new PeerDetails(id,centralServerIP,nodeIP,isFirstNode);
        newPeer.start();

    }

    private void startPeerSocket()
    {
        try {
            System.out.println("Create Peer run listening");
            Socket socket = new Socket(centralServerIP, Constant.SERVER_PORT);
            byte[] sendByte =new byte[Constant.messageSize];

            //Listen from server
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            nodeIP = socket.getLocalAddress().getHostAddress();
            dataInputStream.read(recvByte, 0, recvByte.length);
            String message = new String(Arrays.copyOfRange(recvByte, 0, Constant.messageSize)).trim();
            String messageArray[] = message.split(":");
            System.out.println("Message from server is:" + message);

            if (Integer.parseInt(messageArray[0]) == Constant.n - 1)
            {
                System.out.println("Max peers already present on the ring");
                System.out.println("Node not connected");
                System.exit(1);
            }
            else
            {
                id=Integer.parseInt(messageArray[1]);
                isFirstNode=Boolean.valueOf(messageArray[2]);

            }

            socket.close();

        }catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int getId() {
        return id;
    }


    public static String getIpAddr() {
        return centralServerIP;
    }



}
