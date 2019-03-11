package main;

import Util.Constant;
import Util.Util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.SortedSet;
import java.util.TreeSet;

public class PeerLookUp1
{
    SortedSet<Integer> peerList=new TreeSet<>();
    PeerNode nodeDetails;

    PeerLookUp1(int id,PeerNode nodeDetails,String serverIP,int removeId)
    {

            this.nodeDetails = nodeDetails;

            try {
                Socket socket = new Socket(serverIP, Constant.LOOKUP_PORT);

                    ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream()); //Error Line!
                    try {
                        Object object = objectInput.readObject();
                        peerList.addAll((SortedSet<Integer>) object);
                        this.nodeDetails = new PeerNode(id, peerList);
                     //   this.nodeDetails.setList(peerList);

                    } catch (ClassNotFoundException e) {
                        System.out.println("The title list has not come from the server");
                        e.printStackTrace();
                    }
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    byte[] sendByte = Util.makeMessage(removeId+"");
                    dataOutputStream.write(sendByte);
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    socket.close();


                socket.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public PeerNode getNodeDetails()
    {
        return this.nodeDetails;
    }

}
