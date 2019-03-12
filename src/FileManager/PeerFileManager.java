package FileManager;

import Util.Constant;
import Util.Util;
import Peer.PeerNode;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class PeerFileManager
{
    private byte fileInBytes[];
    private File file;
    private long fileSize;
    private int totalPackets;
    private byte fileInPackets[][];
    PeerNode nodeDetails;

    HashMap<Integer,String> listMap;
    SortedSet<Integer> peerList;
    //HashMap<String, ArrayList<String>> fileMap;
    private String filePath;
    private int sendToZone;
    public void upload(PeerNode node, SortedSet<Integer> list, HashMap<Integer, String> map)
    {
        listMap=map;
        peerList=list;
        nodeDetails=node;

        try {
//            File file = new File(String.valueOf(nodeId));
//
//            if(!file.exists())
//                file.mkdirs();
            System.out.println("Enter the file name to upload");
            Scanner src=new Scanner(System.in);
            src = new Scanner(System.in);
            filePath = src.nextLine();
            sendToZone = hash(filePath);
            file = new File(filePath);
            try {
                fileInBytes = Files.readAllBytes(file.toPath());

            } catch (IOException e) {

            }
            String ipAddress= getIpAddress(sendToZone);
            makePackets();
            sendPackets(ipAddress);
            //replicate at predecessor
            sendPackets(nodeDetails.getPredecessor());
            //replicate at successor
            sendPackets(nodeDetails.getSuccessor());


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void downloadFile(PeerNode node, SortedSet<Integer> list, HashMap<Integer, String> map)
    {
        listMap=map;
        peerList=list;
        nodeDetails=node;

        try {
//            File file = new File(String.valueOf(nodeId));
//
//            if(!file.exists())
//                file.mkdirs();
            System.out.println("Enter the file name to download with its extension");
            Scanner src=new Scanner(System.in);
            src = new Scanner(System.in);
            filePath = src.nextLine();
            sendToZone = hash(filePath);
            file = new File(filePath);
            try {
                fileInBytes = Files.readAllBytes(file.toPath());

            } catch (IOException e) {

            }
            String ipAddress= getIpAddress(sendToZone);
            makePackets();
            sendPackets(ipAddress);
            //replicate at predecessor
            sendPackets(nodeDetails.getPredecessor());
            //replicate at successor
            sendPackets(nodeDetails.getSuccessor());


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void makePackets() {

        fileSize = file.length();
        totalPackets = (int) Math.ceil(fileSize / (double) Constant.FILE_MSG_SIZE);

        fileInPackets = new byte[totalPackets][Constant.FILE_MSG_SIZE];
        for (int i = 0; i < totalPackets - 1; i++)
            System.arraycopy(fileInBytes, i * Constant.FILE_MSG_SIZE, fileInPackets[i], 0, Constant.FILE_MSG_SIZE);
        System.arraycopy(fileInBytes, (totalPackets - 1) * Constant.FILE_MSG_SIZE, fileInPackets[totalPackets - 1], 0,
                (int) fileSize - (totalPackets - 1) * 1024);

    }
    private void sendPackets(String ipAddress) {
        try {
            Socket socket = new Socket(ipAddress, Constant.FILE_UPLOAD_PORT);
            System.out.println("sending packets:"+sendToZone);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String data = new String(new File(filePath).getName() +" "+ totalPackets + " "+sendToZone+" "+ipAddress+" " );
            byte[] sendData=Util.makeMessage(data);
//            String data1 = new String(String.valueOf(sendToZone));
//            System.out.println(data1);
   //         sendData=Util.makeMessage(data);
            dataOutputStream.write(sendData);

            dataOutputStream.flush();
            for (int i = 0; i < totalPackets; i++) {
                dataOutputStream.write(fileInPackets[i]);
            }
            dataOutputStream.flush();
            dataOutputStream.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int hash(String fileName) {
        byte[] filePathBytes = fileName.getBytes();
        Checksum value = new CRC32();
        value.update(filePathBytes, 0, filePathBytes.length);
        int hash = (int) value.getValue() % 127;
        if (hash < 0)
            hash = hash + 127;
        return hash;
    }

    public int rehash(String fileName) {
        byte[] filePathBytes = fileName.getBytes();
        Checksum value = new CRC32();
        value.update(filePathBytes, 0, filePathBytes.length);
        int hash = (int) value.getValue() % 127;
        if (hash < 0)
            hash = hash + 127;
        return hash;
    }



    public String getIpAddress(int value)
    {
        String ipAddress=listMap.get(peerList.first());
        for(Integer i:peerList)
        {
            if(i<value)
                continue;
            else {
                ipAddress=listMap.get(i);
                break;
            }
        }
        return ipAddress;
    }
    public void getFileNames(String nodeIP)
    {
        File directory = new File(nodeIP);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            System.out.println(file.getName());
        }
    }
    public void replicateFiles(PeerNode node)
    {
        File directory = new File(node.getIpAddr());
        //get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList){
            try {
                 fileInBytes = Files.readAllBytes(file.toPath());
             }
             catch (IOException e) {

            }
            makePackets();
            sendPackets(node.getPredecessor());
            //replicate at successor
            sendPackets(node.getSuccessor());
        }
    }

    public void replicateFilesFromSuccessor(PeerNode node)
    {
        //replicate files from successor
    }
}
