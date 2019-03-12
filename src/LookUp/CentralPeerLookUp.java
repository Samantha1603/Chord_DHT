package LookUp;

import CentralNode.CreateServer;
import Peer.Key;
import Util.Constant;
import Util.Util;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class CentralPeerLookUp extends Thread
{
    private String serverIp;
    private byte recvData[] = new byte[Constant.messageSize];
    SortedSet<Integer> list1=new TreeSet<>();
    HashSet<Key> peerKeyList=new HashSet<Key>();

    CentralPeerLookUp(String ip)
    {
        this.serverIp=ip;
    }
    public void run()
    {
        try
        {
            CreateServer s=new CreateServer();
            System.out.println("Central Peer lookup running");
            Socket lookUpSocket = new Socket(this.serverIp, Constant.LOOKUP_PORT);
            list1=s.getList();
            peerKeyList=s.getKeyList();
            sendData(lookUpSocket);

            DataOutputStream dataOutputStream = new DataOutputStream(lookUpSocket.getOutputStream());

            byte[] sendByte = Util.makeMessage("waiting for update");
            dataOutputStream.write(sendByte);
            dataOutputStream.flush();
            ObjectInputStream objectInput = new ObjectInputStream(lookUpSocket.getInputStream()); //Error Line!
            try {
                Object object = objectInput.readObject();
                list1=(SortedSet<Integer>) object;
                System.out.println(list1.size());

                peerKeyList=(HashSet<Key>) object;
                System.out.println(peerKeyList.size());
                sendData(lookUpSocket);
                lookUpSocket.close();

            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server");
                e.printStackTrace();
            }


        }
        catch (EOFException e)
        {

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendData(Socket lookUpSocket)
    {
        try {
            System.out.println("Sending data from central look up");
                ObjectOutputStream objectOutput = new ObjectOutputStream(lookUpSocket.getOutputStream());
                objectOutput.writeObject(list1);
                objectOutput.writeObject(peerKeyList);
                objectOutput.flush();
        }
        catch (Exception e)
        {

        }
    }
}
