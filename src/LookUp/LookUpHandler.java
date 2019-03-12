package LookUp;

import CentralNode.CreateServer;
import Peer.Key;
import Peer.PeerNode;
import Util.Constant;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class LookUpHandler extends Thread
{
    Socket socket;
    SortedSet<Integer> peerList1;
    PeerNode nodeDetails;
    HashMap<Integer,String> listMap1;
    HashSet<Key> peerKeyList;
    int id;
    // constructor
    public LookUpHandler(Socket socket, SortedSet<Integer> list, HashMap<Integer,String> map,HashSet<Key> keyList) {
        this.socket = socket;
        this.peerList1=list;
        this.listMap1=map;
        this.peerKeyList=keyList;

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
                this.peerList1.remove(Integer.parseInt(messageArray[1]));
                this.listMap1.remove(Integer.parseInt(messageArray[1]));
                //left to do - remove from hashset

            }
            else
            {
                this.peerList1.add(Integer.parseInt(messageArray[1]));
                this.listMap1.put(Integer.parseInt(messageArray[1]),messageArray[2]);
                //left to do - in hashset as well
            }
            System.out.println("Map size at handler="+this.listMap1.size());
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(this.peerList1);
            objectOutput.writeObject(this.listMap1);
            objectOutput.writeObject(this.peerKeyList);
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

}
