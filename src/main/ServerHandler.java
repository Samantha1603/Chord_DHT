package main;

import Util.Constant;
import Util.Util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class ServerHandler extends Thread
{
    Socket socket;

    private Random random = new Random();

    // constructor
    public ServerHandler( Socket socket ) {

        this.socket = socket;
    }

    // thread starts
    public void run() {
        System.out.println("ServerHandler running");
        try {

            DataOutputStream dataOutputStream = new DataOutputStream( socket.getOutputStream() );

            byte[] sendByte=Util.makeMessage("isServer");
            System.out.println("Chord Entry Point set - " + socket.getInetAddress().toString());

            dataOutputStream.write(sendByte);
            dataOutputStream.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


}
