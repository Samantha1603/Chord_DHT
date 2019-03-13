package LookUp;

import Peer.Node;
import Util.Constant;
import Util.Util;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class LookUpHandler extends Thread
{
    Socket socket;
    HashMap<Integer,String> peerMap;
    SortedSet<Integer> peerList;
    String ip;
    PeerLookUp p;
    int id;

    // constructor
    public LookUpHandler(Socket socket, SortedSet<Integer> list, HashMap<Integer,String> map,PeerLookUp lookUp) {
        this.socket = socket;
        this.peerList=list;
        this.peerMap=map;
        this.p=lookUp;
    }

    // thread starts
    public void run() {
        System.out.println();
        System.out.println("LookUpHandler running");
        try {
            byte[] sendByte = new byte[Constant.messageSize];

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            dataInputStream.read(sendByte, 0, Constant.messageSize);
            String message = new String(Arrays.copyOfRange(sendByte, 0, Constant.messageSize)).trim();
            System.out.println("message at lookup server is:" + message);

            String[] messageArray = message.split(":");
            if(message.contains("Delete"))
            {
                this.peerList.remove(Integer.parseInt(messageArray[1]));

            }
            else
            {
                this.peerList.add(Integer.parseInt(messageArray[1]));
                id=Integer.parseInt(messageArray[1]);
                ip=messageArray[3];
                peerMap.put(id,ip);
                if(messageArray[4].equalsIgnoreCase("true"))
                    this.p.setEntryIp(ip);
                hash(ip);
                createFingerTable();


            }
            System.out.println("Map size at handler="+this.peerList.size());
            Node peerNode=createFingerTable();
//            DataOutputStream dataOutputStream = new DataOutputStream( socket.getOutputStream() );
//            sendByte = Util.makeMessage(this.p.getEntryIp());
//            dataOutputStream.write(sendByte);
//            dataOutputStream.flush();

            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(peerNode);
            objectOutput.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public int hash(String fileName) {
        byte[] filePathBytes = fileName.getBytes();
        Checksum value = new CRC32();
        value.update(filePathBytes, 0, filePathBytes.length);
        int hash = (int) value.getValue() % (Constant.n-1);
        if (hash < 0)
            hash = hash + (Constant.n-1);
        return hash;
    }

    public Node createFingerTable()
    {
        int pred=peerList.first();
        int succ=peerList.last();
        int startZ=0;
        int endZ=Constant.n-1;
        for(Integer i:peerList)
        {
            if(i<id)
                pred=i;
            else if(i>id)
            {
                succ=i;
                break;
            }
        }
        if(peerList.size()>1)
        {
            startZ=(pred+1)%(Constant.n-1);
            endZ=id;
        }
        Node p=new Node(id,ip,peerMap.get(succ),peerMap.get(pred),peerList,peerMap,startZ,endZ);

        return p;

    }

}
