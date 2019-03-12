package main.NotNeeded;

import Util.Constant;
import LookUp.CentralPeerLookUpHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.SortedSet;
import java.util.TreeSet;

public class CentralPeerLookUp1 extends Thread
{
    private String serverIp;
    private byte recvData[] = new byte[Constant.messageSize];
    SortedSet<Integer> list=new TreeSet<>();

    private static ServerSocket serverSocket;
    private Socket socket;
    static SortedSet<Integer> peerList=new TreeSet<>();

    CentralPeerLookUp1(String ip)
    {
        this.serverIp=ip;
        try
        {
            serverSocket=new ServerSocket(Constant.LOOKUP_PORT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void run() {

        try {

            while (true) {
                socket = serverSocket.accept();
                new CentralPeerLookUpHandler(socket).start();
            }

        } catch (Exception e) {
            System.out.println("Exception in Create Server");
        }
    }
}
