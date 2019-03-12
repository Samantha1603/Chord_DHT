package LookUp;

import CentralNode.CreateServer;
import Util.Constant;
import Util.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class CentralPeerLookUpHandler extends Thread
{
    Socket socket;
    CreateServer s=new CreateServer();
    private Random random = new Random();
    boolean flag=false;
    // constructor
    public CentralPeerLookUpHandler( Socket socket) {
        this.socket = socket;
    }

    public void run() {
        System.out.println();
        System.out.println("CentralPeerLookUP running");
        System.out.println(flag);
        try {
            flag=true;
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("S . get list:"+ s.listSize());
            objectOutput.writeObject(s.getList());
            objectOutput.flush();
            //objectOutput.close();


                byte[] sendByte = Util.makeMessage("updating....");

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                dataInputStream.read(sendByte, 0, Constant.messageSize);
                String message = new String(Arrays.copyOfRange(sendByte, 0, Constant.messageSize)).trim();
               System.out.println(s.listSize());
                s.remove(Integer.parseInt(message));
                System.out.println(s.listSize());

                socket.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
