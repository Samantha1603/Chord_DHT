package Peer;

import Util.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.*;

public class PeerDetails extends Thread
{
    int id;
    String serverIPAddr;
    String clientIPAddr;
    boolean isFirstNode;

    PeerDetails(int randomNumber,String serverIp,String nodeIp, boolean flag){
        id=randomNumber;
        serverIPAddr=serverIp;
        clientIPAddr=nodeIp;
        isFirstNode=flag;
        connectToLookUp("");
    }

    public void run()
    {

        boolean isNodeRunning = true;
        Scanner sc=new Scanner(System.in);
        while (isNodeRunning) {

            printOptionsMenu();
            int switchInt = sc.nextInt();

            switch (switchInt) {

                case 1: //Finger table details
                    break;

                case 2: //Upload Files to Zone
                    break;

                case 3: //Download Files from Zone
                    break;

                case 4: //Files at the Zone//
                    break;

                case 5: //Leave Chord Zone
                    break;

                default:
                    System.out.println("Enter the correct option");
                    break;
            }

        }
    }

    public static void printOptionsMenu()
    {
        System.out.println("Choose an option:");
        System.out.println("1 - Finger Table Details ");
        System.out.println("2 - Upload File");
        System.out.println("3 - Download File ");
        System.out.println("4 - Files present at this node ");
        System.out.println("5 - Leave chord");
        System.out.print("Your input - ");
    }

    public void connectToLookUp(String msg)
    {
        {
            try {
                System.out.println("connect to lookup");
                Socket socket = new Socket(serverIPAddr, Constant.LOOKUP_PORT);
                byte[] sendData=new byte[Constant.messageSize];
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                if(msg.contains("Delete"))
                    sendData= Util.makeMessage(msg+":"+serverIPAddr);
                else
                    sendData=Util.makeMessage("id:"+this.id+":"+serverIPAddr+":"+clientIPAddr+":"+isFirstNode);

                dataOutputStream.write(sendData);

                dataOutputStream.flush();
//                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//                dataInputStream.read(sendData, 0, Constant.messageSize);
//                String message = new String(Arrays.copyOfRange(sendData, 0, Constant.messageSize)).trim();
//                System.out.println("message at Peer Details is:" + message);

                ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream()); //Error Line!
                try {
                    Object object = objectInput.readObject();
                    Node p=(Node) object;
                    p.display();
                } catch (ClassNotFoundException e) {
                    System.out.println("The title list has not come from the server");
                    e.printStackTrace();
                }
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
