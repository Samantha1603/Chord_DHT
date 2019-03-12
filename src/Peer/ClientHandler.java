package Peer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread
{
    Socket serverSocket;
    int port;
    ClientHandler(Socket conn,int port)
    {
      //  System.out.println("ClientHandler constructor");
        this.serverSocket=conn;
        this.port=port;
    }

    public void run()
    {
        System.out.println("In clientHandler run");
        {
            System.out.println("Just connected to "
                    + serverSocket.getRemoteSocketAddress());
            DataInputStream in = null;
            DataOutputStream out = null;
            try {
                in = new DataInputStream(serverSocket.getInputStream());
                String received = in.readUTF();
                String[] arguments = received.split(":");
                System.out.println("recevied msg: " + received);

                out = new DataOutputStream(serverSocket.getOutputStream());

                serverSocket.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
