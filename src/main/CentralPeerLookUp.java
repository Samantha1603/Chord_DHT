package main;

import Util.Constant;
import Util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class PeerLookUp extends Thread
{
    private static ServerSocket lookUpServerSocket;
    private byte recvByte[] = new byte[Constant.messageSize];
    private String serverIp;
    PeerLookUp(String ip)
    {
        this.serverIp=ip;
    }
    public void run()
    {
        try
        {
            Socket lookUpSocket = new Socket(this.serverIp, Constant.SERVER_PORT);
            DataInputStream dataInputStream = new DataInputStream(lookUpSocket.getInputStream());

            dataInputStream.read(recvByte, 0, recvByte.length);

            String message = new String(Arrays.copyOfRange(recvByte, 0, Constant.messageSize)).trim();
            String messageArray[] = message.split("::");
            
            lookUpSocket.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
