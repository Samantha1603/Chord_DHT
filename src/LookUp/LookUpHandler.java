package LookUp;

import CentralNode.CreateServer;
import Peer.PeerNode;
import Util.Constant;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class LookUpHandler extends Thread
{
    Socket socket;
    SortedSet<Integer> peerList=new TreeSet<>();
    PeerNode nodeDetails;
    HashMap<Integer,String> listMap=new HashMap<>();
    int id;
    // constructor
    public LookUpHandler(Socket socket, SortedSet<Integer> list, HashMap<Integer,String> map) {
        this.socket = socket;
        this.peerList=list;
        this.listMap=map;

    }
    // thread starts
    public void run() {
        System.out.println();
        System.out.println("ServerHandler running");
        try {
            CreateServer s=new CreateServer();


            byte[] sendByte = new byte[Constant.messageSize];
             DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             dataInputStream.read(sendByte, 0, Constant.messageSize);
             String message = new String(Arrays.copyOfRange(sendByte, 0, Constant.messageSize)).trim();
             System.out.println("message at server is:" + message);

             String[] messageArray = message.split(":");
            if(message.contains("Delete"))
            {
                this.peerList.remove(Integer.parseInt(messageArray[1]));
                this.listMap.remove(Integer.parseInt(messageArray[1]));
            }
            else
            {
                this.peerList.add(Integer.parseInt(messageArray[1]));
                this.listMap.put(Integer.parseInt(messageArray[1]),messageArray[2]);
            }
            System.out.println("Map size at handler="+this.listMap.size());
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(this.peerList);
            objectOutput.writeObject(this.listMap);

            objectOutput.flush();


        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public PeerNode getNodeDetails()
    {
        return this.nodeDetails;
    }

//    public void updateListInCentralLookUp()
//    {
//        try {
//            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
//            objectOutput.writeObject(this.nodeDetails);
//            objectOutput.flush();
//        }
//        catch (Exception e)
//        {
//
//        }
//    }
}
