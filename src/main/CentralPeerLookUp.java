package main;

import Util.Constant;
import Util.Util;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.SortedSet;
import java.util.TreeSet;

public class CentralPeerLookUp extends Thread
{
    private String serverIp;
    private byte recvData[] = new byte[Constant.messageSize];
    SortedSet<Integer> list=new TreeSet<>();
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
            list=s.getList();
            sendData(lookUpSocket);

            System.out.println(list.size());
            DataOutputStream dataOutputStream = new DataOutputStream(lookUpSocket.getOutputStream());

            byte[] sendByte = Util.makeMessage("waiting for update");
            dataOutputStream.write(sendByte);
            dataOutputStream.flush();
            ObjectInputStream objectInput = new ObjectInputStream(lookUpSocket.getInputStream()); //Error Line!
            try {
                Object object = objectInput.readObject();
                list=(SortedSet<Integer>) object;
                System.out.println(list.size());
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
                objectOutput.writeObject(list);
                objectOutput.flush();
        }
        catch (Exception e)
        {

        }
    }
}
