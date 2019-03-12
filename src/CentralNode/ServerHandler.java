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

            if(s.listKeySize()>=Constant.n-1)
            {
                System.out.println("Peer cannot be created");
            }
            else {

                byte[] sendByte = Util.makeMessage(s.listKeySize()+":"+randomNo);
                System.out.println("Chord ip - " + socket.getInetAddress().toString());
                dataOutputStream.write(sendByte);

                dataOutputStream.flush();

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

                dataInputStream.read(sendByte, 0, Constant.messageSize);
                String message = new String(Arrays.copyOfRange(sendByte, 0, Constant.messageSize)).trim();
                System.out.println("message at server is:" + message);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
