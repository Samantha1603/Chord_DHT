package Peer;

import FileManager.PeerFileManager;
import Util.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.*;

public class PeerDetails extends Thread
{
    PeerNode nodeDetails;
    int id;
    String serverIPAddr;
    String clientIPAddr;
    SortedSet<Integer> peerList=new TreeSet<>();
    HashMap<Integer,String> peerMap=new HashMap<>();
    PeerFileManager fileManager=new PeerFileManager();


    PeerDetails(int randomNumber,String serverIp,String nodeIp){
        id=randomNumber;
        serverIPAddr=serverIp;
        clientIPAddr=nodeIp;
        connectToLookUp("");
        nodeDetails=new PeerNode(id,peerList,peerMap,serverIPAddr);
        fileManager.replicateFilesFromSuccessor(nodeDetails);
//        FileUploadListener uploadListener=new FileUploadListener();
//        uploadListener.start();
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
                    connectToLookUp("");
                    nodeDetails=new PeerNode(id,peerList,peerMap,serverIPAddr);
                    nodeDetails.display();
                    break;

                case 2: //Upload Files to Zone
                    connectToLookUp("");
                    nodeDetails=new PeerNode(id,peerList,peerMap,serverIPAddr);
                    System.out.println("Upload Files");
                    fileManager.upload(nodeDetails,peerList,peerMap);

                    break;

                case 3: //Download Files from Zone
                    connectToLookUp("");
                    nodeDetails=new PeerNode(id,peerList,peerMap,serverIPAddr);
                    System.out.println("Download files from zone");
                    break;

                case 4: //Files at the Zone
                    connectToLookUp("");
                    nodeDetails=new PeerNode(id,peerList,peerMap,serverIPAddr);
                    System.out.println("Files present at the node:");
                    fileManager.getFileNames(clientIPAddr);

                    break;

                case 5: //Leave Chord Zone
                    connectToLookUp("");
                    nodeDetails=new PeerNode(id,peerList,peerMap,serverIPAddr);
                    fileManager.replicateFiles(nodeDetails);
                    nodeDetails.remove(id);
                    connectToLookUp("Delete:"+id);
                    nodeDetails=new PeerNode(id,peerList,peerMap,serverIPAddr);
                    isNodeRunning=false;
                    break;

                default:
                    System.out.println("Enter the correct option");
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
                //System.out.println("Request IP for Zone: " + zoneNumber + " to IP: " + requestIP);
                Socket socket = new Socket(serverIPAddr, Constant.LOOKUP_PORT);
                byte[] sendData=new byte[Constant.messageSize];
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                if(msg.contains("Delete"))
                    sendData=Util.makeMessage(msg+":"+serverIPAddr);
                else
                    sendData=Util.makeMessage("id:"+this.id+":"+serverIPAddr);

                dataOutputStream.write(sendData);
                ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream()); //Error Line!
                try {
                    Object object = objectInput.readObject();
                    this.peerList=(SortedSet<Integer>) object;
                    Object objectMap = objectInput.readObject();
                    this.peerMap=(HashMap<Integer, String>)objectMap;
                    System.out.println("PeerMap:");
                    System.out.println(peerMap.size());

                } catch (ClassNotFoundException e) {
                    System.out.println("The title list has not come from the server");
                    e.printStackTrace();
                }
                dataOutputStream.flush();

                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
