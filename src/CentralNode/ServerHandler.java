package CentralNode;

import Util.Constant;
import Util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class ServerHandler extends Thread
{
    Socket socket;

    private Random random = new Random();

    // constructor
    public ServerHandler( Socket socket) {
        this.socket = socket;
    }

    // thread starts
    public void run() {
        System.out.println();
        System.out.println("ServerHandler running");
        try {
            CreateServer s=new CreateServer();

            DataOutputStream dataOutputStream = new DataOutputStream( socket.getOutputStream() );
            int randomNo=random.nextInt(Constant.n - 2) + 1;
            String sendMessage = s.listSize()+":" ;
            if(s.listSize()>=Constant.n-1)
            {
                System.out.println("Peer cannot be created");
            }
            else {
                while (s.contains(randomNo)) {
                    randomNo = random.nextInt(Constant.n - 2) + 1;
                }

                s.add(randomNo);
                sendMessage += randomNo + ":";
                if (s.listSize() == 1)
                    sendMessage += "true";
                else
                    sendMessage+="false";
            }
            byte[] sendByte = Util.makeMessage(sendMessage);
            System.out.println("Connected Node ip - " + socket.getInetAddress().toString());
            dataOutputStream.write(sendByte);

            dataOutputStream.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
