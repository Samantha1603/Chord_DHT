package main;

import Util.Constant;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.SortedSet;
import java.util.TreeSet;

public class CreateServer extends Thread
{

    private static ServerSocket serverSocket;
    static SortedSet<Integer> peerList=new TreeSet<>();
    private Socket socket;
    public static void main(String args[]){
        CreateServer s=new CreateServer();
        try
        {
            System.out.println("In create server main");
            serverSocket=new ServerSocket(Constant.SERVER_PORT);
            s.start();
//            CentralPeerLookUp1 lookUp = new CentralPeerLookUp1("localhost");
//            lookUp.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void run() {

        System.out.println("In creat server run");
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
        peerList.add(val);
    }

    public SortedSet<Integer> getList()
    {
        return peerList;
    }

    public void remove(int id)
    {
        peerList.remove(id);
    }

    public int listSize()
    {
        return peerList.size();
    }

    public void setList(SortedSet<Integer> list)
    {
        peerList=list;
    }

    public boolean contains(int val)
    {
        return peerList.contains(val);
    }
}
