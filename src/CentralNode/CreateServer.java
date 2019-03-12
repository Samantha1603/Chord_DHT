package CentralNode;

import Util.Constant;
import Peer.Key;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class CreateServer extends Thread
{

    private static ServerSocket serverSocket;
    static SortedSet<Integer> peerList1=new TreeSet<>();
    static HashSet<Key> peerKeyList=new HashSet<>();
    private Socket socket;
    public static void main(String args[]){
        CreateServer s=new CreateServer();
        try
        {
            serverSocket=new ServerSocket(Constant.SERVER_PORT);
            s.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void run() {

        System.out.println("Centarlized node running");
        try {

            while( true ) {
                InetAddress IP=InetAddress.getLocalHost();
                System.out.println("IP of my system is := "+IP.getHostAddress());

                socket = serverSocket.accept();
                new ServerHandler(socket).start();
            }

        }
        catch( Exception e ) {
            System.out.println("Exception in Create Server");
        }
    }

    public void add(int val)
    {
        peerList1.add(val);
    }

    public SortedSet<Integer> getList()
    {
        return peerList1;
    }

    public void remove(int id)
    {
        peerList1.remove(id);
    }

    public int listSize()
    {
        return peerList1.size();
    }

    public void setList(SortedSet<Integer> list)
    {
        peerList1=list;
    }

    public boolean contains(int val)
    {
        return peerList1.contains(val);
    }

    public void add(int start,int end)
    {
        peerKeyList.add(new Key(start,end));
    }

    public HashSet<Key> getKeyList()
    {
        return peerKeyList;
    }

    public void remove(Key key)
    {
        peerKeyList.remove(key);
    }

    public int listKeySize()
    {
        return peerKeyList.size();
    }

    public void setKeyList(HashSet<Key> list)
    {
        peerKeyList=list;
    }

    public boolean contains(Key val)
    {
        return peerKeyList.contains(val);
    }
}
