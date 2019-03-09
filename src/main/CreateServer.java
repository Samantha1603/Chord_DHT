package main;

import Util.Constant;

import java.net.ServerSocket;
import java.net.Socket;

public class CreateServer extends Thread
{

    private static ServerSocket serverSocket;
    private Socket socket;
    public static void main(String args[]){
        CreateServer s=new CreateServer();
        try
        {
            serverSocket=new ServerSocket(Constant.SERVER_PORT);
            s.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void run() {

        try {

            System.out.println("Create Server running");
            while( true ) {
                socket = serverSocket.accept();
                new ServerHandler(socket).start();
            }

        }
        catch( Exception e ) {

        }
    }


}
