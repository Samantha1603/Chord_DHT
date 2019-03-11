package main;

import Util.Constant;
import Util.Util;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerLookUp extends Thread
{
    private static ServerSocket serverLookUpSocket;
    private Socket socket;
    int ip;
    private FingerTable[] fingerDetails;
    PeerNode node;

    PeerLookUp(int ip,PeerNode nodeDetails)
    {
        this.ip=ip;
        this.node=nodeDetails;
        try
        {
            serverLookUpSocket=new ServerSocket(Constant.LOOKUP_PORT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        {
            System.out.println("PeerLookUp running");
            try {
                socket = serverLookUpSocket.accept();
                LookUpHandler lp=new LookUpHandler(socket,ip,node);
                lp.start();
                lp.join();
                node=lp.getNodeDetails();
                System.out.println("after peer look up handler");
                System.out.println(node.getList().size());

                //serverLookUpSocket.close();
                //socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }
}
