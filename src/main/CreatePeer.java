package main;

import Util.Constant;
import Util.Util;

import java.io.*;;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class CreatePeer {

    private byte recvByte[] = new byte[Constant.messageSize];

    static int id;
    static String ipAddr;
    PeerLookUp p;

    public static void main(String args[])
    {
        CreatePeer createPeer=new CreatePeer();
        if(args.length>0)
            ipAddr=args[0];
        else
            ipAddr="192.168.56.1";
        System.out.println(ipAddr);
        createPeer.startPeerSocket();
        PeerDetails newPeer = new PeerDetails(id);
        newPeer.start();
    }

    private void startPeerSocket()
    {
        try {
            System.out.println("Create Peer run listening");
            Socket socket = new Socket(ipAddr, Constant.SERVER_PORT);
            System.out.println("Reading from socket");
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            dataInputStream.read(recvByte, 0, recvByte.length);

            String message = new String(Arrays.copyOfRange(recvByte, 0, Constant.messageSize)).trim();
            String messageArray[] = message.split(":");
            System.out.println("message from server:"+message);
            if(Integer.parseInt(messageArray[0])==Constant.n-1)
                System.out.println("Max peers already present on the ring");
            else {
                this.id=Integer.parseInt(messageArray[1]);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                byte[] sendByte = Util.makeMessage("new node:" + ipAddr);
                dataOutputStream.write(sendByte);
                dataOutputStream.flush();
            }

            socket.close();

        }catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main1(String[] args) {

        CreatePeer createPeer=new CreatePeer();

        if (args.length>0) {
            ipAddr=args[0];

            try {
                InetAddress IP = InetAddress.getLocalHost();
                ipAddr=IP.getHostAddress();
            }
            catch (UnknownHostException e)
            {
                ipAddr="localhost";
            }
            createPeer.initialSetUp(ipAddr);

        }
//        boolean isNodeRunning = true;
//        Scanner sc=new Scanner(System.in);
//        while (isNodeRunning) {
//            printOptionsMenu();
//          //  System.out.println("in switch"+createPeer.nodeDetails.getList().size());
//
// //           createPeer.p=new PeerLookUp(id,createPeer.nodeDetails);
////                p=new PeerLookUp1(id,nodeDetails);
////                p.start();
////                p.join();
//           int switchInt = sc.nextInt();
//
//            switch (switchInt) {
//
//                case 1: //Finger table details
//                    System.out.println("Finger table details");
//                    createPeer.nodeDetails.display();
//                    break;
//
//                case 2: //Leave Chord Zone
//                    System.out.println("Peer with id:"+createPeer.nodeDetails.getId()+" has left");
//                    createPeer.nodeDetails.remove(createPeer.nodeDetails.getId());
//                    System.out.println("List size:"+createPeer.nodeDetails.getList().size());
//                    System.exit(1);
//                    break;
//                case 3: //Upload Files to Zone
//                    System.out.println("Upload Files to Zone");
//                    createPeer.nodeDetails.displayList();
//                    break;
//                case 4: //Download Files from Zone
//
//                    System.out.println("Download files from zone");
//                    break;
//                case 5: //Files at the Zone
//                    System.out.println("Files at the zone");
//                    break;
//
//
//                default:
//                    System.out.println("Enter the correct number");
//
//            }
//
//        }
    }

    private void initialSetUp(String serverIP) {

        try {
            System.out.println("Create Peer run listening");
            Socket socket = new Socket(serverIP, Constant.SERVER_PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            dataInputStream.read(recvByte, 0, recvByte.length);

            String message = new String(Arrays.copyOfRange(recvByte, 0, Constant.messageSize)).trim();
            String messageArray[] = message.split(":");
            if(Integer.parseInt(messageArray[0])==Constant.n-1)
                System.out.println("Max peers already present on the ring");
            else {
                this.id=Integer.parseInt(messageArray[1]);

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                byte[] sendByte = Util.makeMessage("new node:" + serverIP);
                dataOutputStream.write(sendByte);
                dataOutputStream.flush();
             //   p=new PeerLookUp1(id,nodeDetails,serverIP,-1);
           /*     p=new PeerLookUp(id,nodeDetails);
                p.start();
                p.join();
                nodeDetails=p.nodeDetails;
          *///      System.out.println("in socket"+nodeDetails.getList().size());
//                CentralPeerLookUp lookUp = new CentralPeerLookUp(serverIP);
//                lookUp.start();
            }

            socket.close();

        }catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static int getId() {
        return id;
    }


    public static String getIpAddr() {
        return ipAddr;
    }



}
