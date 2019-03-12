package LookUp;

import Peer.Key;
import Util.Constant;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class PeerLookUp extends Thread
{

    private static ServerSocket serverLookUpSocket;
    private Socket socket;
    SortedSet<Integer> list1=new TreeSet<>();
    HashMap<Integer,String> listMap1=new HashMap<>();
    HashSet<Key> peerKeyList=new HashSet<Key>();

    public static void main(String args[])
    {
        PeerLookUp p=new PeerLookUp();
        try
        {
            System.out.println("In create server main");
            serverLookUpSocket=new ServerSocket(Constant.LOOKUP_PORT);
            p.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run() {

        System.out.println("In peer look up server run");
        try {

            while( true ) {
                InetAddress IP=InetAddress.getLocalHost();
                System.out.println("IP of my system is := "+IP.getHostAddress());

                socket = serverLookUpSocket.accept();
                new LookUpHandler(socket,list1,listMap1,peerKeyList).start();
            }

        }
        catch( Exception e ) {
            System.out.println("Exception in Create Server");
        }
    }
}
