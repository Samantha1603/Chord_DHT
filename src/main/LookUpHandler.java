package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.SortedSet;
import java.util.TreeSet;

public class LookUpHandler extends Thread
{
    Socket socket;
    SortedSet<Integer> peerList=new TreeSet<>();
    PeerNode nodeDetails;
    int id;
    // constructor
    public LookUpHandler( Socket socket,int id,PeerNode node) {
        this.id=id;
        this.socket = socket;
        this.nodeDetails=node;
    }

    // thread starts
    public void run() {
        try {

            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream()); //Error Line!
            try {
                Object object = objectInput.readObject();
                peerList.addAll((SortedSet<Integer>) object);
                this.nodeDetails=new PeerNode(id,peerList);
                socket.close();
            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server");
                e.printStackTrace();
            }

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
